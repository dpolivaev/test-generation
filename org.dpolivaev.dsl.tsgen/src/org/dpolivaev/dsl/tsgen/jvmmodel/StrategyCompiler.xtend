package org.dpolivaev.dsl.tsgen.jvmmodel

import org.eclipse.xtext.xbase.compiler.XbaseCompiler
import org.eclipse.xtext.xbase.compiler.output.ITreeAppendable
import org.eclipse.xtext.xbase.XExpression
import org.dpolivaev.dsl.tsgen.strategydsl.PropertyCall
import org.eclipse.xtext.xbase.XStringLiteral
import org.eclipse.xtext.common.types.JvmTypeReference
import org.eclipse.xtext.util.Strings

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