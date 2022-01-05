package runner;
//html:report/TestSummaryReport.html
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import steps.StepsSupporter;
import support.Configurations;

@RunWith(Cucumber.class)
@CucumberOptions(features = { "src/test/resources/features" },
        glue = { "steps" },
        plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "json:target/cucumber-report.json",
                "pretty",
                "summary"},
        tags = "@test",
        dryRun = false,
        monochrome = true)

public class CucumberTestRunner {
    @BeforeClass
    public static void executeBeforeStart(){
        if(Configurations.getInstance().getProperty("BackupReport").equalsIgnoreCase("Yes")) {
            StepsSupporter stepsSupporter = new StepsSupporter();
            stepsSupporter.createBackupReportDirectory();
        }
    }

    @AfterClass
    public static void executeAfterTest(){

    }

}
