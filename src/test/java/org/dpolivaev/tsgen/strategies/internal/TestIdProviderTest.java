package org.dpolivaev.tsgen.strategies.internal;

import static org.junit.Assert.*;

import org.dpolivaev.tsgen.strategies.internal.TestIdProvider;
import org.dpolivaev.tsgen.utils.internal.IdConverter;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

public class TestIdProviderTest {
	final static String VALUE_NAME_SEPARATOR_CHAR = "\u001F";
	final static String PROPERTY_SEPARATOR_CHAR = "\u001E";
	
	private void checkCamelCaseId(String invalidId, String validId) {
		String javaId = new IdConverter().camelCaseId(invalidId);
		assertThat(javaId, CoreMatchers.equalTo(validId));
	}
	
	private void check_snakeCaseId(String invalidId, String validId) {
		String javaId = new IdConverter().snakeCaseId(invalidId);
		assertThat(javaId, CoreMatchers.equalTo(validId));
	}

	@Test
	public void onePropertyString() {
		TestIdProvider idProvider = new TestIdProvider("=", " ");
		String id = idProvider.idFrom("x" + VALUE_NAME_SEPARATOR_CHAR+ "y");
		assertThat(id, CoreMatchers.equalTo("x=y"));
	}

	@Test
	public void twoPropertyString() {
		TestIdProvider idProvider = new TestIdProvider("=", " ");
		String id = idProvider.idFrom("x" + VALUE_NAME_SEPARATOR_CHAR+ "1" + PROPERTY_SEPARATOR_CHAR + "y" + VALUE_NAME_SEPARATOR_CHAR+ "2");
		assertThat(id, CoreMatchers.equalTo("x=1 y=2"));
	}

	@Test
	public void removesTrue() {
		TestIdProvider idProvider = new TestIdProvider("=", " ");
		String id = idProvider.idFrom("x" + VALUE_NAME_SEPARATOR_CHAR+ "true" + PROPERTY_SEPARATOR_CHAR + "y" + VALUE_NAME_SEPARATOR_CHAR+ "TRUE");
		assertThat(id, CoreMatchers.equalTo("x y"));
	}

	@Test
	public void removesFalse() {
		TestIdProvider idProvider = new TestIdProvider("=", " ");
		String id = idProvider.idFrom("x" + VALUE_NAME_SEPARATOR_CHAR+ "false" + PROPERTY_SEPARATOR_CHAR + "y" + VALUE_NAME_SEPARATOR_CHAR+ "FALSE");
		assertThat(id, CoreMatchers.equalTo(""));
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
	public void givenUpperCaseCharacters_snakeCaseId_makesSnakeCase() throws Exception {
		check_snakeCaseId("A", "a");
	}
	
	@Test
	public void givenUpperCaseAfterLowerCase_snakeCaseId_insertsUnderscore() throws Exception {
		check_snakeCaseId("aA", "a_a");
	}

	@Test
	public void givenUpperCaseAfterUpperCase_snakeCaseId_insertsNoUnderscore() throws Exception {
		check_snakeCaseId("AA", "aa");
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
