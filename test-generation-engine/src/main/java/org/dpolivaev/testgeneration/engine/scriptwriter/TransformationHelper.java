package org.dpolivaev.testgeneration.engine.scriptwriter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dpolivaev.testgeneration.engine.utils.internal.IdConverter;
import org.dpolivaev.testgeneration.engine.utils.internal.StringConverter;

public class TransformationHelper {
	public static String camelCaseId(String input){
		return new IdConverter().camelCaseId(input);
	}
	
	public static String snakeLowerCaseId(String input){
		return new IdConverter().snakeLowerCaseId(input);
	}
	
	public static String snakeUpperCaseId(String input){
		return new IdConverter().snakeUpperCaseId(input);
	}
	
	public static String lowerFirstCamelCaseId(String input){
		return new IdConverter().lowerFirstCamelCaseId(input);
	}
	
	public static String upperFirstCamelCaseId(String input){
		return new IdConverter().upperFirstCamelCaseId(input);
	}
	
	public static String javaString(String input){
		return new StringConverter().javaString(input);
	}
	
	public static String singleLineComment(String prefix, String input){
		return new StringConverter().singleLineComment(prefix, input);
	}

	public static String literal(String input){
		return new IdConverter().literal(input);
	}
	
	public static String replaceAll(String input, String regex, String replacement){
		return input.replaceAll(regex, replacement);
	}

	public static String substringBeforeLast(String input, String delimiter){
		final int lastIndexOfDelimiter = input.lastIndexOf(delimiter);
		if(lastIndexOfDelimiter <= 0)
			return "";
		return input.substring(0, lastIndexOfDelimiter);
	}

	public static String substringAfterLast(String input, String delimiter){
			final int lastIndexOfDelimiter = input.lastIndexOf(delimiter);
			if(lastIndexOfDelimiter <= 0)
				return input;
			return input.substring(lastIndexOfDelimiter + 1);
	}
	
	private static Pattern VALID_METHOD_CALL = Pattern.compile("[A-Za-z_]\\w+\\.[A-Za-z_]\\w+");
	private static Pattern EXPLICIT_DRIVER_AND_CALL = Pattern.compile("(.*?)<<(.*)");
	
	public static String methodCall(String defaultDriver, String call){
		if(VALID_METHOD_CALL.matcher(call).matches())
			return call;
		Matcher explicitDriverMatcher = EXPLICIT_DRIVER_AND_CALL.matcher(call);
		if(explicitDriverMatcher.matches())
			return camelCaseId(explicitDriverMatcher.group(1).trim()) + '.' + camelCaseId(explicitDriverMatcher.group(2).trim());
		return camelCaseId(defaultDriver) + '.' + camelCaseId(call);
	}

}