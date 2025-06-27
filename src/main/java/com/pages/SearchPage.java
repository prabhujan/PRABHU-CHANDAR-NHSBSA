package com.pages;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchPage {

	private WebDriver driver;

	private By keywordInput = By.id("keyword");
	private By locationInput = By.id("location");
	private By searchButton = By.id("search");
	private By jobCards = By.cssSelector(".search-results");
	private By sortByDateDropdown = By.id("sort");
//	private By jobCards = By.className("job-result");
	private By dateClass = By.className("date-posted");

	public SearchPage(WebDriver driver) {
		this.driver = driver;
	}

	public String getSearchPageTitle() {
		return driver.getTitle();

	}

	public void enterSearchCriteria(String keyword, String location) {
		System.out.println("Entering search keyword: " + keyword);
		driver.findElement(keywordInput).clear();
		driver.findElement(keywordInput).sendKeys(keyword);

		System.out.println("Entering location: " + location);
		driver.findElement(locationInput).clear();
		driver.findElement(locationInput).sendKeys(location);

	}

	public void clickSearch() {

//		searchButton.click();
		System.out.println("Clicking on the Search button");
		driver.findElement(searchButton).click();
	}

//	public boolean resultsMatchPreferences() {
//		List<WebElement> results = driver.findElements(jobResults);
//	    return !results.isEmpty();
//	}

	public boolean hasJobResults() {
		System.out.println("Waiting for job results to appear...");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		List<WebElement> results = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(jobCards));
		System.out.println("Job results found: " + results.size());
		return !results.isEmpty();
	}

	public void selectSortByNewest() {
		System.out.println("Selecting 'Date Posted (newest)' from sort dropdown");
		WebElement dropdown = driver.findElement(sortByDateDropdown);
		new Select(dropdown).selectByVisibleText("Date Posted (newest)");
		new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions
				.presenceOfAllElementsLocatedBy(By.cssSelector("li[data-test='search-result-publicationDate']")));
		 System.out.println("Sort option applied successfully");
	}

	public boolean isSortedByNewest() {
		List<WebElement> jobElements = driver
				.findElements(By.cssSelector("li[data-test='search-result-publicationDate']"));
		List<LocalDate> actualDates = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.ENGLISH);

		for (WebElement job : jobElements) {
			try {
				String dateText = job.getText().replace("Date posted:", "").trim();
				LocalDate parsedDate = LocalDate.parse(dateText, formatter);
				actualDates.add(parsedDate);
			} catch (Exception e) {
				System.out.println("Skipping invalid date: " + e.getMessage());
			}
		}

		System.out.println("Extracted dates:");
		for (LocalDate date : actualDates) {
			System.out.println(date);
		}

		if (actualDates.size() < 2)
			return false;

		List<LocalDate> sortedDates = new ArrayList<>(actualDates);
		sortedDates.sort(Comparator.reverseOrder());

		return actualDates.equals(sortedDates);
		
	}
	
	
	public boolean noResultsOrInvalidMessageShown() {
		System.out.println("Checking for 'No results' or invalid search message...");
	    By noResultsMessage = By.id("search-results-heading");
	    return isElementPresent(noResultsMessage);
	    
	}

	public boolean isElementPresent(By locator) {
	    try {
	        return driver.findElement(locator).isDisplayed();
	    } catch (NoSuchElementException e) {
	        return false;
	    }
	}
}
