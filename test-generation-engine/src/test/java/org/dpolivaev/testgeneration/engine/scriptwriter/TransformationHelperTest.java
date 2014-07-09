package org.dpolivaev.testgeneration.engine.scriptwriter;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import org.dpolivaev.testgeneration.engine.scriptwriter.TransformationHelper;
import org.junit.Test;

public class TransformationHelperTest {

	@Test
	public void substringBeforeNotContainedDelimiter() {
		final String someString = "abc";
		final String notContainedString = ".";
		final String substringBeforeLast = TransformationHelper.substringBeforeLast(someString, notContainedString);
		assertThat(substringBeforeLast, equalTo(""));
	}

	@Test
	public void substringBeforeContainedDelimiter() {
		final String someString = "a.b.c";
		final String containedString = ".";
		final String substringBeforeLast = TransformationHelper.substringBeforeLast(someString, containedString);
		assertThat(substringBeforeLast, equalTo("a.b"));
	}


	@Test
	public void substringAfterNotContainedDelimiter() {
		final String someString = "abc";
		final String notContainedString = ".";
		final String substringBeforeLast = TransformationHelper.substringAfterLast(someString, notContainedString);
		assertThat(substringBeforeLast, equalTo("abc"));
	}

	@Test
	public void substringAfterContainedDelimiter() {
		final String someString = "a.b.c";
		final String containedString = ".";
		final String substringBeforeLast = TransformationHelper.substringAfterLast(someString, containedString);
		assertThat(substringBeforeLast, equalTo("c"));
	}

	@Test
	public void emptySubstringAfterContainedDelimiter() {
		final String someString = "a.b.";
		final String containedString = ".";
		final String substringBeforeLast = TransformationHelper.substringAfterLast(someString, containedString);
		assertThat(substringBeforeLast, equalTo(""));
	}
	
	@Test 
	public void validMethodCall(){
		String methodCall = TransformationHelper.methodCall(null, "Object.Method");
		assertThat(methodCall, equalTo("Object.Method"));
	}
	
	@Test 
	public void explicitDriverandCall(){
		String methodCall = TransformationHelper.methodCall(null, "some object << some method");
		assertThat(methodCall, equalTo("someObject.someMethod"));
	}
	
	@Test 
	public void defaultDriverMethodCall(){
		String methodCall = TransformationHelper.methodCall("default driver", "some method");
		assertThat(methodCall, equalTo("defaultDriver.someMethod"));
	}
}
