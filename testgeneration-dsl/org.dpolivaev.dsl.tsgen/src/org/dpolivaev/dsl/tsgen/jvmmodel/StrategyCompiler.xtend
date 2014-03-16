package org.dpolivaev.dsl.tsgen.jvmmodel

import javax.inject.Inject
import org.dpolivaev.dsl.tsgen.strategydsl.LabeledExpression
import org.dpolivaev.dsl.tsgen.strategydsl.PropertyCall
import org.eclipse.xtext.common.types.JvmPrimitiveType
import org.eclipse.xtext.common.types.util.Primitives
import org.eclipse.xtext.util.Strings
import org.eclipse.xtext.xbase.XCastedExpression
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XStringLiteral
import org.eclipse.xtext.xbase.compiler.XbaseCompiler
import org.eclipse.xtext.xbase.compiler.output.ITreeAppendable
import org.eclipse.xtext.common.types.JvmDeclaredType
import org.dpolivaev.tsgen.coverage.CoverageTracker

class StrategyCompiler extends XbaseCompiler {
	@Inject Primitives primitives
	override protected doInternalToJavaStatement(XExpression expr, ITreeAppendable it, boolean isReferenced) {
		switch expr {
			PropertyCall: generateComment(expr, it, isReferenced)
			LabeledExpression: _internalToJavaStatement(expr, it, isReferenced)
			default : super.doInternalToJavaStatement(expr, it, isReferenced)
		}
	}
	
	def _internalToJavaStatement(LabeledExpression expr, ITreeAppendable b, boolean isReferenced) {
		if(expr.label != null && hasCodeTracker(b)) {
			if(expr.reason != null)
				doInternalToJavaStatement(expr.reason, b, true)
			val it = b.trace(expr, false);
			newLine
			append('if(coverageTracker != null) coverageTracker.reach("') append(expr.label) append('", ')
			if(expr.reason != null){
				append('String.valueOf(')
				internalToConvertedExpression(expr.reason, it, expr.reason.getType())
				append(')')
				}
			else
				append('""')				
			append(");");
			if(expr.expr != null){
				if (isReferenced && !isPrimitiveVoid(expr)) {
					declareSyntheticVariable(expr, it);
				}
				val canBeReferenced = isReferenced && !isPrimitiveVoid(expr.expr);
				if (canBeReferenced) {
					internalToJavaStatement(expr.expr, it, canBeReferenced);
					newLine
					append(getVarName(expr, it)).append(" = ")
					internalToConvertedExpression(expr.expr, it, getType(expr));
					append(";")
				}
			}
		}
		if(expr.expr != null)
			doInternalToJavaStatement(expr.expr, b, isReferenced)
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
	
	protected def _internalToConvertedExpression(PropertyCall expr, ITreeAppendable it) {
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
}