package org.dpolivaev.testgeneration.dsl.jvmmodel

import com.google.inject.Inject
import java.util.Collections
import java.util.HashSet
import org.dpolivaev.testgeneration.dsl.testspec.LabeledExpression
import org.dpolivaev.testgeneration.dsl.testspec.Rule
import org.dpolivaev.testgeneration.engine.coverage.CoverageEntry
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.xbase.XBooleanLiteral
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XNumberLiteral
import org.eclipse.xtext.xbase.XStringLiteral
import org.eclipse.xtext.xbase.compiler.output.ITreeAppendable
import org.eclipse.xtext.xbase.jvmmodel.JvmTypesBuilder

class CoverageEntriesInferrer{
	@Inject extension JvmTypesBuilder jvmTypesBuilder
	
	def appendArrayInitializer(ITreeAppendable it, EObject sourceObject) {
		append('new ') append(sourceObject.newTypeRef(CoverageEntry).type) append('[]{')
		val labels = new HashSet<CoverageEntry>
		val contents = EcoreUtil2.eAllContents(sourceObject)
		for(obj : contents){
			if (obj instanceof LabeledExpression)
				labels += coverageEntry(obj as LabeledExpression)
			if (obj instanceof Rule)
				labels += coverageEntries(obj as Rule)
		}
		for(label:labels){
			newLine
			append('  new ')
			append(sourceObject.newTypeRef(CoverageEntry).type) 
			append('("') append(label.name) append('", ')
			if(label.reason == CoverageEntry.ANY)  
				append(sourceObject.newTypeRef(CoverageEntry).type).append('.ANY')
			else
				append('"').append(label.reason).append('"')
					 
			append('),')
		}
		append('}')
	}
	
	def coverageEntry(LabeledExpression expression) {
		val expressionLabel = expression.label
		val expressionReason =expression.reason
		coverageEntry(expressionLabel, expressionReason)
	}
	
	def coverageEntry(String expressionLabel, XExpression expressionReason) {
		val reason =
			switch(expressionReason){
				XStringLiteral : expressionReason.value
				XNumberLiteral : expressionReason.value
				XBooleanLiteral : expressionReason.isTrue.toString
				default : CoverageEntry.ANY
			} 
		new CoverageEntry(expressionLabel, reason)
	}
	
	def coverageEntries(Rule rule) {
		val name = rule.propertyName?.name.unquote
		if(name != null && name.startsWith("[" )  && name.endsWith("]") && rule.values?.actions != null){
			val requirementId = name.substring(1, name.length - 1)
			val entries = new HashSet<CoverageEntry>
			for(action  : rule.values.actions)
				for(valueProvider  : action.valueProviders)
					if(valueProvider.expressions.size == 1)
						entries += coverageEntry(requirementId, valueProvider.expressions.get(0))
			if(entries.isEmpty)
				entries += new CoverageEntry(requirementId, CoverageEntry.ANY)
			entries
		}
		else
			Collections.<CoverageEntry>emptySet
	}
	
	def unquote(String string){
		if(string != null && string.length > 2 && string.startsWith('"') && string.endsWith('"'))
			string.substring(1, string.length - 1) 
		else 
			string
	}
}