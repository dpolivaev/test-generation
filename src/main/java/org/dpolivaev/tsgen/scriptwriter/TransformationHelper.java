package org.dpolivaev.tsgen.scriptwriter;

import org.dpolivaev.tsgen.utils.internal.IdConverter;
import org.dpolivaev.tsgen.utils.internal.StringConverter;

public class TransformationHelper {
	public static String camelCaseId(String input){
		return new IdConverter().camelCaseId(input);
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

}