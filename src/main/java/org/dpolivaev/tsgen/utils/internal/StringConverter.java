package org.dpolivaev.tsgen.utils.internal;

import java.util.regex.Pattern;

public class StringConverter {

	public String javaString(String input) {
		String output = input;
		output = output.replaceAll("\"", "\\\\\"");
		output = output.replaceAll("\n", "\\\\n\"\n+\\\"");
		return '"' + output + '"';		
	}

}
