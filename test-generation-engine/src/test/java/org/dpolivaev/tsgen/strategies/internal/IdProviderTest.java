package org.dpolivaev.tsgen.strategies.internal;

import static org.junit.Assert.assertThat;

import org.dpolivaev.tsgen.utils.internal.IdConverter;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

public class IdProviderTest {
	final static String VALUE_NAME_SEPARATOR_CHAR = "\u001F";
	final static String PROPERTY_SEPARATOR_CHAR = "\u001E";
	
	private void checkCamelCaseId(String invalidId, String validId) {
		String javaId = new IdConverter().camelCaseId(invalidId);
		assertThat(javaId, CoreMatchers.equalTo(validId));
	}
	
	private void check_snakeLowerCaseId(String invalidId, String validId) {
		String javaId = new IdConverter().snakeLowerCaseId(invalidId);
		assertThat(javaId, CoreMatchers.equalTo(validId));
	}

	private void check_snakeUpperCaseId(String invalidId, String validId) {
		String javaId = new IdConverter().snakeUpperCaseId(invalidId);
		assertThat(javaId, CoreMatchers.equalTo(validId));
	}

	@Test
	public void givenValidJavaId_camelCaseId_returnsSameString() throws Exception {
		String validId = "abc123_";
		checkCamelCaseId(validId, validId);
	}
	@Test
	public void givenNonAlphaNumericCharacters_camelCaseId_removesThem() throws Exception {
		checkCamelCaseId("abc$123_", "abc123_");
	}

	@Test
	public void givenNonAlphaNumericCharactersAfterUpperCaseLetter_camelCaseId_insertsUnderscoreBetweenFollowingCharacter() throws Exception {
		checkCamelCaseId("abC$abc", "abC_abc");
		checkCamelCaseId("abC$1bc", "abC_1bc");
	}

	@Test
	public void givenNonAlphaNumericCharacters_camelCaseId_makesFollowingLowerCaseLetterUpperCase() throws Exception {
		checkCamelCaseId("abc$abc", "abcAbc");
	}

	@Test
	public void givenUpperCaseCharacters_snakeLowerCaseId_makesLowerCase() throws Exception {
		check_snakeLowerCaseId("A", "a");
	}
	
	@Test
	public void givenUpperCaseAfterLowerCase_snakeLowerCaseId_insertsUnderscore() throws Exception {
		check_snakeLowerCaseId("aA", "a_a");
	}

	@Test
	public void givenUpperCaseAfterUpperCase_snakeLowerCaseId_insertsNoUnderscore() throws Exception {
		check_snakeLowerCaseId("AA", "aa");
	}

	@Test
	public void givenUpperCaseAfterLowerCase_snakeUpperCaseId_insertsUnderscore() throws Exception {
		check_snakeUpperCaseId("aA", "A_A");
	}

	@Test
	public void createsLowerFirstCamelCaseId() throws Exception {
		String javaId = new IdConverter().lowerFirstCamelCaseId("ABC§abc");
		assertThat(javaId, CoreMatchers.equalTo("aBC_abc"));
	}

	@Test
	public void createsUpperFirstCamelCaseId() throws Exception {
		String javaId = new IdConverter().upperFirstCamelCaseId("abc§abc");
		assertThat(javaId, CoreMatchers.equalTo("AbcAbc"));
	}

	@Test
	public void insertsUnderscoreIfStartsWithNumber() throws Exception {
		checkCamelCaseId("1", "_1");
	}

}