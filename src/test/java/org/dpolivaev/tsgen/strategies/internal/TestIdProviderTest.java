package org.dpolivaev.tsgen.strategies.internal;

import static org.junit.Assert.*;

import org.dpolivaev.tsgen.strategies.internal.TestIdProvider;
import org.dpolivaev.tsgen.utils.internal.IdConverter;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

public class TestIdProviderTest {
	final static String VALUE_NAME_SEPARATOR_CHAR = "\u001F";
	final static String PROPERTY_SEPARATOR_CHAR = "\u001E";
	
	private void checkJavaId(String invalidId, String validId) {
		String javaId = new IdConverter().javaId(invalidId);
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
	public void givenValidJavaId_returnsSameString() throws Exception {
		String validId = "abc123_";
		checkJavaId(validId, validId);
	}
	@Test
	public void givenNonAlphaNumericCharactersRemovesThem() throws Exception {
		checkJavaId("abc$123_", "abc123_");
	}

	@Test
	public void givenNonAlphaNumericCharactersAfterUpperCaseLetterInsertsUnderscoreBetweenFollowingCharacter() throws Exception {
		checkJavaId("abC$abc", "abC_abc");
		checkJavaId("abC$1bc", "abC_1bc");
	}

	@Test
	public void givenNonAlphaNumericCharactersMakesFollowingLowerCaseLetterUpperCase() throws Exception {
		checkJavaId("abc$abc", "abcAbc");
	}

	@Test
	public void createsLowerCaseJavaId() throws Exception {
		String javaId = new IdConverter().lowerCaseJavaId("ABC§abc");
		assertThat(javaId, CoreMatchers.equalTo("aBC_abc"));
	}

	@Test
	public void createsUpperCaseJavaId() throws Exception {
		String javaId = new IdConverter().upperCaseJavaId("abc§abc");
		assertThat(javaId, CoreMatchers.equalTo("AbcAbc"));
	}

	@Test
	public void insertsUnderscoreIfStartsWithNumber() throws Exception {
		checkJavaId("1", "_1");
	}

}
