package org.dpolivaev.testgeneration.dsl.jvmmodel

import javax.inject.Inject
import org.dpolivaev.testgeneration.dsl.testspec.LabeledExpression
import org.dpolivaev.testgeneration.dsl.testspec.PropertyCall
import org.eclipse.xtext.common.types.JvmPrimitiveType
import org.eclipse.xtext.common.types.util.Primitives
import org.eclipse.xtext.util.Strings
import org.eclipse.xtext.xbase.XCastedExpression
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XStringLiteral
import org.eclipse.xtext.xbase.compiler.XbaseCompiler
import org.eclipse.xtext.xbase.compiler.output.ITreeAppendable
import org.eclipse.xtext.common.types.JvmDeclaredType
import org.dpolivaev.testgeneration.engine.coverage.CoverageTracker
import org.eclipse.xtext.common.types.JvmTypeReference
import org.eclipse.xtext.xbase.XNumberLiteral
import java.math.BigInteger

class StrategyCompiler extends XbaseCompiler {
	@Inject Primitives primitives
	override protected doInternalToJavaStatement(XExpression expr, ITreeAppendable it, boolean isReferenced) {
		switch expr {
			PropertyCall: _internalToJavaStatement(expr, it, isReferenced)
			LabeledExpression: _internalToJavaStatement(expr, it, isReferenced)
			default : super.doInternalToJavaStatement(expr, it, isReferenced)
		}
	}
	
	def _internalToJavaStatement(PropertyCall propertyCall, ITreeAppendable it, boolean isReferenced) {
		if(propertyCall.name != null)
			generateComment(propertyCall, it, isReferenced)
		else{
			for(expr : propertyCall.expressions){
				internalToJavaStatement(expr, it, isReferenced)
			}
			if(isReferenced){
				declareSyntheticVariable(propertyCall, it);
				newLine
				append(getVarName(propertyCall, it)) append(" = propertyContainer.get(new StringBuilder()")
				for(expr : propertyCall.expressions){
					append('.append(')
					internalToConvertedExpression(expr, it, expr.type)
					append(')')
				}
				append('.toString());')
			}

		}
	}

	protected def _internalToConvertedExpression(PropertyCall expr, ITreeAppendable it) {
		if(expr.name != null) {
			append('propertyContainer.')
			val container = expr.eContainer
			if(container instanceof XCastedExpression){
				val type = (container as XCastedExpression).type.type
				if(type instanceof JvmPrimitiveType){
					append('<')
					append(primitives.getWrapperType(type))
					append('>')
				}
			}
			append('''get("«expr.name.escapeQuotes»")''')
		}
		else
			trace(expr, false).append(getVarName(expr, it))
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
				internalToConvertedExpression(labeledExpression.reason, it, labeledExpression.reason.type)
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
					internalToConvertedExpression(labeledExpression.expr, it, labeledExpression.type);
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
		if (getTypeReferences().is(type, Character.TYPE)) 
			b.append("'").append(javaString).append("'")
		else if (getTypeReferences().is(type, Character))
			b.append("Character.valueOf('").append(javaString).append("')")
		else 
			b.append("\"").append(javaString).append("\"")
		
	}

	static val eolSeparator = System.getProperty("line.separator")
	def convertToJavaString(String string) {
			Strings.convertToJavaString(string.replace(eolSeparator, "\n"));
	}
	
	
	override protected internalCanCompileToJavaExpression(XExpression expr, ITreeAppendable it) {
		return expr instanceof PropertyCall || super.internalCanCompileToJavaExpression(expr, it);
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

	override protected JvmTypeReference getType(XExpression expr) {
		val type = typeProvider.getType(expr)
		if(type != null)
			return type
		if (expr instanceof XNumberLiteral && (expr as XNumberLiteral).value.toLowerCase.endsWith("#bi"))
			return 	typeReferences.getTypeForName(BigInteger, expr)
		return null
	}

}