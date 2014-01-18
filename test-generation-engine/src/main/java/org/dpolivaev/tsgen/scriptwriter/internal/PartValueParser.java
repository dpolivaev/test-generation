package org.dpolivaev.tsgen.scriptwriter.internal;

public class PartValueParser {
	final private int argumentListStartPosition;
	final private String calledMethod;
	final private boolean isArgumentListFound;
	final private String argumentList;

	public PartValueParser(String value) {
		argumentListStartPosition = value.indexOf('(');
		isArgumentListFound = value.endsWith(")")
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

	public boolean isArgumentListFound() {
		return isArgumentListFound;
	}

	public String[] getArgumentList() {
		return argumentList.split("\\s*,\\s*");
	}
}