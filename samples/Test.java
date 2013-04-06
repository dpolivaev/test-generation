package some.testpackage;
import org.junit.Test;


public class Test {

    @CoveredRequirements(reqs = {"####"})
    @Test
    public void testCaseName() {
      // Preconditions
      someTestCasePrecondition();
      enterState_REQUIRED_STATE();
      somePreconditionInState();
      
      // Focus
      testCaseFocus(/* x = */ 0);
      
      //Verifications
      someVerificationInState();
      verifyState_STATE_AFTER();
      someVerification();
      
      // Post processing
      somePostProcessing();
    }
}
