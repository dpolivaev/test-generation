Tutorial : developing tests for a log-in page
===============

![Login-page](login-form.png)

## Test ideas
- Login with valid email address and password,
- Login with invalid email address,
- Login with invalid password,
- Login with http protocol is rejected, https is required.

(Thanks to Sabine Herrmann for her idea)

The revisions committed to this repository corresponds to the tutorial steps. 
**Check out or compare different revisions to see how the tutorial goes.**  

## Step 1 : Create test generation project in eclipse
- Create Java project.
- Add test generation library available after the framework plug-ins are installed in eclipse.
- To compile and execute the generated tests in the same project, add JUnit 4 Library.

## Step 2 : Create a strategy file and run test generation
- Create new file `teststrategy/LoginTestSuite.tsgen`.
- Allow Eclipse to add XText nature to the project.
- Write following configuration into file `LoginTestSuite.tsgen`.
  
file LoginTestSuite.tsgen:

	package login.testgeneration

	import org.dpolivaev.tsgen.strategies.StrategyHelper;

	strategy structure
		let script.precondition.alias be "beforeAll"
		let script.postprocessing.alias be "afterAll"
		let testcase.precondition.alias be "arrange"
		let testcase.focus.alias be "act"
		let testcase.verification.alias be "assert"
		let testcase.postprocessing.alias be "after"

		let default testcase be StrategyHelper.idProvider
		let default testcase.description be StrategyHelper.descriptionProvider

	strategy loginTests
		apply TestStructure.structure

		let script be "login/LoginTest"	
		let script.driver be "login/LoginTestDriver"

	run strategy loginTests
		apply "/java.xslt" output "generated-tests/java" 
 
- Make automatically created directory `src-gen` a project source folder (right-click, use as source folder).
- Right-click on file `LoginTestSuite.tsgen` in the project explorer or in the file editor context menu
and select "Run As", "Generation Task".
- Open generated JUnit test file `generated-tests/login/LoginTest.java`.

## Step 3: Create test driver class
- Make directory `generated-tests` a project source folder (right-click, use as source folder).
- Create new source folder `src-driver` and new class `LoginTestDriver` in package `login`.
- The test should compile without errors now.

##Step 4: First test case: log in with valid email address and password
- Add test steps and their parameters to the strategy file.

file TestStructure.tsgen:

	package login.testgeneration
	import org.dpolivaev.tsgen.strategies.StrategyHelper;

	strategy structure
		let script.precondition.alias be "beforeAll"
		let script.postprocessing.alias be "afterAll"
		let testcase.precondition.alias be "arrange"
		let testcase.focus.alias be "act"
		let testcase.verification.alias be "assert"
		let testcase.postprocessing.alias be "after"

		let default testcase be StrategyHelper.idProvider
		let default testcase.description be StrategyHelper.descriptionProvider


file LoginTestSuite.tsgen:

	package login.testgeneration

	strategy loginTests
		apply structure

		let script be "login/LoginTest"
		let script.driver be "login/LoginTestDriver"

		let page be "LOGIN_PAGE"
		let arrange#1 be "go to page(page)"

		let email be "VALID_MAIL"
		let arrange#2 be "enter mail address(email)"

		let password be "VALID_PASSWORD"
		let arrange#3 be "enter password(password)"

		let act be "submit"

		let pageAfterSubmit be "LOGIN_SUCCESS_PAGE"
		let assert be "checkPage(pageAfterSubmit)"

- Run generation task.
- Observe reported problems because of missing method and constant declarations. 
- Add enum constant declaration to the Driver class

file LoginTestDriver.java:
	package login;

	public class LoginTestDriver {
		public enum Page{LOGIN_PAGE, LOGIN_SUCCESS_PAGE}
		public enum EMail{VALID_MAIL}
		public enum Password{VALID_PASSWORD}
	}

- Add script.imports property to the strategy file

