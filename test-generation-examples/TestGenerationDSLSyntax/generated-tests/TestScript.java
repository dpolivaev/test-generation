import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;

import org.dpolivaev.testgeneration.engine.java.Description;
import org.dpolivaev.testgeneration.engine.java.Coverage;
import org.dpolivaev.testgeneration.engine.java.GoalCoverage;

@SuppressWarnings("unused")
public class TestScript {
	 driver = new ();
	
	@Test
	public void test001_testGroups() throws Exception {
		
	// Focus 1
		driver.step1();
	// Focus 2
		driver.anotherStep2();
	// Focus 3
		driver.anotherStep3();
	// Focus 4
		driver.step4();
	}
	
	@Test
	public void test002_stepDefinitions() throws Exception {
		
	// Focus 1
		driver.testStepWithoutParameters();
	// Focus 2
	/* some comment */
		driver.commentedTestStepWithoutParameters();
	// Focus 3
		driver.testStepWithFixStringParameter(/* parameter */ "string parameter");
	// Focus 4
		driver.testStepWithFixNumberParameter(/* parameter */ 12345);
	// Focus 5
		driver.testStepWithFixIdentifierParameter(/* parameter */ FIX_PARAMETER_VALUE);
	// Focus 6
		driver.testStepWithPropertyAsParameter(/* property */ PROPERTY_VALUE);
	// Focus 7
	/* some comment */
		driver.commentedTestStepWithPropertyAsParameter(/* property */ PROPERTY_VALUE);
	// Focus 8
	/* some comment distributed
	over multiple lines */
		driver.commentedTestStepWithPropertyAsParameter(/* property */ PROPERTY_VALUE);
	// Focus 9
		driver.testStepWithMultipleParameters(/* parameter */ FIX_PARAMETER_VALUE, 
			/* string */ STRING_PROPERTY);
	// Focus 10
		driver.testStepWithPropertyPropertyValueAsA_stepNamePart();
	// Focus 11
	/* only comment, the step does nothing */
	// Focus 12
		// embedded code fragment
	    	codeFragmentTestStep();
	}
	
}
