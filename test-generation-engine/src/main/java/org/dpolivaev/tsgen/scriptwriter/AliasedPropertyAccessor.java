package org.dpolivaev.tsgen.scriptwriter;

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
		return getAliasedParts(AliasedPropertyAccessor.testCaseParts);
	}

	private String[] getAliasedParts(final String[] parts) {
		String[] aliasedParts = new String[parts.length];
		int index = 0;
		for(String part : parts){
			final String alias = getAlias(part);
			aliasedParts[index++] = alias;
		}
		return aliasedParts;
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
		return getAliasedParts(AliasedPropertyAccessor.scriptParts);
	}

	public String getPreconditionPropertyName() {
		return getTestCaseParts()[PRECONDITION_INDEX];
	}
	
	public String getFocusPropertyName() {
		return getTestCaseParts()[FOCUS_INDEX];
	}
	
	public String getVerificationPropertyName() {
		return getTestCaseParts()[VERIFICATION_INDEX];
	}
	
	public String getPostprocessingPropertyName() {
		return getTestCaseParts()[POSTPOCESSING_INDEX];
	}
	
	public String getScriptPreconditionPropertyName() {
		return getScriptParts()[SCRIPT_PRECONDITION_INDEX];
	}
	
	public String getScriptPostprocessingPropertyName() {
		return getScriptParts()[SCRIPT_POSTPOCESSING_INDEX];
	}
	
	public boolean isPart(String name){
		return Arrays.asList(AliasedPropertyAccessor.testCaseParts).contains(name) || Arrays.asList(AliasedPropertyAccessor.scriptParts).contains(name);
	}
	
}
