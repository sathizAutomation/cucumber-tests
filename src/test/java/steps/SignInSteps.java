package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pages.Pages;
import support.Configurations;

public class SignInSteps extends Pages {
    StepsSupporter supporter;
    public WebDriver driver;
    Configurations configurations = Configurations.getInstance();

    public SignInSteps(StepsSupporter supporter){
        super(supporter.driver);
        this.supporter = supporter;
        driver = supporter.driver;
    }

    @Given("The user navigates to authentication page")
    public void the_user_navigates_to_authentication_page() {
        signInPage.navigateToAuthScreen();
    }

    @When("he enters {string} and {string} for the application")
    public void he_enters_and_for_the_application(String emailId, String password) {
        signInPage.enterEmailIdAndPassword(emailId,password);
    }

    @When("logins to the application by pressing the login button")
    public void logins_to_the_application_by_pressing_the_login_button() {
        signInPage.pressLogin();
    }

    @Then("verifies that he is in the home page")
    public void verifies_that_he_is_in_the_home_page() {
        signInPage.assertHomePage();
    }

}
