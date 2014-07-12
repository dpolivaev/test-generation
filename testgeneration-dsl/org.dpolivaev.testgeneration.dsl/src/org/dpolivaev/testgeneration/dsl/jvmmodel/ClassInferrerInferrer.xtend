package org.dpolivaev.testgeneration.dsl.jvmmodel

import com.google.inject.Inject
import com.google.inject.Singleton
import java.util.List
import org.dpolivaev.testgeneration.dsl.testspec.CounterDefinition
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
						CounterDefinition: {
							append('''this.«counterVariableName(declaration)» = _init_«declaration.name»();''')
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
				
				CounterDefinition: {
					val type = declaration.newTypeRef(int)
					jvmType.members += declaration.toMethod('_init_' + declaration.name, type) [ body = declaration.right; visibility = JvmVisibility::PRIVATE]
					jvmType.members += declaration.toField(counterVariableName(declaration), type) [ final = false; visibility = JvmVisibility::PRIVATE]
				}
			}
		}
		inferCounterMethods(jvmType, vars, fieldVisibility, false)
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
				CounterDefinition: {
					val name = counterVariableName(declaration)
					val type = declaration.newTypeRef(int)
					val right = declaration.right
					jvmType.members += declaration.toField(name, type) [ initializer = right;  visibility = JvmVisibility::PRIVATE; static = true]
				}
			}
		}
		inferCounterMethods(jvmType, vars, fieldVisibility, true)
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

	def inferCounterMethods(JvmGenericType jvmType, List<? extends EObject> objects, JvmVisibility fieldVisibility,
		boolean staticFlag) {
		for (counter : objects) {
			switch (counter) {
				CounterDefinition: {
					jvmType.members += counter.toMethod(counter.name, counter.newTypeRef(int)) [
						body = [appendable |
							val it = appendable.trace(counter)
							append('''return «counter.name»(1);''')
						]
						static = staticFlag
						visibility = fieldVisibility
					]
					jvmType.members += counter.toMethod(counter.name, counter.newTypeRef(int)) [
						parameters += counter.toParameter("_groupSize", counter.newTypeRef(int))
						body = [appendable |
							val it = appendable.trace(counter)
							val counterVariableName = counterVariableName(counter)
							append('''int oldCounterValue = «counterVariableName»;''')
							newLine
							append('''«counterVariableName» += _groupSize;''')
							newLine
							append('return oldCounterValue;')
						]
						static = staticFlag
						visibility = fieldVisibility
					]
				}
			}
		}
	}
	
	def counterVariableName(CounterDefinition counter) {
		"_counter_" + counter.name
	}
}
