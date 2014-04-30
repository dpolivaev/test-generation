package org.dpolivaev.testgeneration.engine.java;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public final class ExceptionMatcher extends BaseMatcher<Runnable> {
	public static Matcher<? super Runnable> throwsException(final Class<? extends Throwable> expectedException) {
		return new ExceptionMatcher(expectedException);
	}
	private final Class<? extends Throwable> expectedException;
	private Class<? extends Exception> thrownException;

	public ExceptionMatcher(Class<? extends Throwable> expectedException) {
		this.expectedException = expectedException;
	}

	@Override
	public boolean matches(Object object) {
		Runnable runnable = (Runnable) object;
		try{
			runnable.run();
			return false;
		}
		catch (Exception e){
			thrownException = e.getClass();
			return expectedException.isAssignableFrom(thrownException);
		}
	}

	@Override
	public void describeMismatch(Object item, Description description) {
	    description.appendText("was ");
	    if(thrownException != null)
	    	description.appendValue(thrownException);
	    else
	    	description.appendText("not thrown");
	}

	@Override
	public void describeTo(Description description) {
		description.appendValue(expectedException);
	}
}