/*
* generated by Xtext
*/
package org.dpolivaev.testgeneration.dsl.ui.outline
import org.eclipse.xtext.ui.editor.outline.impl.DefaultOutlineTreeProvider

;
import org.dpolivaev.testgeneration.dsl.testspec.Strategy
import org.dpolivaev.testgeneration.dsl.testspec.RuleGroup
import org.dpolivaev.testgeneration.dsl.testspec.Trigger
import org.dpolivaev.testgeneration.dsl.testspec.Condition
import static extension org.eclipse.xtext.nodemodel.util.NodeModelUtils.*
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.ui.editor.outline.IOutlineNode
import org.dpolivaev.testgeneration.dsl.testspec.Rule
import org.dpolivaev.testgeneration.dsl.testspec.ValueAction
import org.dpolivaev.testgeneration.dsl.testspec.Run

/**
 * Customization of the default outline structure.
 *
 * see http://www.eclipse.org/Xtext/documentation.html#outline
 */
public class TestSpecOutlineTreeProvider extends DefaultOutlineTreeProvider {
	protected def _text(Strategy strategy) {return "Strategy " + strategy.name}
	protected def _text(XExpression expr) { return expr.text}
	protected def _text(Rule rule) {
		return 
		(if (rule.isDefault) "default :" else ":")
		+ (rule.specialValue?:"")
		+ (if(rule.specialValue!= null) " " else "")
		+ (rule.name?:"")
		+ (if (rule.ordered) "(ordered)" else if (rule.shuffled) "(shuffled)" else "")
	}
	protected def _text(ValueAction values) { return values.valueProviders.map[text].toString}
	protected def _isLeaf(ValueAction values) { return values.ruleGroups.empty}
	
	protected def _createChildren(IOutlineNode parentNode, ValueAction valueAction){
		for(group:valueAction.ruleGroups)
			createNode(parentNode, group)
	}
	
	protected def _isLeaf(Condition condition){return true}
	protected def _isLeaf(Trigger trigger){return true}
	protected def _isLeaf(Run run){return true}
	
	protected override void _createChildren(IOutlineNode parentNode, EObject modelElement) {
		val contents = modelElement.eContents().sortBy[
			val node = it.node
			if (node != null) node.offset else 0
		];
		for (EObject childElement : contents)
			createNode(parentNode, childElement);
	}

	protected def void _createNode(IOutlineNode parentNode, RuleGroup modelElement){
		if(modelElement.condition == null && modelElement.trigger == null )
			createChildren(parentNode, modelElement)
		else
			_createNode(parentNode, modelElement as EObject);
	}
	
	protected def _text(RuleGroup modelElement) { 
		return (if (modelElement.trigger != null)'''for each «modelElement.trigger.properties»''' else "")
		+ (if(modelElement?.condition?.expr != null) '''if «modelElement.condition.expr.text»''' else "")
	}
	protected def _createChildren(IOutlineNode parentNode, RuleGroup ruleGroup){
		if(ruleGroup.rule != null)
			createNode(parentNode, ruleGroup.rule)
		for(group:ruleGroup.ruleGroups)
			createNode(parentNode, group)
	}
	
	protected def _createChildren(IOutlineNode parentNode, Strategy strategy){
		for(group: strategy.ruleGroups)
			createNode(parentNode, group)
	}
	
	protected def _isLeaf(Strategy strategy){return strategy.ruleGroups.empty}
	
	protected def _createChildren(IOutlineNode parentNode, Rule rule){
		if(rule.values != null)
			_createChildren(parentNode, rule.values);
	}
	
	protected def _isLeaf(Rule rule){return rule.values != null && _isLeaf(rule.values)}
	
	protected def _text(Run run) { return run.text}
	
	private def text(EObject it) {return node?.text?.trim?.replaceAll("\\s+", " ")}
}