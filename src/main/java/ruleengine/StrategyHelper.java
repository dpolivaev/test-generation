package ruleengine;

import ruleengine.impl.IdConverter;
import ruleengine.impl.TestIdProvider;

public class StrategyHelper {

	public static Strategy id(String propertyName) {
		return TestIdProvider.strategy(propertyName);
	}
	
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
