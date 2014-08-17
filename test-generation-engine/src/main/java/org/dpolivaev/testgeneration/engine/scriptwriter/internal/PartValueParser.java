package org.dpolivaev.testgeneration.engine.scriptwriter.internal;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dpolivaev.testgeneration.engine.ruleengine.PropertyContainer;
import org.dpolivaev.testgeneration.engine.scriptwriter.IllegalStepValueException;

public class PartValueParser {
	private static final String METHOD_PATTERN = "([^\"(;]+)";
	private static final String ARGLIST_PATTERN = "(?:\\(([^;]*)\\))?";
	private static final String COMMENT_PATTERN = "(?:\\s*;(.*))?";
	private final static Pattern METHOD_CALL = Pattern.compile("(?:" + METHOD_PATTERN + ARGLIST_PATTERN + ")?" + COMMENT_PATTERN + "\\s*", Pattern.DOTALL);
	final private String calledMethod;
	final private String argumentList;
	final private ArrayList<String> methodPropertyList;
	final private String comment;
	final private PropertyContainer propertyContainer;

	public PartValueParser(PropertyContainer propertyContainer, String value) {
		this.propertyContainer = propertyContainer;
		methodPropertyList = new ArrayList<>();
		final Matcher matcher = METHOD_CALL.matcher(value);
		if(matcher.matches()){
			calledMethod = method(notNull(matcher.group(1)));
			argumentList = notNull(matcher.group(2)).trim();
			comment = notNull(matcher.group(3)).trim();
		}
		else{
			throw new IllegalStepValueException(value);
		}
	}

	static private Pattern PROPERTY = Pattern.compile(":\\w+(?:\\.\\w+|#\\d+)*");
	private String method(String value) {
		value = value.trim();
		Matcher matcher = PROPERTY.matcher(value);
		matcher.reset();
        boolean result = matcher.find();
        if (result) {
            StringBuffer sb = new StringBuffer();
            do {
            	String group = matcher.group();
            	methodPropertyList.add(group);
				String propertyName = group.substring(1);
				String propertyValue = propertyContainer.get(propertyName).toString();
				matcher.appendReplacement(sb, Matcher.quoteReplacement(propertyValue));
                result = matcher.find();
            } while (result);
            matcher.appendTail(sb);
            return sb.toString();
        }
		return value;
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

	public String[] getMethodPropertyList() {
		return methodPropertyList.toArray(new String[0]);
	}
}