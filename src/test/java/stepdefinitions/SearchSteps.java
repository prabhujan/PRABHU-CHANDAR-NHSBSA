package stepdefinitions;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import com.pages.SearchPage;
import com.qa.factory.DriverFactory;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SearchSteps {

	private SearchPage searchPage = new SearchPage(DriverFactory.getDriver());
	WebDriver driver;

	@Given("I am on the NHS Jobs search page")
	public void openSearchPage() {

		DriverFactory.getDriver().get("https://www.jobs.nhs.uk/candidate/search");

	}

	@When("I enter my preferences like {string} in keyword and {string} in location")
	public void enterPreferences(String keyword, String location) {
		searchPage.enterSearchCriteria(keyword, location);
	}

	@And("I click on the search button")
	public void clickSearchButton() {
		searchPage.clickSearch();
	}

	@Then("I should see a list of jobs that match my preferences")
	public void verifyJobResults() {
	    Assert.assertTrue("No job results found", searchPage.hasJobResults());
	}

	@And("the results should be sorted by the newest Date Posted")
	public void verifySortedByNewestDate() {
	    searchPage.selectSortByNewest();
	    Assert.assertTrue("Results are not sorted by newest date", searchPage.isSortedByNewest());
	}
	
	@Then("I should see a message or no results indicating invalid search")
	public void verifyNoResultsOrErrorShown() {
	    Assert.assertTrue(
	        "Expected 'no results' message, but it was not displayed.",
	        searchPage.noResultsOrInvalidMessageShown()
	    );
	}

}
