package org.dpolivaev.testgeneration.dsl.jvmmodel

import com.google.inject.Inject
import com.google.inject.Singleton
import java.util.List
import org.dpolivaev.testgeneration.dsl.testspec.MethodDefinition
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.common.types.JvmFormalParameter
import org.eclipse.xtext.common.types.JvmGenericType
import org.eclipse.xtext.common.types.JvmVisibility
import org.eclipse.xtext.xbase.XVariableDeclaration
import org.eclipse.xtext.xbase.jvmmodel.JvmTypesBuilder

@Singleton
class ClassInferrer {
	@Inject extension JvmTypesBuilder jvmTypesBuilder

	def inferConstructor(JvmGenericType jvmType, EObject object,  List<JvmFormalParameter> parameters,  List<? extends EObject> vars) {
		for(parameter:parameters)
			jvmType.members += parameter.toField(parameter.name, parameter.parameterType)[
				visibility = JvmVisibility::PRIVATE
				final = true
			]

		jvmType.members += object.toConstructor [
			for (parameter : parameters)
				it.parameters += parameter.toParameter(parameter.name, parameter.parameterType)
			body = [
				for (parameter : parameters) {
					append('''this.«parameter.name» = «parameter.name»;''')
					newLine
				}
				for (declaration : vars) {
					switch (declaration) {
						XVariableDeclaration: {
							append('''this.«declaration.name» = _init_«declaration.name»();''')
							newLine
						}
					}
				}
			]
			visibility = JvmVisibility::PUBLIC
		]
	}

	def inferMemberVariables(JvmGenericType jvmType, List<? extends EObject> vars, JvmVisibility fieldVisibility) {
		for (declaration : vars) {
			switch (declaration) {
				XVariableDeclaration: {
					val type = declaration.type ?: declaration.right?.inferredType
					if (declaration.right != null) {
						jvmType.members += declaration.toMethod('_init_' + declaration.name, type) [ body = declaration.right; visibility = JvmVisibility::PRIVATE]
					}

					jvmType.members += declaration.toField(declaration.name, type) [ final = ! declaration.writeable; visibility = fieldVisibility]
				}
			}
		}
	}
	
	def inferStaticMemberVariables(JvmGenericType jvmType, List<? extends EObject> vars, JvmVisibility fieldVisibility) {
		for (declaration : vars) {
			switch (declaration) {
				XVariableDeclaration: {
					val name = declaration.name
					val type = declaration.right.inferredType
					val right = declaration.right
					jvmType.members += declaration.toField(name, type) [ initializer = right;  visibility = fieldVisibility; static = true]
				}
			}
		}
	}

	def inferMemberMethods(JvmGenericType jvmType, List<MethodDefinition> methods,
		JvmVisibility fieldVisibility, boolean staticFlag){
			for(method:methods){
				jvmType.members += method.toMethod(method.name, method.returnType)[
					for(parameter:method.parameters)
						parameters += parameter.toParameter(parameter.name, parameter.parameterType)
					body = method.body
					static = staticFlag
					visibility = fieldVisibility
				]
			}
	}
}
