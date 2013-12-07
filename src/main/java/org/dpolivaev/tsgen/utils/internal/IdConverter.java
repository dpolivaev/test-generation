package org.dpolivaev.tsgen.utils.internal;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IdConverter{
	private static final Pattern invalidCharacters = Pattern.compile("[^A-Za-z0-9_]+");
	
	public String camelCaseId(String input) {
		String output = input;
		output = removeInvalidCharacters(output);
		output = makeFirstToNotNumber(output);
		return output;
	}

	private String makeFirstToNotNumber(String validCharacterString) {
		if(validCharacterString.isEmpty() || ! Character.isDigit(validCharacterString.charAt(0)))
			return validCharacterString;
		else
			return "_" + validCharacterString;
	}

	private String removeInvalidCharacters(String input) {
		Matcher matcher = invalidCharacters.matcher(input);
		matcher.reset();
		if (matcher.find()) {
		    StringBuffer sb = new StringBuffer();
		    matcher.appendReplacement(sb, "");
	        while (matcher.find()){
		    	int nextPosition = sb.length();
		    	matcher.appendReplacement(sb, "");
		        transformCharactersAfterReplacement(sb, nextPosition);
		    }
	    	int nextPosition = sb.length();
		    matcher.appendTail(sb);
		    transformCharactersAfterReplacement(sb, nextPosition);
		    return sb.toString();
		}
		return input;
	}
	
	private void transformCharactersAfterReplacement(StringBuffer sb, int index) {
		if(index < sb.length()) {
			if(index > 1){
				char charBeforeReplacement = sb.charAt(index-1);
				if(Character.isUpperCase(charBeforeReplacement)){
					sb.insert(index, '_');
					return;
				}
			}
			char charAfterReplacement = sb.charAt(index);
			sb.setCharAt(index, Character.toUpperCase(charAfterReplacement));
		}
	}

	public String lowerFirstCamelCaseId(String input) {
		String validCharacterString = camelCaseId(input);
		if(validCharacterString.isEmpty() || ! Character.isUpperCase(validCharacterString.charAt(0)))
			return validCharacterString;
		else {
			StringBuilder output = new StringBuilder(validCharacterString.length());
			output.append(Character.toLowerCase(validCharacterString.charAt(0)));
			output.append(validCharacterString.subSequence(1, validCharacterString.length()));
			return output.toString();
		}
	}

	public String upperFirstCamelCaseId(String input) {
		String validCharacterString = camelCaseId(input);
		if(validCharacterString.isEmpty() || ! Character.isLowerCase(validCharacterString.charAt(0)))
			return validCharacterString;
		else {
			StringBuilder output = new StringBuilder(validCharacterString.length());
			output.append(Character.toUpperCase(validCharacterString.charAt(0)));
			output.append(validCharacterString.subSequence(1, validCharacterString.length()));
			return output.toString();
		}
	}

	public String snakeLowerCaseId(String invalidId) {
		String output = camelCaseId(invalidId);
		output = replaceUpperCaseByUnderscore(output);
		return output;
	}

	private String replaceUpperCaseByUnderscore(String input) {
		StringBuilder sb = new StringBuilder();
		boolean lastCharacterWasLowerCase = false;
		for(char c : input.toCharArray()){
			char lowerCaseCharacter = Character.toLowerCase(c);
			boolean lowerCaseCharacterFound = c == lowerCaseCharacter;
			if(lastCharacterWasLowerCase && ! lowerCaseCharacterFound)
				sb.append('_');
			sb.append(lowerCaseCharacter);
			lastCharacterWasLowerCase = lowerCaseCharacterFound;
		}
		return sb.toString();
	}

	public String snakeUpperCaseId(String invalidId) {
		return snakeLowerCaseId(invalidId).toUpperCase();
	}
}