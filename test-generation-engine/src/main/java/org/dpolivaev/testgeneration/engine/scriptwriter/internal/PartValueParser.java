package org.dpolivaev.testgeneration.engine.scriptwriter.internal;

public class PartValueParser {
	final private int argumentListStartPosition;
	final private String calledMethod;
	final private String argumentList;

	public PartValueParser(String value) {
		argumentListStartPosition = value.indexOf('(');
		boolean isArgumentListFound = value.endsWith(")")
				&& argumentListStartPosition != -1;

		if (isArgumentListFound) {
			calledMethod = value.substring(0, argumentListStartPosition).trim();
			argumentList = value.substring(argumentListStartPosition + 1,
					value.length() - 1).trim();
		} else {
			calledMethod = value;
			argumentList = null;
		}
	}

	public int getArgumentListStartPosition() {
		return argumentListStartPosition;
	}

	public String getCalledMethod() {
		return calledMethod;
	}

	public String[] getArgumentList() {
		if(argumentList != null && ! argumentList.isEmpty())
			return argumentList.split("\\s*,\\s*");
		else
			return new String[]{};
	}
}