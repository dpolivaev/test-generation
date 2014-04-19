package org.dpolivaev.tsgen.scriptwriter;

import org.dpolivaev.tsgen.utils.internal.IdConverter;
import org.dpolivaev.tsgen.utils.internal.StringConverter;

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
	
	public static String literal(String input){
		return new IdConverter().literal(input);
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

}