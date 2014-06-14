package org.dpolivaev.testgeneration.dsl.jvmmodel

import java.math.BigInteger
import javax.inject.Inject
import org.dpolivaev.testgeneration.dsl.testspec.LabeledExpression
import org.dpolivaev.testgeneration.dsl.testspec.PropertyCall
import org.dpolivaev.testgeneration.engine.coverage.CoverageTracker
import org.eclipse.xtext.common.types.JvmDeclaredType
import org.eclipse.xtext.common.types.JvmPrimitiveType
import org.eclipse.xtext.common.types.util.Primitives
import org.eclipse.xtext.util.Strings
import org.eclipse.xtext.xbase.XCastedExpression
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XNumberLiteral
import org.eclipse.xtext.xbase.XStringLiteral
import org.eclipse.xtext.xbase.compiler.XbaseCompiler
import org.eclipse.xtext.xbase.compiler.output.ITreeAppendable
import org.eclipse.xtext.xbase.typesystem.references.LightweightTypeReference
import org.eclipse.xtext.common.types.util.TypeReferences

class StrategyCompiler extends XbaseCompiler {
	@Inject Primitives primitives
	@Inject TypeReferences typeReferences
	override protected doInternalToJavaStatement(XExpression expr, ITreeAppendable it, boolean isReferenced) {
		switch expr {
			PropertyCall: _internalToJavaStatement(expr, it, isReferenced)
			LabeledExpression: _internalToJavaStatement(expr, it, isReferenced)
			default : super.doInternalToJavaStatement(expr, it, isReferenced)
		}
	}

	override protected LightweightTypeReference getTypeForVariableDeclaration(XExpression expr){
		if(expr instanceof PropertyCall){
			val container = expr.eContainer
			if(container instanceof XCastedExpression){
					val type = container.type.toLightweight(expr)
					if (type.primitive)
						return type.wrapperTypeIfPrimitive
			}
		}
		return super.getTypeForVariableDeclaration(expr)
	}
	
	def _internalToJavaStatement(PropertyCall propertyCall, ITreeAppendable it, boolean isReferenced) {
		val propertyName = propertyCall.propertyName
		if(propertyName == null)
			return it
		if(propertyName.name != null)
			generateComment(propertyCall, it, isReferenced)
		else{
			for(expr : propertyName.nameExpressions){
				internalToJavaStatement(expr, it, isReferenced)
			}
			if(isReferenced){
				declareSyntheticVariable(propertyCall, it);
				newLine
				append(getVarName(propertyCall, it)) append(" = propertyContainer.")
				addTypeCastForPrimitive(propertyCall, it)
				append("get(new StringBuilder()")
				for(expr : propertyName.nameExpressions){
					append('.append(')
					internalToJavaExpression(expr, it)
					append(')')
				}
				append('.toString());')
			}

		}
	}

	protected def _internalToConvertedExpression(PropertyCall propertyCall, ITreeAppendable it) {
		val propertyName = propertyCall.propertyName
		if(propertyName == null)
			return it
		if(propertyName.name != null) {
			append('propertyContainer.')
			addTypeCastForPrimitive(propertyCall, it)
			append('''get("«propertyName.name.escapeQuotes»")''')
		}
		else
			trace(propertyCall, false).append(getVarName(propertyCall, it))
	}

	protected def addTypeCastForPrimitive(PropertyCall expr, ITreeAppendable it) {
		val container = expr.eContainer
		if(container instanceof XCastedExpression){
			val type = (container as XCastedExpression).type.type
			if(type instanceof JvmPrimitiveType){
				append('<')
				append(primitives.getWrapperType(type))
				append('>')
			}
		}
	}

