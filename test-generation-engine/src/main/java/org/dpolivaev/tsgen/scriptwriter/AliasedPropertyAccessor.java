package org.dpolivaev.tsgen.scriptwriter;

import java.util.ArrayList;
import java.util.Arrays;

import org.dpolivaev.tsgen.ruleengine.PropertyContainer;
import org.dpolivaev.tsgen.ruleengine.SpecialValue;

public class AliasedPropertyAccessor {
	private static final int PRECONDITION_INDEX = 0;
	private static final int FOCUS_INDEX = 2;
	private static final int VERIFICATION_INDEX = 4;
	private static final int POSTPOCESSING_INDEX = 6;
	private static final int SCRIPT_PRECONDITION_INDEX = 0;
	private static final int SCRIPT_POSTPOCESSING_INDEX = 2;


	final static String[] testCaseParts = {
			"testcase.precondition", "Precondition",
			"testcase.focus", "Focus",
			"testcase.verification", "Verification",
			"testcase.postprocessing", "Postprocessing",
	};
	final static String[] scriptParts = {
			"script.precondition", "ScriptPrecondition",
			"script.postprocessing", "ScriptPostprocessing",
	};
	final private PropertyContainer propertyContainer;

	public AliasedPropertyAccessor(PropertyContainer propertyContainer) {
		this.propertyContainer = propertyContainer;
	}

	public String[] getTestCaseParts() {
		return getAliasedParts(AliasedPropertyAccessor.testCaseParts, false);
	}

	private String[] getAliasedParts(final String[] parts, boolean includeAliasNames) {
		ArrayList<String> aliasedParts = new ArrayList<>(parts.length*2);
		for(String part : parts){
			if(includeAliasNames) {
				final String aliasName = part + ".alias";
				if (! propertyContainer.get(aliasName).equals(SpecialValue.UNDEFINED)) {
					aliasedParts.add(aliasName);
				}
			}
			else{
				final String alias = getAlias(part);
				aliasedParts.add(alias);
			}
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
		return getAliasedParts(AliasedPropertyAccessor.scriptParts, false);
	}

	public String getFocusPropertyName() {
		return getAlias(testCaseParts[FOCUS_INDEX]);
	}
	
	public boolean isPart(String name){
		return Arrays.asList(AliasedPropertyAccessor.testCaseParts).contains(name) || Arrays.asList(AliasedPropertyAccessor.scriptParts).contains(name);
	}

	public String[] getTestCaseAliasNames() {
		return getAliasedParts(AliasedPropertyAccessor.testCaseParts, true);
	}

	public String[] getScriptAliasPropertyNames() {
		return getAliasedParts(AliasedPropertyAccessor.scriptParts, true);
	}
	
}
