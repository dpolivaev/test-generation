package org.dpolivaev.dsl.tsgen.typesystem

import org.dpolivaev.dsl.tsgen.strategydsl.PropertyCall
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.typesystem.computation.ITypeComputationState
import org.eclipse.xtext.xbase.typesystem.computation.XbaseTypeComputer
import org.eclipse.xtext.xbase.XCastedExpression
import org.eclipse.xtext.common.types.JvmPrimitiveType
import com.google.inject.Inject
import org.eclipse.xtext.common.types.util.Primitives

class StrategyTypeComputer extends XbaseTypeComputer {
	@Inject Primitives primitives;
	override computeTypes(XExpression expression, ITypeComputationState state) {
		if(expression instanceof PropertyCall) {
			_computeTypes(expression as PropertyCall, state);
		} else {
			super.computeTypes(expression, state)
		}
	}
	
	protected def _computeTypes(PropertyCall expression, ITypeComputationState state) {
		val container = expression.eContainer
		if(container instanceof XCastedExpression){
			val type = (container as XCastedExpression).type
			if(type.type instanceof JvmPrimitiveType){
				val wrapperType = primitives.asWrapperTypeIfPrimitive(type)
				state.acceptActualType(state.converter.toLightweightReference(wrapperType))
				return
			}
		}
		state.acceptActualType(getTypeForName(Object, state))
	}
}