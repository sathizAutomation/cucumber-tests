package steps;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import io.cucumber.core.api.Scenario;
import pages.Pages;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import support.Configurations;
import support.Settings;
import support.UiDriver;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StepsSupporter {
    static Logger log = Logger.getLogger(StepsSupporter.class.getName());
    public Settings settings = Settings.getInstance();
    Configurations configurations = Configurations.getInstance();
    public UiDriver uidriver = new UiDriver();
    String screenshotsPath = settings.getScreenshotsDir();
    public WebDriver driver;

    @Before
    public WebDriver initializeBrowser() {
        String browserName = configurations.getProperty("BROWSER");
        String appURL = configurations.getProperty("APP_URL");
        //take a copy of report for previous execution
        //createHistoricalReport();
        //instantiate the driver instance
        driver = uidriver.getDriver(browserName);
        //browser initial set ups
        log.log(Level.INFO, "The browser is launched for the execution!");
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(600, TimeUnit.SECONDS);
        driver.get(appURL);
        return driver;
    }

    @AfterStep
    public void takeScreenshot(Scenario scenario) {
        String randomNo = Pages.randomNumber(10);
        String screenDir = settings.getResourcesDir() + "screenshots" + System.getProperty("file.separator");
        File screenCap = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String screenshotName = screenDir + "screenshot_" + randomNo + ".png";
        try {
            FileUtils.copyFile(screenCap, new File(screenshotName));
            ExtentCucumberAdapter.addTestStepScreenCaptureFromPath(screenshotName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void cleanUp() {
        driver.quit();
        log.log(Level.INFO, "The browser is closed after the execution!");
    }

    /*Method to take backup of previous report*/
    public void createBackupReportDirectory() {
        String reportPath = settings.getReportsDir();
        String history = settings.getReportsDir() + "Historical Reports";
        new File("history").mkdirs();
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddhhmmss");
        String datetime = ft.format(dNow);
        String executionDir = history + System.getProperty("file.separator") + "Execution_" + datetime;
        String TestSummaryReport = history + System.getProperty("file.separator") + "Execution_" + datetime + System.getProperty("file.separator") + "TestSummaryReport.html";
        new File(executionDir).mkdirs();
        try {
            FileUtils.copyFileToDirectory(new File(reportPath + "TestSummaryReport.html"), new File(executionDir), false);
            FileUtils.copyDirectory(new File(reportPath + "cucumber-html-reports"), new File(executionDir), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}