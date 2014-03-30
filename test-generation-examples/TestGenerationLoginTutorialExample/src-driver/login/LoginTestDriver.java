package login;

public class LoginTestDriver {
	public enum Page{LOGIN_PAGE, LOGIN_SUCCESS_PAGE}
	public enum EMail{VALID_MAIL, INVALID_MAIL, NOT_ENTERED_MAIL}
	public enum Password{VALID_PASSWORD, INVALID_PASSWORD, NOT_ENTERED_PASSWORD}
	public enum Protocol{HTTP, HTTPS}

	public void submit(Protocol protocol){}
	public void checkPage(Page loginSuccessPage) {}
	public void enterMailAddress(EMail validMail) {}
	public void enterPassword(Password validPassword) {}
	public void goToPage(Page loginPage) {}
	public void enteredPasswordIsNotVisible() {}
	public void passwordFieldIsEmpty() {}
	public void emailFieldEqualsTo(EMail notEnteredMail) {}
}
