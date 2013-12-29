package org.dpolivaev.dsl.tsgen.naming;

import org.dpolivaev.dsl.tsgen.strategydsl.Values;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.naming.SimpleNameProvider;

import com.google.inject.Inject;

/**
 * @author Sven Efftinge - Initial contribution and API
 * @author Jan Koehnlein
 */
public class ValuesXBaseNameProvider extends DefaultDeclarativeQualifiedNameProvider {
	@Inject
	private SimpleNameProvider simpleNameProvider;
	
	public QualifiedName getFullyQualifiedName(EObject obj) {
		if(obj instanceof Values){
			return simpleNameProvider.getFullyQualifiedName(obj);
		}
		return super.getFullyQualifiedName(obj);
	}

}
