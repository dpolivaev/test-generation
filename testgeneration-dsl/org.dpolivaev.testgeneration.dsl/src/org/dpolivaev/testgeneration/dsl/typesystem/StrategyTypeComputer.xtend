package org.dpolivaev.testgeneration.dsl.typesystem

import com.google.inject.Inject
import org.dpolivaev.testgeneration.dsl.testspec.LabeledExpression
import org.dpolivaev.testgeneration.dsl.testspec.PropertyCall
import org.eclipse.xtext.common.types.JvmPrimitiveType
import org.eclipse.xtext.common.types.util.Primitives
import org.eclipse.xtext.xbase.XCastedExpression
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.typesystem.computation.ITypeComputationState
import org.eclipse.xtext.xbase.typesystem.computation.XbaseTypeComputer
import org.eclipse.xtext.xbase.typesystem.references.AnyTypeReference

class StrategyTypeComputer extends XbaseTypeComputer {
	@Inject Primitives primitives;
	override computeTypes(XExpression expression, ITypeComputationState state) {
		switch(expression){
			PropertyCall : _computeTypes(expression, state)
			LabeledExpression : _computeTypes(expression, state)
			default: super.computeTypes(expression, state)
		}
	}
	
	protected def _computeTypes(PropertyCall propertyCall, ITypeComputationState state) {
		for(expr:propertyCall.propertyName.nameExpressions)
			state.computeTypes(expr)
		val container = propertyCall.eContainer
		if(container instanceof XCastedExpression){
			val type = (container as XCastedExpression).type
			if(type.type instanceof JvmPrimitiveType){
				val wrapperType = primitives.asWrapperTypeIfPrimitive(type)
				state.acceptActualType(state.getReferenceOwner().toLightweightTypeReference(wrapperType))
				return
			}
		}
		state.acceptActualType(getTypeForName(Object, state))
	}
	protected def _computeTypes(LabeledExpression labeledExpression, ITypeComputationState state) {
		val type = state.computeTypes(labeledExpression.expr).actualExpressionType?:new AnyTypeReference(state.getReferenceOwner())
		state.acceptActualType(type)
	}
	
}