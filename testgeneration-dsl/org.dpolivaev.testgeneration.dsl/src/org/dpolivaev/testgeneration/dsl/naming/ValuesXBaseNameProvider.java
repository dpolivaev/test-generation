package org.dpolivaev.testgeneration.dsl.naming;

import org.dpolivaev.testgeneration.dsl.testspec.Strategy;
import org.dpolivaev.testgeneration.dsl.testspec.Values;
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
			return getFullyQualifiedName((Values) obj);
		}
		return super.getFullyQualifiedName(obj);
	}

	private QualifiedName getFullyQualifiedName(Values obj) {
		QualifiedName lastName = simpleNameProvider.getFullyQualifiedName(obj);
		if(lastName != null){
			for(EObject strategy = obj.eContainer(); strategy != null; strategy = strategy.eContainer())
				if(strategy instanceof Strategy)
					return super.getFullyQualifiedName(strategy).append(lastName);
		}
		return lastName;
	}

}
