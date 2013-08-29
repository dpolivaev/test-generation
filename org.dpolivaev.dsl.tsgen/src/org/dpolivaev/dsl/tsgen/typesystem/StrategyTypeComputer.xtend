package org.dpolivaev.dsl.tsgen.typesystem

import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.typesystem.computation.ITypeComputationState
import org.eclipse.xtext.xbase.typesystem.computation.XbaseTypeComputer
import org.dpolivaev.dsl.tsgen.strategydsl.PropertyCall

class StrategyTypeComputer extends XbaseTypeComputer {
	
	override computeTypes(XExpression expression, ITypeComputationState state) {
		if(expression instanceof PropertyCall) {
			_computeTypes(expression as PropertyCall, state);
		} else {
			super.computeTypes(expression, state)
		}
	}
	
	protected def _computeTypes(PropertyCall expression, ITypeComputationState state) {
		state.acceptActualType(getTypeForName(Object, state))
	}
}