file LoginTestSuite.tsgen:

	strategy loginTests
		apply structure

		let script be "login/LoginTest"
		let script.driver be "login/LoginTestDriver"
		let script.imports be "import static login.LoginTestDriver.Page.*;
							   import static login.LoginTestDriver.EMail.*;
							   import static login.LoginTestDriver.Password.*;"

- Run the test generation again.
- Fix the rest problems adding required methods to the driver class.

file LoginTestDriver.java:

	package login;

	public class LoginTestDriver {
		public enum Page{LOGIN_PAGE, LOGIN_SUCCESS_PAGE}
		public enum EMail{VALID_MAIL}
		public enum Password{VALID_PASSWORD}
		public void submit(){}
		public void checkPage(Page loginSuccessPage) {}
		public void enterMailAddress(EMail validMail) {}
		public void enterPassword(Password validPassword) {}
		public void goToPage(Page loginPage) {}
	}

##Step 5: Test cases: log in with valid, invalid and not entered email address and password
- Create another two test cases with invalid and empty mails and passwords.

file LoginTestSuite.tsgen:

	let arrange#1 be "go to page(page)"
	let arrange#2 be "enter mail address(email)"
	let arrange#3 be "enter password(password)"
	let act be "submit"
	let assert be "checkPage(pageAfterSubmit)"

	let page be "LOGIN_PAGE"

	let email be "VALID_MAIL", "INVALID_MAIL", "NOT_ENTERED_MAIL"
	let password be "VALID_PASSWORD", "INVALID_PASSWORD", "NOT_ENTERED_PASSWORD"

	let pageAfterSubmit be :page
	if :email == "VALID_MAIL" && :password == "VALID_PASSWORD"
		let pageAfterSubmit be "LOGIN_SUCCESS_PAGE"

- Run the generation and inspect the output.
- Add missed identifiers.

