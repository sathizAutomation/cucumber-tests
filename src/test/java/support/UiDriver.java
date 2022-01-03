package support;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class UiDriver {
	Configurations configurations = Configurations.getInstance();
	WebDriver wDriver;
	EventFiringWebDriver eventDriver;
	public DesiredCapabilities capability;

	public WebDriver getDriver(String driver) {

		if (configurations.getProperty("RunMode").equalsIgnoreCase("local")) {
			startBrowserForLocal(driver);
		} else if (configurations.getProperty("RunMode").equalsIgnoreCase("Remote")) {
			if (configurations.getProperty("RemoteMachine").equalsIgnoreCase("BrowserStack")) {
				startBrowserStack(driver);
			} else if (configurations.getProperty("RemoteMachine").equalsIgnoreCase("Docker")) {
				startBrowserDocker(driver);
			}
		} else {
			try {
				throw new Exception("Please set up the run mode properly in Configurations.properties");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return wDriver;
	}

	public WebDriver startBrowserForLocal(String driver){

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

	public void startBrowserDocker(String browser) {
		DesiredCapabilities capability;

		try {
			switch (browser) {
				case "Google Chrome":
					capability = DesiredCapabilities.chrome();
					capability.setCapability("browser", "Chrome");
					capability.setCapability("os", "Windows");
					wDriver = new RemoteWebDriver(new URL(configurations.getProperty("RemoteURL")), capability);
					wDriver.manage().window().maximize();

					break;
				case "firefox":
					capability = DesiredCapabilities.firefox();
					capability.setCapability("browser", "firefox");
					capability.setCapability("os", "Windows");
					wDriver = new RemoteWebDriver(new URL(configurations.getProperty("RemoteURL")), capability);
					wDriver.manage().window().maximize();
					break;
				}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void startBrowserStack(String driver) {
		final String AUTOMATE_USERNAME = configurations.getProperty("BrowserStack_UserName");
		final String AUTOMATE_ACCESS_KEY = configurations.getProperty("BrowserStack_AccessKey");
		final String BrowserstackURL = "https://" + AUTOMATE_USERNAME + ":" + AUTOMATE_ACCESS_KEY + "@hub-cloud.browserstack.com/wd/hub";
		try {
			DesiredCapabilities caps = new DesiredCapabilities();
			caps.setCapability("os_version", "10");
			caps.setCapability("resolution", "1920x1080");
			caps.setCapability("browser", "Chrome");
			caps.setCapability("browser_version", "latest");
			caps.setCapability("os", "Windows");
			caps.setCapability("name", "BStack-[Java] Sample Test"); // test name
			caps.setCapability("build", "BStack Build Number 1"); // CI/CD job or build name
			wDriver = new RemoteWebDriver(new URL(BrowserstackURL), caps);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
