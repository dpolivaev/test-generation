package org.dpolivaev.dsl.tsgen.naming;

import org.dpolivaev.dsl.tsgen.strategydsl.Values;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.naming.SimpleNameProvider;
import org.eclipse.xtext.xbase.scoping.XbaseQualifiedNameProvider;

import com.google.inject.Inject;

public class ValuesXBaseNameProvider extends XbaseQualifiedNameProvider {
	@Inject
	private SimpleNameProvider simpleNameProvider;
	
	public QualifiedName getFullyQualifiedName(EObject obj) {
		if(obj instanceof Values){
			return simpleNameProvider.getFullyQualifiedName(obj);
		}
		return super.getFullyQualifiedName(obj);
	}

}