file LoginTestDriver.java:

	public class LoginTestDriver {
		public enum Page{LOGIN_PAGE, LOGIN_SUCCESS_PAGE}
		public enum EMail{VALID_MAIL, INVALID_MAIL, NOT_ENTERED_MAIL}
		public enum Password{VALID_PASSWORD, INVALID_PASSWORD, NOT_ENTERED_PASSWORD}

##Step 6. Use of arbitrary type expressions for properties

After defining enumerations representing protocols, passwords and emails they can be used as property values.

	package login.testgeneration

	import org.dpolivaev.tsgen.strategies.StrategyHelper;
	
	import static login.LoginTestDriver.EMail.*;
	import static login.LoginTestDriver.Password.*;
	import static login.LoginTestDriver.Page.*;

Now all quotes can be removed

	let page be LOGIN_PAGE

	let email be VALID_MAIL, INVALID_MAIL, NOT_ENTERED_MAIL
	let password be VALID_PASSWORD, INVALID_PASSWORD, NOT_ENTERED_PASSWORD

	let pageAfterSubmit be :page
	if :email == VALID_MAIL && :password == VALID_PASSWORD
		let pageAfterSubmit be LOGIN_SUCCESS_PAGE

##Step 7: 9 test cases covering all input combinations

- Let the strategy combine all values.

file LoginTestSuite.tsgen:

	let email be VALID_MAIL, INVALID_MAIL, NOT_ENTERED_MAIL {
		let password be VALID_PASSWORD, INVALID_PASSWORD, NOT_ENTERED_PASSWORD
	}
- Run the generation and inspect the output.

##Step 8: 6 test cases covering the most interesting input combinations

- Let the strategy combine all values.

file LoginTestSuite.tsgen:

	let email be 
	VALID_MAIL { let password be VALID_PASSWORD, INVALID_PASSWORD, NOT_ENTERED_PASSWORD }, 
	INVALID_MAIL { let password be VALID_PASSWORD }, 
	NOT_ENTERED_MAIL { let password be VALID_PASSWORD, NOT_ENTERED_PASSWORD }

- Run the generation and inspect the output.

##Step 9: Add tests considering protocols http and https

file LoginTestDriver.java:

	public enum Protocol{HTTP, HTTPS}

file LoginTestSuite.tsgen, imports:

	import static login.LoginTestDriver.Protocol.*;


file LoginTestSuite.tsgen, script imports:

	let script.imports be "import static login.LoginTestDriver.Page.*;
						   import static login.LoginTestDriver.EMail.*;
						   import static login.LoginTestDriver.Password.*;
						   import static login.LoginTestDriver.Protocol.*;"

file LoginTestSuite.tsgen, strategy definition:

	let act be "submit(protocol)"

...

	let protocol be HTTP{
		let email be VALID_MAIL 
		let password be VALID_PASSWORD
	}, 
	HTTPS {
		let email be VALID_MAIL { let password be VALID_PASSWORD, INVALID_PASSWORD, NOT_ENTERED_PASSWORD}, 
		INVALID_MAIL { let password be VALID_PASSWORD}, 
		NOT_ENTERED_MAIL { let password be VALID_PASSWORD, NOT_ENTERED_PASSWORD}
	}

	let pageAfterSubmit be :page
	if :protocol == HTTPS && :email == VALID_MAIL && :password == VALID_PASSWORD
		let pageAfterSubmit be LOGIN_SUCCESS_PAGE

Run generation and fix the driver method signature.

file LoginTestDriver.java:

	public void submit(Protocol protocol){}

##Step 10. Test coverage

Consider the following requirements:

- R1. The page must be treated as sensitive data (https)
- R2. The logon page must use the password field to keep the password from being viewable.
- R3. Submit the form with valid user credentials (registered) the log-in success page should be shown
- R4. Entering invalid email address or password an error message should appear and the password should be removed and the email stays

Currently there are tests for requirements R1. and R3. Test coverage can be evaluated if requirements are referenced by the test strategy as follows:

Requirement R1: 

	let protocol be HTTP{
		let email be VALID_MAIL 
		let password be VALID_PASSWORD
		let [R1] be "logging in with protocol HTTP not allowed"
	}, 

Requirement R3:

	if :protocol == HTTPS && :email == VALID_MAIL && :password == VALID_PASSWORD
		let pageAfterSubmit be LOGIN_SUCCESS_PAGE {
			let [R3] be "log in successful"
		}

Coverage report can be generated by adding keyword `goal` and subsection `report` to the run section.

	run strategy goal loginTests
		apply "/java.xslt" output "generated-tests/java"
		report  "report/testcoverage.xml"

Running the test generation adds test coverage information to the generated tests 
and produces test coverate report in xml format. 

##Step 11. Identifying gaps in requirement coverage
It can be used for identifying gaps in requirement coverage. Particularly if strategy contains requirements which are never assigned they appear in the coverage report.  

	if :act == "enter password(password)" && :password != NOT_ENTERED_PASSWORD && :assert == "password is not visible" 
	    let [R2] be "entered password is not visible"

It produces following element in the test coverage report: 

	<Item name="R2" reached="0">entered password is not visible</Item>

##Step 12. Adding test for requirement R2

Requirement R2 should be checked before the log-in is submitted. Therefore we need a new group of tests not performing the log - in pressing submit button. Fortunately building the new group is easy.

	let arrange#1 be "go to page(page)"
	let page be LOGIN_PAGE

	let act be 
	"submit(protocol)"{
		let arrange#2 be "enter mail address(email)"
		let arrange#3 be "enter password(password)"
		let assert be "checkPage(pageAfterSubmit)"

		let protocol be HTTP{
			let email be VALID_MAIL 
			let password be VALID_PASSWORD
			let [R1] be "logging in with protocol HTTP not allowed"
		}, 
		HTTPS {
			let email be VALID_MAIL { let password be VALID_PASSWORD, INVALID_PASSWORD, NOT_ENTERED_PASSWORD}, 
			INVALID_MAIL { let password be VALID_PASSWORD}, 
			NOT_ENTERED_MAIL { let password be VALID_PASSWORD, NOT_ENTERED_PASSWORD}
		}

		let pageAfterSubmit be :page
		if :protocol == HTTPS && :email == VALID_MAIL && :password == VALID_PASSWORD
			let pageAfterSubmit be LOGIN_SUCCESS_PAGE {
				let [R3] be "log in successful"
			}
	},
	"enter password(password)"{
		let password be VALID_PASSWORD
		let assert be "entered password is not visible"
	}

	if :act == "enter password(password)" && :password != NOT_ENTERED_PASSWORD && :assert == "entered password is not visible" 
	    let [R2] be "entered password is not visible"

Run test generation and add the new method to the driver class to fix the compile errors.

##Step 13. Removing conditional rule

The requirement coverage property `[R2]` can be moved up to eliminate the need of the condition.

	"enter password(password)"{
		let password be VALID_PASSWORD
		let assert be "entered password is not visible"
	    let [R2] be :assert
	}

##Step 14. Adding verification for requirement R4

Pretty small changes in the strategy and in the driver are required.

The strategy:

	let pageAfterSubmit be :page{
		let assert#2 be "password field is empty"
		let assert#3 be "email field equals to (email)"
		let [R4] be "check email and password fields after failed log-in"
	}

The driver:

	public void passwordFieldIsEmpty() {}
	public void emailFieldEqualsTo(EMail notEnteredMail) {}

##Step 15. Eliminating duplication by defining default values

Defining default property values allows to avoid duplication and to focus on the relevant test aspects.
Here default values for properties `password` and `email` are set:

	let arrange#1 be "go to page(page)"
	let page be LOGIN_PAGE
	let default password be VALID_PASSWORD
	let default email be VALID_MAIL

	let act be 
	"submit(protocol)"{
		let arrange#2 be "enter mail address(email)"
		let arrange#3 be "enter password(password)"
		let assert be "checkPage(pageAfterSubmit)"

		let protocol be HTTP{
			let [R1] be "logging in with protocol HTTP not allowed"
		},
		HTTPS {
			let email be VALID_MAIL { let password be VALID_PASSWORD, INVALID_PASSWORD, NOT_ENTERED_PASSWORD},
			INVALID_MAIL { let password be VALID_PASSWORD},
			NOT_ENTERED_MAIL { let password be VALID_PASSWORD, NOT_ENTERED_PASSWORD}
		}

		let pageAfterSubmit be :page
		if :protocol == HTTPS && :email == VALID_MAIL && :password == VALID_PASSWORD
			let pageAfterSubmit be LOGIN_SUCCESS_PAGE {
				let [R3] be "log in successful"
			}
	},
	"enter password(password)"{
		let assert be "entered password is not visible"
		let [R2] be :assert
	}

##Step 16. Creating multiple scripts for different parts of the test suite

If property script has different values for different test cases they are output to different files. Remove line

	let script be "login/LoginTest"

and add set individual values for different actions:

	let act be 
	"submit(protocol)"{
		let script be "login/LoginSubmit"
		let arrange#2 be "enter mail address(email)"

and

	"enter password(password)"{
		let script be "login/LoginFormat"
		let assert be "entered password is not visible"

##Step 17. Splitting test strategy in smaller strategies

Strategies can include rules defined in other strategies defined in the same file, other files or libraries.

	strategy loginTests
		let script.driver be "login/LoginTestDriver"
		let script.imports be "import static login.LoginTestDriver.Page.*;
							   import static login.LoginTestDriver.EMail.*;
							   import static login.LoginTestDriver.Password.*;
							   import static login.LoginTestDriver.Protocol.*;"

		let arrange#1 be "go to page(page)"
		let page be LOGIN_PAGE
		let default password be VALID_PASSWORD
		let default email be VALID_MAIL

		let act be "submit(protocol)"{
			apply submitTest
		},
		"enter password(password)"{
			apply formatTest
		}

	strategy submitTest
		let script be "login/LoginSubmit"
		let arrange#2 be "enter mail address(email)"
		let arrange#3 be "enter password(password)"
		let assert be "checkPage(pageAfterSubmit)"

		let protocol be HTTP{
			let [R1] be "logging in with protocol HTTP not allowed"
		},
		HTTPS {
			let email be VALID_MAIL { let password be VALID_PASSWORD, INVALID_PASSWORD, NOT_ENTERED_PASSWORD},
			INVALID_MAIL { let password be VALID_PASSWORD},
			NOT_ENTERED_MAIL { let password be VALID_PASSWORD, NOT_ENTERED_PASSWORD}
		}

		let pageAfterSubmit be :page{
			let assert#2 be "password field is empty"
			let assert#3 be "email field equals to (email)"
			let [R4] be "check email and password fields after failed log-in"
		}

		if traced loginOracle.isLoginSuccessful(:protocol as Protocol, :email as EMail, :password as Password)
			let pageAfterSubmit be LOGIN_SUCCESS_PAGE

	strategy formatTest
		let script be "login/LoginFormat"
		let assert be "entered password is not visible"
		let [R2] be :assert


##Step 18. Use of oracle for calculating predictions

Better separation of concerns and reuse can be achieved if calculation of expected results is implemented separately from strategy definition. For this purpose test oracle components can be defined.

After adding oracle definition like

	oracle loginOracle
		def isLoginSuccessful(){
			return :protocol == HTTPS && :email == VALID_MAIL && :password == VALID_PASSWORD;
		}

and registering the oracle in the run section

	run strategy goal loginTests with oracle loginOracle
		apply "/java.xslt" output "generated-tests/java"
		report  "report/testcoverage.xml"

the login successful condition in the strategy can be expressed as

	if loginOracle.isLoginSuccessful()
		let pageAfterSubmit be LOGIN_SUCCESS_PAGE {
			let [R3] be "log in successful"
		}

##Step 19. Use of oracle for test coverage evaluation

Requirements can be tracked in oracle implementation like

	oracle loginOracle
		def isLoginSuccessful(){
			if(:protocol == HTTPS && :email == VALID_MAIL && :password == VALID_PASSWORD)
				["R3" "log in successful"] return true
			else
				return false
		}

or in a short form

	oracle loginOracle
		def isLoginSuccessful(){
			return :protocol == HTTPS && :email == VALID_MAIL
				&& :password == VALID_PASSWORD && ["R3" "log in successful"] true;
		}

The strategy changes to

	if traced loginOracle.isLoginSuccessful() let pageAfterSubmit be LOGIN_SUCCESS_PAGE

and the run configuration changes to

	run strategy goal loginTests with oracle goal loginOracle
		apply "/java.xslt" output "generated-tests/java"
		report  "report/testcoverage.xml"

Keyword `traced` is needed to mark the oracle calls relevant for the requirement coverage.

##Step 20. Breaking dependency of the oracle from the strategy

Current oracle implementation refers to the property values defined in the strategy. Its interface can be changed so that the oracle which is converted to a java class can be used without the strategy context.

Imports:

	import login.LoginTestDriver.EMail;
	import login.LoginTestDriver.Password;
	import login.LoginTestDriver.Protocol;

Oracle implementation:

	oracle loginOracle
		def isLoginSuccessful(Protocol protocol, EMail email, Password password){
			return :protocol == HTTPS && :email == VALID_MAIL
				&& :password == VALID_PASSWORD && ["R3" "log in successful"] true;
		}

Strategy:

	if traced loginOracle.isLoginSuccessful(:protocol as Protocol, :email as EMail, :password as Password)
		let pageAfterSubmit be LOGIN_SUCCESS_PAGE

##Step 21. Moving strategy and oracle to separate files

Strategies, oracles and generation run sections can be put in separate files.
All file names should start with capital letters

file LoginTestSuite.tsgen:

	package login.testgeneration

	import org.dpolivaev.tsgen.strategies.StrategyHelper;

	import static login.testgeneration.LoginOracles.*;
	import static login.testgeneration.LoginStrategies.*;

	run strategy goal loginTests with oracle goal loginOracle
		apply "/java.xslt" output "generated-tests/java"
		report  "report/testcoverage.xml"
