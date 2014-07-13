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
public class LoginSubmit {
	LoginTestDriver driver = new LoginTestDriver();
	
	@Description("")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="R1", coverage={"logging in with protocol HTTP not allowed"}),
			@GoalCoverage(goal = "requirement coverage", item="R4", coverage={"check email and password fields after failed log-in"})
		}
	)
	@Test
	public void test001_submitHTTP() throws Exception {
		
	// Precondition 1
		driver.goToPage(/* page */ LOGIN_PAGE);
	// Precondition 2
		driver.enterMailAddress(/* email */ VALID_MAIL);
	// Precondition 3
		driver.enterPassword(/* password */ VALID_PASSWORD);
	// Focus 1
		driver.submit(/* protocol */ HTTP);
	// Verification 1
		driver.checkPage(/* pageAfterSubmit */ LOGIN_PAGE);
	// Verification 2
		driver.passwordFieldIsEmpty();
	// Verification 3
		driver.emailFieldEqualsTo(/* email */ VALID_MAIL);
	}
	
	@Description("")
	@Coverage(
		first = {
			@GoalCoverage(goal = "requirement coverage", item="R3", coverage={"log in successful"})
		}
	)
	@Test
	public void test002_VALID_MAIL_VALID_PASSWORD_submitHTTPS() throws Exception {
		
	// Precondition 1
		driver.goToPage(/* page */ LOGIN_PAGE);
	// Precondition 2
		driver.enterMailAddress(/* email */ VALID_MAIL);
	// Precondition 3
		driver.enterPassword(/* password */ VALID_PASSWORD);
	// Focus 1
		driver.submit(/* protocol */ HTTPS);
	// Verification 1
		driver.checkPage(/* pageAfterSubmit */ LOGIN_SUCCESS_PAGE);
	}
	
	@Description("")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="R4", coverage={"check email and password fields after failed log-in"})
		}
	)
	@Test
	public void test003_VALID_MAIL_INVALID_PASSWORD_submitHTTPS() throws Exception {
		
	// Precondition 1
		driver.goToPage(/* page */ LOGIN_PAGE);
	// Precondition 2
		driver.enterMailAddress(/* email */ VALID_MAIL);
	// Precondition 3
		driver.enterPassword(/* password */ INVALID_PASSWORD);
	// Focus 1
		driver.submit(/* protocol */ HTTPS);
	// Verification 1
		driver.checkPage(/* pageAfterSubmit */ LOGIN_PAGE);
	// Verification 2
		driver.passwordFieldIsEmpty();
	// Verification 3
		driver.emailFieldEqualsTo(/* email */ VALID_MAIL);
	}
	
	@Description("")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="R4", coverage={"check email and password fields after failed log-in"})
		}
	)
	@Test
	public void test004_VALID_MAIL_NOT_ENTERED_PASSWORD_submitHTTPS() throws Exception {
		
	// Precondition 1
		driver.goToPage(/* page */ LOGIN_PAGE);
	// Precondition 2
		driver.enterMailAddress(/* email */ VALID_MAIL);
	// Precondition 3
		driver.enterPassword(/* password */ NOT_ENTERED_PASSWORD);
	// Focus 1
		driver.submit(/* protocol */ HTTPS);
	// Verification 1
		driver.checkPage(/* pageAfterSubmit */ LOGIN_PAGE);
	// Verification 2
		driver.passwordFieldIsEmpty();
	// Verification 3
		driver.emailFieldEqualsTo(/* email */ VALID_MAIL);
	}
	
	@Description("")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="R4", coverage={"check email and password fields after failed log-in"})
		}
	)
	@Test
	public void test005_INVALID_MAIL_submitHTTPS() throws Exception {
		
	// Precondition 1
		driver.goToPage(/* page */ LOGIN_PAGE);
	// Precondition 2
		driver.enterMailAddress(/* email */ INVALID_MAIL);
	// Precondition 3
		driver.enterPassword(/* password */ VALID_PASSWORD);
	// Focus 1
		driver.submit(/* protocol */ HTTPS);
	// Verification 1
		driver.checkPage(/* pageAfterSubmit */ LOGIN_PAGE);
	// Verification 2
		driver.passwordFieldIsEmpty();
	// Verification 3
		driver.emailFieldEqualsTo(/* email */ INVALID_MAIL);
	}
	
	@Description("")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="R4", coverage={"check email and password fields after failed log-in"})
		}
	)
	@Test
	public void test006_NOT_ENTERED_MAIL_VALID_PASSWORD_submitHTTPS() throws Exception {
		
	// Precondition 1
		driver.goToPage(/* page */ LOGIN_PAGE);
	// Precondition 2
		driver.enterMailAddress(/* email */ NOT_ENTERED_MAIL);
	// Precondition 3
		driver.enterPassword(/* password */ VALID_PASSWORD);
	// Focus 1
		driver.submit(/* protocol */ HTTPS);
	// Verification 1
		driver.checkPage(/* pageAfterSubmit */ LOGIN_PAGE);
	// Verification 2
		driver.passwordFieldIsEmpty();
	// Verification 3
		driver.emailFieldEqualsTo(/* email */ NOT_ENTERED_MAIL);
	}
	
	@Description("")
	@Coverage(
		again = {
			@GoalCoverage(goal = "requirement coverage", item="R4", coverage={"check email and password fields after failed log-in"})
		}
	)
	@Test
	public void test007_NOT_ENTERED_MAIL_NOT_ENTERED_PASSWORD_submitHTTPS() throws Exception {
		
	// Precondition 1
		driver.goToPage(/* page */ LOGIN_PAGE);
	// Precondition 2
		driver.enterMailAddress(/* email */ NOT_ENTERED_MAIL);
	// Precondition 3
		driver.enterPassword(/* password */ NOT_ENTERED_PASSWORD);
	// Focus 1
		driver.submit(/* protocol */ HTTPS);
	// Verification 1
		driver.checkPage(/* pageAfterSubmit */ LOGIN_PAGE);
	// Verification 2
		driver.passwordFieldIsEmpty();
	// Verification 3
		driver.emailFieldEqualsTo(/* email */ NOT_ENTERED_MAIL);
	}
	
}
