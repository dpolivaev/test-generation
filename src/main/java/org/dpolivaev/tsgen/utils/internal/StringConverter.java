package org.dpolivaev.tsgen.utils.internal;

public class StringConverter {

	public String javaString(String input) {
		String output = input;
		output = output.replaceAll("\"", "\\\\\"");
		output = output.replaceAll("\n", "\\\\n\"\n+\\\"");
		return '"' + output + '"';		
	}

}
