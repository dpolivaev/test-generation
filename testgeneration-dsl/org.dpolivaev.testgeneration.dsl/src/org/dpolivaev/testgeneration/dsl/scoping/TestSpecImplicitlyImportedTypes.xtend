package org.dpolivaev.testgeneration.dsl.scoping

import org.eclipse.xtext.xbase.scoping.batch.ImplicitlyImportedTypes
import com.google.inject.Singleton
import java.util.List
import org.eclipse.xtext.common.types.JvmType
import org.eclipse.emf.ecore.resource.Resource
import com.google.common.collect.Lists
import org.dpolivaev.testgeneration.engine.ruleengine.SpecialValue
import org.dpolivaev.testgeneration.engine.strategies.StepCounter

@Singleton
class TestSpecImplicitlyImportedTypes extends ImplicitlyImportedTypes {
	override List<JvmType> getStaticImportClasses(Resource context){
		val types = super.getStaticImportClasses(context);
		val objectsInContext = context.contents
		if(objectsInContext.size >= 2){
			val resourceMainType = objectsInContext.get(1)
			if(resourceMainType instanceof JvmType)
				types.add(resourceMainType)
		}
		return types
	}

	override protected List<Class<?>> getStaticImportClasses() {
		return Lists.<Class<?>> newArrayList(
			ArrayLiterals, CollectionLiterals, InputOutput, SpecialValue, StepCounter);
	}
}