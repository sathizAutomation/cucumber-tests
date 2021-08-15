package pages;

import com.github.javafaker.Faker;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import pages.NavigationMenu;
import pages.RegistrationPage;
import pages.SignInPage;
import support.DBConnection;
import support.Settings;

import java.util.Random;


public class Pages {
	public Settings settings = Settings.getInstance();
	public DBConnection dbConnection = DBConnection.getInstance();
	protected Faker random = new Faker();
	protected NavigationMenu navigationMenu;
	protected RegistrationPage registrationPage;
	protected SignInPage signInPage;
	
	public Pages(WebDriver driver) {
		//Initialize the pages
		signInPage = PageFactory.initElements(driver, SignInPage.class);
		navigationMenu = PageFactory.initElements(driver, NavigationMenu.class);
		registrationPage = PageFactory.initElements(driver, RegistrationPage.class);

	}

	public static String randomNumber(int length) {
		Random rand = new Random();
		String  randomNo="";
		for(int len = 1;len<=length;len++) {
			randomNo = randomNo + (rand.nextInt(9) + 1);
			if(randomNo.length()==length) {
				return randomNo;
			}
		}
		return "NOT_RANDOM";
	}
}
