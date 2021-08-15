package pages;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.logging.Level;

public class SignInPage extends PageSupporter {
    WebDriver driver;

    public SignInPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @FindBy(how = How.ID, using = "email_create")
    private WebElement email;

    @FindBy(how = How.ID, using = "SubmitCreate")
    private WebElement createAnAccount;

    public String createNewAccount(String emailId) {
        String random = randomNumber(5);
        emailId = emailId + random;
        waitFor(email, 30);
        email.sendKeys(emailId);
        createAnAccount.click();
        return emailId;
    }

    @FindBy(how = How.CSS, using = "a[title*='Log in']")
    private WebElement logIn;

    public void navigateToAuthScreen() {
        try {
            waitFor(logIn, 30);
            logIn.click();
            log.log(Level.INFO, "Navigating to Authorization screen...");
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
        }
    }

    @FindBy(how = How.ID, using = "email")
    private WebElement loginEmail;

    @FindBy(how = How.XPATH, using = "//input[@id='passwd']")
    private WebElement loginPassword;

    @FindBy(how = How.ID, using = "SubmitLogin")
    private WebElement submitLogin;

    @FindBy(how = How.CSS, using = "[title*='Information']")
    private WebElement info;

    public void enterEmailIdAndPassword(String emailId, String password) {
        try {
            waitFor(loginEmail, 30);
            loginEmail.sendKeys(emailId);
            loginPassword.sendKeys(password);
            log.log(Level.INFO, "Entering data for email id and password...");
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
        }
    }

    public void pressLogin() {
        try {
            waitFor(submitLogin, 30);
            submitLogin.click();
            log.log(Level.INFO, "Logging into the application by clicking submit...");
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
        }
    }

    public void assertHomePage() {
        waitFor(info, 30);
        Assert.assertTrue(info.isDisplayed());
    }

    @FindBy(how = How.CSS, using = ".alert-danger li")
    private WebElement error;

    public void inValidSignIn(String emailId, String password) {
        waitFor(loginEmail, 30);
        loginEmail.sendKeys(emailId);
        loginPassword.sendKeys(password);
        submitLogin.click();
        waitFor(error, 30);
        Assert.assertTrue(error.isDisplayed());
    }

}