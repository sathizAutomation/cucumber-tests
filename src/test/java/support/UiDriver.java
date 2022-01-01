package support;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.util.HashMap;

public class UiDriver {
	Configurations configurations = Configurations.getInstance();
	WebDriver wDriver;
	EventFiringWebDriver eventDriver;

	public WebDriver getDriver(String driver){

		try{

			if(driver.equalsIgnoreCase("Chrome")) {
				 HashMap<String, Object> chromePrefs = new HashMap<String, Object>();  
				 chromePrefs.put("profile.default_content_settings.popups", 0);  
				 //chromePrefs.put("download.default_directory", TestSupporter.FILE_DOWNLOAD_PATH);
				 ChromeOptions options = new ChromeOptions();  
				 options.setExperimentalOption("prefs", chromePrefs);  
				 options.addArguments("--disable-notifications");  
				 DesiredCapabilities cap = DesiredCapabilities.chrome();  
				 cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);  
				 cap.setCapability(ChromeOptions.CAPABILITY, options);
				 WebDriverManager.chromedriver().setup();
				 wDriver = new ChromeDriver(options);
			}

			else if(driver.equalsIgnoreCase("IE")) {
				DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
				capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				//capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
				capabilities.setCapability("ignoreZoomSetting", true);
				capabilities.setCapability("ignoreProtectedModeSettings", true);
				capabilities.setCapability("requireWindowFocus", true);
				String ieDriver=Settings.getInstance().getDriverEXEDir()+"IEDriverServer.exe";
				System.setProperty("webdriver.ie.driver", ieDriver);
				wDriver = new InternetExplorerDriver(capabilities);	
				wDriver.manage().window().maximize();
				return wDriver;
			}

			else {
				WebDriverManager.firefoxdriver().setup();
				wDriver = new FirefoxDriver();
				return wDriver;
			}

		}catch(Exception e){
			e.printStackTrace();
			wDriver = new FirefoxDriver();
			return wDriver;
		}
		return wDriver;
	}
	/**
	 *
	 * Method name  : quitDriver
	 * Return types : boolean
	 * Description  : This method is to quit the driver instance after completing a test
	 */

	public boolean quitDriver(WebDriver wDriver){
		try{
			wDriver.quit();
			return true;
		}catch(Exception e){
			return false;
		}
	}

}
