package org.dpolivaev.testgeneration.engine.strategies.internal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import org.dpolivaev.testgeneration.engine.ruleengine.PropertyContainer;
import org.dpolivaev.testgeneration.engine.scriptwriter.internal.PartValueParser;
import org.junit.Before;
import org.junit.Test;

public class PartValueParserTest {
	private GivenAssignments givenAssignments;
	private PropertyContainer assignments() {
		return givenAssignments.getAssignments();
	}
	
	@Before
	public void setup(){
		givenAssignments = new GivenAssignments();
	}
	@Test
	public void onlyMethod() {
		final PartValueParser parser = new PartValueParser(assignments(), "method");
		assertThat(parser.getCalledMethod(), equalTo("method"));
	}

	@Test
	public void commentedMethodName() {
		final PartValueParser parser = new PartValueParser(assignments(), "method ; comment");
		assertThat(parser.getCalledMethod(), equalTo("method"));
	}

	@Test
	public void commentedMethodComment() {
		final PartValueParser parser = new PartValueParser(assignments(), "method ; comment");
		assertThat(parser.getComment(), equalTo("comment"));
	}

}
