package org.dpolivaev.testgeneration.engine.utils.internal;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringWithNumbersComparator{
	final static Pattern NUMBER_PATTERN = Pattern.compile("\\d+");
	
	public static int compare(String o1, String o2) {
		final Matcher numberMatcher1 = NUMBER_PATTERN.matcher(o1);
		final Matcher numberMatcher2 = NUMBER_PATTERN.matcher(o2);
		int comparedCharacterNumber = 0;
		while(numberMatcher1.find() && numberMatcher2.find()){
			final String prefix1 = o1.substring(comparedCharacterNumber, numberMatcher1.start());
			final String prefix2 = o2.substring(comparedCharacterNumber, numberMatcher2.start());
			final int stringComparisonResult = prefix1.compareTo(prefix2);
			if(stringComparisonResult != 0)
				return stringComparisonResult;
			final int numberComparisonResult = Integer.valueOf(numberMatcher1.group()).compareTo(Integer.valueOf(numberMatcher2.group()));
			if(numberComparisonResult != 0)
				return numberComparisonResult;
			comparedCharacterNumber = numberMatcher1.end();
			int comparedCharacterNumber2 = numberMatcher2.end();
			if(comparedCharacterNumber != comparedCharacterNumber2)
				return Integer.valueOf(comparedCharacterNumber).compareTo(comparedCharacterNumber2);
		}
		return o1.substring(comparedCharacterNumber).compareTo(o2.substring(comparedCharacterNumber));
	}

}
