package org.dpolivaev.dsl.tsgen.scoping

import org.eclipse.xtext.xbase.scoping.batch.ImplicitlyImportedTypes
import com.google.inject.Singleton
import java.util.List
import org.eclipse.xtext.common.types.JvmType
import org.eclipse.emf.ecore.resource.Resource

@Singleton
class StrategyDslImplicitlyImportedTypes extends ImplicitlyImportedTypes {
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
}