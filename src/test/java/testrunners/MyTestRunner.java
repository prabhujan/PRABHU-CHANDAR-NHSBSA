package testrunners;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		 features = {"src/test/resources/AppFeatures"},
		    glue = {"stepdefinitions", "AppHooks"},
		    tags = "@regression",
		    plugin = {
		        "pretty",
		        "html:target/cucumber-reports/cucumber-html-report.html",
		        "json:target/cucumber-reports/cucumber.json",
		        },
		publish = true
		)
public class MyTestRunner {
	

	}