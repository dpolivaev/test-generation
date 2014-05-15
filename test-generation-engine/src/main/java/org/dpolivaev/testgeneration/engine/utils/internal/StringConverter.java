package org.dpolivaev.testgeneration.engine.utils.internal;

import java.util.regex.Matcher;

public class StringConverter {

	public String javaString(String input) {
		String output = input;
		output = output.replaceAll("\"", "\\\\\"");
		output = output.replaceAll("\n", "\\\\n\"\n+\\\"");
		return '"' + output + '"';		
	}

	public String singleLineComment(String prefix, String text) {
		if(text.isEmpty())
			return text;
		return prefix + text.replaceAll("\n", "\n" + Matcher.quoteReplacement(prefix));
	}

}
