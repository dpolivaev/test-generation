/*
 * generated by Xtext
 */
package org.dpolivaev.dsl.tsgen;

import org.dpolivaev.dsl.tsgen.jvmmodel.StrategyCompiler;
import org.dpolivaev.dsl.tsgen.naming.ValuesXBaseNameProvider;
import org.dpolivaev.dsl.tsgen.typesystem.StrategyTypeComputer;
import org.eclipse.xtext.xbase.compiler.XbaseCompiler;
import org.eclipse.xtext.xbase.typesystem.computation.ITypeComputer;

/**
 * Use this class to register components to be used at runtime / without the Equinox extension registry.
 */
public class StrategyDslRuntimeModule extends org.dpolivaev.dsl.tsgen.AbstractStrategyDslRuntimeModule {
	public Class<? extends XbaseCompiler> bindXbaseCompiler() {
		return StrategyCompiler.class;
	}
	
	public Class<? extends org.eclipse.xtext.naming.IQualifiedNameProvider> bindIQualifiedNameProvider() {
		return ValuesXBaseNameProvider.class;
	}

	@Override
	public Class<? extends ITypeComputer> bindITypeComputer() {
		return StrategyTypeComputer.class;
	}
	
	@Override
	public Class<? extends org.eclipse.xtext.formatting.IFormatter> bindIFormatter() {
		return org.dpolivaev.dsl.tsgen.formatting.StrategyDslFormatter.class;
	}
}