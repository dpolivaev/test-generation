package login;

import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;

import org.dpolivaev.testgeneration.engine.java.Description;
import org.dpolivaev.testgeneration.engine.java.Coverage;
import org.dpolivaev.testgeneration.engine.java.GoalCoverage;

import static login.LoginTestDriver.Page.*;
import static login.LoginTestDriver.EMail.*;
import static login.LoginTestDriver.Password.*;
import static login.LoginTestDriver.Protocol.*;
import login.LoginTestDriver;
import static login.LoginTestDriver.*;

@SuppressWarnings("unused")
public class LoginFormat {
	LoginTestDriver driver = new LoginTestDriver();
	
	@Description("")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="R2", coverage={"entered password is not visible"})
		}
	)
	@Test
	public void test001_enterPassword() throws Exception {
		
	// Precondition 1
		driver.goToPage(/* page */ LOGIN_PAGE);
	// Focus 1
		driver.enterPassword(/* password */ VALID_PASSWORD);
	// Verification 1
		driver.enteredPasswordIsNotVisible();
	}
	
}
