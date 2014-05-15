package org.dpolivaev.testgeneration.engine.strategies.internal;

import static org.junit.Assert.*;

import org.dpolivaev.testgeneration.engine.utils.internal.StringConverter;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

public class StringConverterTest {

	@Test
	public void convertsEmptyString_returnsQuotedInput() {
		String input = "";
		String output = new StringConverter().javaString(input);
		assertThat(output, CoreMatchers.equalTo(quote(input)));
	}

	private String quote(String input) {
		return '"' + input + '"';
	}

	@Test
	public void convertsStringWithoutCR_returnsQuotedInput() {
		String input = "aaa";
		String output = new StringConverter().javaString(input);
		assertThat(output, CoreMatchers.equalTo(quote(input)));
	}
	
	@Test
	public void convertsStringWithCR_returnsInput() {
		String input = "a\nb";
		String output = new StringConverter().javaString(input);
		assertThat(output, CoreMatchers.equalTo("\"a\\n\"\n+\"b\""));
	}
	
	@Test
	public void escapesQuotes() {
		String input = "a\"b";
		String output = new StringConverter().javaString(input);
		assertThat(output, CoreMatchers.equalTo("\"a\\\"b\""));
	}

	@Test
	public void convertsStringWithCrAndQuotes() {
		String input = "a\nb\"";
		String output = new StringConverter().javaString(input);
		assertThat(output, CoreMatchers.equalTo("\"a\\n\"\n+\"b\\\"\""));
	}
	
	@Test
	public void convertsEmptyStringToComment_returnsEmptyString() {
		String input = "";
		String output = new StringConverter().singleLineComment("//", input);
		assertThat(output, CoreMatchers.equalTo(""));
	}

	@Test
	public void convertsOneLineStringToComment() {
		String input = "comment";
		String output = new StringConverter().singleLineComment("//", input);
		assertThat(output, CoreMatchers.equalTo("//comment"));
	}

	@Test
	public void convertsTwoLineStringToComment() {
		String input = "comment1\ncomment2";
		String output = new StringConverter().singleLineComment("//", input);
		assertThat(output, CoreMatchers.equalTo("//comment1\n//comment2"));
	}


}
