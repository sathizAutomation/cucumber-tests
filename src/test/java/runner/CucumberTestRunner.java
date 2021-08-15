package runner;
//html:report/TestSummaryReport.html
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
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

}
