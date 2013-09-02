package ruleengine;

import static org.junit.Assert.*;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.mockito.Mockito;

import ruleengine.impl.Assignments;
import ruleengine.impl.TestIdProvider;
import testutils.TestUtils;

public class StrategyHelperTest {
	final static String RECORD_SEPARATOR = "\u001E";
	final static String UNIT_SEPARATOR = "\u001F";
	@Test
	public void onePropertyString() {
		TestIdProvider idProvider = new TestIdProvider("=", " ");
		String id = idProvider.idFrom("x" + UNIT_SEPARATOR+ "y");
		assertThat(id, CoreMatchers.equalTo("x=y"));
	}

	@Test
	public void twoPropertyString() {
		TestIdProvider idProvider = new TestIdProvider("=", " ");
		String id = idProvider.idFrom("x" + UNIT_SEPARATOR+ "1" + RECORD_SEPARATOR + "y" + UNIT_SEPARATOR+ "2");
		assertThat(id, CoreMatchers.equalTo("x=1 y=2"));
	}

	@Test
	public void removesTrue() {
		TestIdProvider idProvider = new TestIdProvider("=", " ");
		String id = idProvider.idFrom("x" + UNIT_SEPARATOR+ "true" + RECORD_SEPARATOR + "y" + UNIT_SEPARATOR+ "TRUE");
		assertThat(id, CoreMatchers.equalTo("x y"));
	}

	@Test
	public void removesFalse() {
		TestIdProvider idProvider = new TestIdProvider("=", " ");
		String id = idProvider.idFrom("x" + UNIT_SEPARATOR+ "false" + RECORD_SEPARATOR + "y" + UNIT_SEPARATOR+ "FALSE");
		assertThat(id, CoreMatchers.equalTo(""));
	}

}