	def _internalToJavaStatement(LabeledExpression labeledExpression, ITreeAppendable b, boolean isReferenced) {
		if(labeledExpression.label != null && hasCodeTracker(b)) {
			if(labeledExpression.reason != null)
				doInternalToJavaStatement(labeledExpression.reason, b, true)
			val it = b.trace(labeledExpression, false);
			newLine
			append('if(coverageTracker != null) coverageTracker.reach("') append(labeledExpression.label) append('", ')
			if(labeledExpression.reason != null){
				append('String.valueOf(')
				internalToJavaExpression(labeledExpression.reason, it)
				append(')')
				}
			else
				append('""')				
			append(");");
			if(labeledExpression.expr != null){
				if (isReferenced && !isPrimitiveVoid(labeledExpression)) {
					declareSyntheticVariable(labeledExpression, it);
				}
				val canBeReferenced = isReferenced && !isPrimitiveVoid(labeledExpression.expr);
				if (canBeReferenced) {
					internalToJavaStatement(labeledExpression.expr, it, canBeReferenced);
					newLine
					append(getVarName(labeledExpression, it)).append(" = ")
					internalToJavaExpression(labeledExpression.expr, it)
					append(";")
				}
			}
		}
		if(labeledExpression.expr != null)
			doInternalToJavaStatement(labeledExpression.expr, b, isReferenced)
	}
	


	def hasCodeTracker(ITreeAppendable it) {
		if (hasObject("this")) {
			val thisElement = getObject("this");
			if (thisElement instanceof JvmDeclaredType) {
				val type = thisElement as JvmDeclaredType
				val field = type.declaredFields.findFirst[
					simpleName == "coverageTracker"
				]
				return field != null && field.type.qualifiedName.equals(CoverageTracker.name)
			}
			
		}
		return false
	}
	
	override protected internalToConvertedExpression(XExpression expr, ITreeAppendable it) {
		switch expr {
			PropertyCall: _internalToConvertedExpression(expr, it)
			LabeledExpression: _internalToConvertedExpression(expr, it)
			default : super.internalToConvertedExpression(expr, it)
		}
	}
	
	protected def _internalToConvertedExpression(LabeledExpression expr, ITreeAppendable it){
		if(expr.label != null && hasCodeTracker(it))
			trace(expr, false).append(getVarName(expr, it))
		else if(expr.expr != null)
			internalToConvertedExpression(expr.expr, it)
	}
	
	override public void _toJavaExpression(XStringLiteral expr, ITreeAppendable b) {
		val type = getType(expr);
		val javaString = convertToJavaString(expr.getValue());
		if (typeReferences.is(type, Character.TYPE)) 
			b.append("'").append(javaString).append("'")
		else if (typeReferences.is(type, Character))
			b.append("Character.valueOf('").append(javaString).append("')")
		else 
			b.append("\"").append(javaString).append("\"")
		
	}

	static val eolSeparator = System.getProperty("line.separator")
	def convertToJavaString(String string) {
			Strings.convertToJavaString(string.replace(eolSeparator, "\n"));
	}
	
	
	override protected internalCanCompileToJavaExpression(XExpression expr, ITreeAppendable it) {
		if(expr instanceof XCastedExpression)
			return internalCanCompileToJavaExpression(expr.target, it) 
		return super.internalCanCompileToJavaExpression(expr, it);
	}

	def public static escapeQuotes(String input) {
		var output = input
		if(output.startsWith("\""))
			output = output.substring(1)
		if(output.endsWith("\""))
			output = output.substring(0, output.length - 1)
		output = output.replaceAll("\"", "\\\\\\\"")
		return output
	}

	override protected LightweightTypeReference getLightweightType(XExpression expr) {
		if(expr instanceof PropertyCall){
			val container = expr.eContainer
			if(container instanceof XCastedExpression){
				val castedType = container.type.toLightweight(container)
				if(castedType.primitive)
					return castedType.wrapperTypeIfPrimitive
			}
		}
		val type = super.getLightweightType(expr)
		if(type != null)
			return type
		if (expr instanceof XNumberLiteral && (expr as XNumberLiteral).value.toLowerCase.endsWith("#bi"))
			return 	typeReferences.getTypeForName(BigInteger, expr).toLightweight(expr)
		return null
	}
	
	override protected boolean isVariableDeclarationRequired(XExpression expr, ITreeAppendable b){
		if(expr instanceof XCastedExpression)
			return isVariableDeclarationRequired(expr.target, b)
		else if(expr instanceof PropertyCall)
			return expr.propertyName?.name == null
		else
			return super.isVariableDeclarationRequired(expr, b)
	}

}