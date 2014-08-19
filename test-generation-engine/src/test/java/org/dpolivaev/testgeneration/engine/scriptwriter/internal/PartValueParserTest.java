package org.dpolivaev.testgeneration.engine.scriptwriter.internal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import org.dpolivaev.testgeneration.engine.ruleengine.PropertyContainer;
import org.dpolivaev.testgeneration.engine.scriptwriter.IllegalStepValueException;
import org.dpolivaev.testgeneration.engine.scriptwriter.internal.PartValueParser;
import org.dpolivaev.testgeneration.engine.strategies.internal.GivenAssignments;
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
	public void onlyMethodWithQuotes() {
		final PartValueParser parser = new PartValueParser(assignments(), "method \"with quotes\"");
		assertThat(parser.getCalledMethod(), equalTo("method \"with quotes\""));
	}

	@Test
	public void methodWithParametersName() {
		final PartValueParser parser = new PartValueParser(assignments(), "method(x:1)");
		assertThat(parser.getCalledMethod(), equalTo("method"));
	}

	@Test
	public void methodWithParametersParameters() {
		final PartValueParser parser = new PartValueParser(assignments(), "method(x:1)");
		assertThat(parser.getArgumentList(), equalTo(new String[]{"x:1"}));
	}

	@Test
	public void commentedMethodName() {
		final PartValueParser parser = new PartValueParser(assignments(), "method ; comment");
		assertThat(parser.getCalledMethod(), equalTo("method"));
	}

	@Test
	public void commentedMethodWithParametersName() {
		final PartValueParser parser = new PartValueParser(assignments(), "method(parameter:1) ; comment");
		assertThat(parser.getCalledMethod(), equalTo("method"));
	}
	
	@Test
	public void commentedMethodWithParametersComment() {
		final PartValueParser parser = new PartValueParser(assignments(), "method(parameter:1) ; comment");
		assertThat(parser.getComment(), equalTo("comment"));
	}
	
	@Test
	public void commentedMethodComment() {
		final PartValueParser parser = new PartValueParser(assignments(), "method ; comment");
		assertThat(parser.getComment(), equalTo("comment"));
	}

	@Test
	public void onlyComment() {
		final PartValueParser parser = new PartValueParser(assignments(), " ; comment");
		assertThat(parser.getCalledMethod(), equalTo(""));
	}

	@Test
	public void onlyCommentComment() {
		final PartValueParser parser = new PartValueParser(assignments(), "; comment");
		assertThat(parser.getComment(), equalTo("comment"));
	}

	@Test
	public void methodContainingProperty() {
		givenAssignments.given("property", "content");
		final PartValueParser parser = new PartValueParser(assignments(), "method :property");
		assertThat(parser.getCalledMethod(), equalTo("method content"));
	}
	
	@Test
	public void propertiesInMethod() {
		givenAssignments.given("property", "content");
		final PartValueParser parser = new PartValueParser(assignments(), "method :property");
		assertThat(new String[]{":property"}, equalTo(parser.getMethodPropertyList()));
	}
	
	@Test(expected=IllegalStepValueException.class)
	public void illegalStepValue(){
		new PartValueParser(assignments(), "method(;)");
		
	}
	@Test
	public void codeFragmentStepValue(){
		PartValueParser parser = new PartValueParser(assignments(), "\"method\"");
		assertThat(parser.getCalledMethod(), equalTo("\"method\""));
	}
}
