package org.dpolivaev.dsl.tsgen.exporting;
import java.util.Set;

import org.dpolivaev.dsl.tsgen.naming.ValuesXBaseNameProvider;
import org.eclipse.xtext.Grammar;
import org.eclipse.xtext.generator.BindFactory;
import org.eclipse.xtext.generator.Binding;
import org.eclipse.xtext.generator.DefaultGeneratorFragment;
import org.eclipse.xtext.generator.IGeneratorFragment;
import org.eclipse.xtext.naming.IQualifiedNameProvider;

/**
 * An {@link IGeneratorFragment} to create simple name based scopes.
 *
 * @author Sven Efftinge - Initial contribution and API
 * @author Sebastian Zarnekow
 */
public class RuleIdNamesFragment extends DefaultGeneratorFragment {

	@Override
	public Set<Binding> getGuiceBindingsRt(Grammar grammar) {
		return new BindFactory()
			.addfinalTypeToType(IQualifiedNameProvider.class.getName(), ValuesXBaseNameProvider.class.getName())
			.getBindings();
	}

	@Override
	public Set<Binding> getGuiceBindingsUi(Grammar grammar) {
		return new BindFactory()
		.addTypeToType("org.eclipse.xtext.ui.refactoring.IDependentElementsCalculator",
				"org.eclipse.xtext.ui.refactoring.impl.DefaultDependentElementsCalculator")
		.getBindings();
	}
}
