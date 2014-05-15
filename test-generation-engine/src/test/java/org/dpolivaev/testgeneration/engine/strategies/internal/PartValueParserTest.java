package org.dpolivaev.testgeneration.engine.strategies.internal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import org.dpolivaev.testgeneration.engine.scriptwriter.internal.PartValueParser;
import org.junit.Test;

public class PartValueParserTest {

	@Test
	public void onlyMethod() {
		final PartValueParser parser = new PartValueParser("method");
		assertThat(parser.getCalledMethod(), equalTo("method"));
	}

	@Test
	public void commentedMethodName() {
		final PartValueParser parser = new PartValueParser("method ; comment");
		assertThat(parser.getCalledMethod(), equalTo("method"));
	}

	@Test
	public void commentedMethodComment() {
		final PartValueParser parser = new PartValueParser("method ; comment");
		assertThat(parser.getComment(), equalTo("comment"));
	}

}
