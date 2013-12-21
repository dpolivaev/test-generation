package org.dpolivaev.dsl.tsgen.naming;
import static org.eclipse.xtext.util.SimpleAttributeResolver.newResolver;

import org.dpolivaev.dsl.tsgen.strategydsl.Rule;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.util.SimpleAttributeResolver;
import org.eclipse.xtext.xbase.scoping.XbaseQualifiedNameProvider;
import org.eclipse.xtext.naming.IQualifiedNameProvider;

import com.google.inject.Inject;

/**
 * @author Sven Efftinge - Initial contribution and API
 * @author Jan Koehnlein
 */
public class RuleXBaseNameProvider extends IQualifiedNameProvider.AbstractImpl {
	public final static SimpleAttributeResolver<EObject, String> ID_RESOLVER = newResolver(String.class, "id");
	@Inject
	private IQualifiedNameConverter qualifiedNameConverter;
	
	@Inject
	private XbaseQualifiedNameProvider xbaseQualifiedNameProvider;
	
	public QualifiedName getFullyQualifiedName(EObject obj) {
		if(obj instanceof Rule){
			String name = ID_RESOLVER.apply(obj);
			if(name == null)
				return null;
			return qualifiedNameConverter.toQualifiedName(name);
		}
		return xbaseQualifiedNameProvider.getFullyQualifiedName(obj);
	}

}
