package org.dpolivaev.tsgen.scriptwriter;

import java.util.ArrayList;
import java.util.Arrays;

import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.SpecialValue;

public class AliasedPropertyAccessor {
	private static final int FOCUS_INDEX = 2;


	final static String[] testCaseParts = {
			"precondition", "Precondition",
			"focus", "Focus",
			"verification", "Verification",
			"postprocessing", "Postprocessing",
	};
	final static String[] scriptParts = {
			"precondition", "ScriptPrecondition",
			"postprocessing", "ScriptPostprocessing",
	};
	final private PropertyContainer propertyContainer;


	public static final String DEFAULT_TESTCASE_PROPERTY = "testcase";


	public static final String DEFAULT_SCRIPT_PROPERTY_NAME = "script";

	public AliasedPropertyAccessor(PropertyContainer propertyContainer) {
		this.propertyContainer = propertyContainer;
	}

	public String[] getTestCaseParts() {
		return getAliasedParts(getAlias(DEFAULT_TESTCASE_PROPERTY) + '.', AliasedPropertyAccessor.testCaseParts);
	}

	private String[] getAliasedParts(String prefix, final String[] parts) {
		ArrayList<String> aliasedParts = new ArrayList<>(parts.length);
		int index = 0;
		for(String part : parts){
			final String completePropertyName;
			if(index++%2==0)
				completePropertyName = prefix + part;
			else
				completePropertyName = part;
			final String alias = getAlias(completePropertyName);
			aliasedParts.add(alias);
		}
		return aliasedParts.toArray(new String[aliasedParts.size()]);
	}

	public String getAlias(String property) {
		final Object aliasObject = propertyContainer.get(property + ".alias");
		final String alias;
		if(aliasObject.equals(SpecialValue.UNDEFINED))
			alias = property;
		else
			alias = aliasObject.toString();
		return alias;
	}

	public String[] getScriptParts() {
		return getAliasedParts(getAlias(DEFAULT_SCRIPT_PROPERTY_NAME) + '.', AliasedPropertyAccessor.scriptParts);
	}

	public String getFocusPropertyName() {
		return getAlias(getAlias(DEFAULT_TESTCASE_PROPERTY) + '.' + testCaseParts[FOCUS_INDEX]);
	}
	
	public boolean isPart(String name){
		return Arrays.asList(getTestCaseParts()).contains(name) || Arrays.asList(getScriptParts()).contains(name);
	}
	
	public boolean isAlias(final String propertyName) {
		return propertyName.endsWith(".alias");
	}
}
