package org.dpolivaev.testgeneration.dsl.jvmmodel

import org.eclipse.xtext.common.types.JvmGenericType
import org.eclipse.emf.ecore.EObject
import java.util.List
import org.eclipse.xtext.common.types.JvmFormalParameter
import com.google.inject.Inject
import org.eclipse.xtext.xbase.jvmmodel.JvmTypesBuilder
import org.eclipse.xtext.common.types.JvmVisibility
import com.google.inject.Singleton
import org.eclipse.xtext.xbase.XVariableDeclaration
import org.eclipse.xtext.xbase.XExpression
import org.dpolivaev.testgeneration.dsl.testspec.MethodDefinition

@Singleton
class ClassInferrer {
	@Inject extension JvmTypesBuilder jvmTypesBuilder

	def inferConstructor(JvmGenericType jvmType, EObject object,  List<JvmFormalParameter> parameters,  List<XExpression> vars) {
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
				for(expr:vars){
					val declaration = expr as XVariableDeclaration
					append('''this.«declaration.name» = _init_«declaration.name»();''')
					newLine
				}
			]
			visibility = JvmVisibility::PUBLIC
		]
	}

	def inferMemberVariables(JvmGenericType jvmType, EObject object,  List<XExpression> vars){
			for(expr:vars){
				val declaration = expr as XVariableDeclaration
				val type = declaration.type?: declaration.right?.inferredType
				if (declaration.right != null){
					jvmType.members += object.toMethod('_init_' + declaration.name, type)[
						body = declaration.right
						visibility = JvmVisibility::PRIVATE
					]
				}

				jvmType.members += object.toField(declaration.name, type)[
					final = ! declaration.writeable
					visibility = JvmVisibility::PUBLIC
				]


			}
	}

	def inferMemberMethods(JvmGenericType jvmType, EObject object,  List<MethodDefinition> methods){
			for(method:methods){
				jvmType.members += object.toMethod(method.name, method.returnType)[
					for(parameter:method.parameters)
						parameters += parameter.toParameter(parameter.name, parameter.parameterType)
					body = method.body
				]
			}
	}

}