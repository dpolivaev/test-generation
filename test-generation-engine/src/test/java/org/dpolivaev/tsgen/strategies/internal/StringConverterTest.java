package org.dpolivaev.tsgen.strategies.internal;

import static org.junit.Assert.*;

import org.dpolivaev.tsgen.utils.internal.StringConverter;
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
	
}
