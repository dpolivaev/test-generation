package org.dpolivaev.dsl.tsgen.jvmmodel

import org.eclipse.xtext.xbase.compiler.XbaseCompiler
import org.eclipse.xtext.xbase.compiler.output.ITreeAppendable
import org.eclipse.xtext.xbase.XExpression
import org.dpolivaev.dsl.tsgen.strategydsl.PropertyCall

class StrategyCompiler extends XbaseCompiler {
	override protected doInternalToJavaStatement(XExpression expr, ITreeAppendable it, boolean isReferenced) {
		switch expr {
			PropertyCall:{
				generateComment(expr, it, isReferenced);
			}
			default :
				super.doInternalToJavaStatement(expr, it, isReferenced)
		}
	}
	
	override protected internalToConvertedExpression(XExpression expr, ITreeAppendable it) {
		switch expr {
			PropertyCall:{
				append('''propertyContainer.get("«expr.name.escapeQuotes»")''');
			}
			default :
				super.internalToConvertedExpression(expr, it)
		}
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