package org.dpolivaev.testgeneration.engine.scriptwriter.internal;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PartValueParser {
	private static final String METHOD_PATTERN = "([^(;]+)";
	private static final String ARGLIST_PATTERN = "(?:\\(([^;]*)\\))?";
	private static final String COMMENT_PATTERN = "(?:;(.*))?";
	private final static Pattern METHOD_CALL = Pattern.compile(METHOD_PATTERN + ARGLIST_PATTERN + COMMENT_PATTERN + "\\s*", Pattern.DOTALL);
	final private String calledMethod;
	final private String argumentList;
	final private String comment;

	public PartValueParser(String value) {
		final Matcher matcher = METHOD_CALL.matcher(value);
		if(matcher.matches()){
			calledMethod = matcher.group(1).trim();
			argumentList = notNull(matcher.group(2)).trim();
			comment = notNull(matcher.group(3)).trim();
		}
		else{
			calledMethod = value.trim();
			argumentList = "";
			comment = "";
		}
	}

	private String notNull(String string) {
		return string != null ? string : "";
	}

	public String getCalledMethod() {
		return calledMethod;
	}

	public String[] getArgumentList() {
		if(! argumentList.isEmpty())
			return argumentList.split("\\s*,\\s*");
		else
			return new String[]{};
	}

	public String getComment() {
		return comment;
	}
}