package org.dpolivaev.tsgen.scriptwriter;

import org.dpolivaev.tsgen.utils.internal.IdConverter;
import org.dpolivaev.tsgen.utils.internal.StringConverter;

public class TransformationHelper {
	public static String javaId(String input){
		return new IdConverter().javaId(input);
	}
	
	public static String lowerCaseJavaId(String input){
		return new IdConverter().lowerCaseJavaId(input);
	}
	
	public static String upperCaseJavaId(String input){
		return new IdConverter().upperCaseJavaId(input);
	}
	
	public static String javaString(String input){
		return new StringConverter().javaString(input);
	}

}