package org.dpolivaev.tsgen.scriptproducer;

import org.dpolivaev.tsgen.strategies.internal.IdConverter;

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

}