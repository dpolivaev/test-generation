package org.dpolivaev.testgeneration.dsl.jvmmodel

import org.eclipse.xtext.common.types.JvmGenericType
import org.eclipse.emf.ecore.EObject
import java.util.List
import org.eclipse.xtext.common.types.JvmFormalParameter
import com.google.inject.Inject
import org.eclipse.xtext.xbase.jvmmodel.JvmTypesBuilder
import org.eclipse.xtext.common.types.JvmVisibility
import com.google.inject.Singleton

@Singleton
class ConstructorInferrer {
	@Inject extension JvmTypesBuilder jvmTypesBuilder
	
	def inferConstructor(JvmGenericType jvmType, EObject object,  List<JvmFormalParameter> parameters) {
		for(parameter:parameters)
			jvmType.members += parameter.toField(parameter.name, parameter.parameterType)[
				visibility = JvmVisibility::PRIVATE
				final = true
			]

		jvmType.members += object.toConstructor[
			for(parameter:parameters)
				it.parameters += parameter.toParameter(parameter.name, parameter.parameterType)
			body = [
				for(parameter: parameters){
					append('''this.«parameter.name» = «parameter.name»;''')
					newLine
				}
			]
			visibility = JvmVisibility::PUBLIC
		]
	}
}