package org.dpolivaev.dsl.tsgen.naming;

import org.dpolivaev.dsl.tsgen.strategydsl.Values;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.naming.SimpleNameProvider;
import org.eclipse.xtext.xbase.scoping.XbaseQualifiedNameProvider;

import com.google.inject.Inject;

/**
 * @author Sven Efftinge - Initial contribution and API
 * @author Jan Koehnlein
 */
public class ValuesXBaseNameProvider extends IQualifiedNameProvider.AbstractImpl {
	@Inject
	private SimpleNameProvider simpleNameProvider;
	
	@Inject
	private XbaseQualifiedNameProvider xbaseQualifiedNameProvider;
	
	public QualifiedName getFullyQualifiedName(EObject obj) {
		if(obj instanceof Values){
			return simpleNameProvider.getFullyQualifiedName(obj);
		}
		return xbaseQualifiedNameProvider.getFullyQualifiedName(obj);
	}

}
