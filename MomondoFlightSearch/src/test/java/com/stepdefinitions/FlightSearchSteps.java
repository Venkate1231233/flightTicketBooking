package com.stepdefinitions;

import com.pageobjects.HomePage;
import com.pageobjects.SearchResultsPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.DataProvider;

import static org.junit.Assert.assertTrue;

public class FlightSearchSteps {
	private WebDriver driver;
	private HomePage homePage;
	private SearchResultsPage resultsPage;
	 @DataProvider(name = "locationData")
	    public Object[][] locationData() {
	        return new Object[][] {
	            {"Tampa", "Detroit"},
	            {"Chicago", "Detroit"},
	            {"Los Angeles", "Dallas"}
	        };
	    }

	@Given("I am on the Momondo homepage")
	public void iAmOnTheMomondoHomepage() {
		driver = new ChromeDriver();
		driver.get("https://www.momondo.com/");
		homePage = new HomePage(driver);
		resultsPage = new SearchResultsPage(driver);
	}

	@When("I search for flights from {string} to {string}")
	public void iSearchForFlightsFromTo(String startLocation, String endLocation) {
		homePage.removeDefaultLocation();
		homePage.enterStartLocation(startLocation);
		homePage.selectStartAirport();
		homePage.enterEndLocation(endLocation);
		homePage.selectEndAirport();
		homePage.clickSearch();
		homePage.switchToNewTab();
	}

	@Then("I should see the cheapest flight price")
	public void iShouldSeeTheCheapestFlightPrice() {
		int cheapestPrice = resultsPage.findCheapestPrice();
		assertTrue("Cheapest price should be found", cheapestPrice != -1);
		System.out.println("The cheapest price is: $" + cheapestPrice);
	}

	@Then("I should see the longest roundtrip duration with outbound and inbound details for {string} to {string}")
	public void iShouldSeeTheLongestRoundtripDuration(String startLocation, String endLocation) {
		int longestRoundtripDuration = resultsPage.findLongestRoundtripDuration();

		// Assume resultsPage provides methods to get outbound and inbound durations
		int outboundDuration = resultsPage.getOutboundDuration(); // Fetch outbound duration
		int inboundDuration = resultsPage.getInboundDuration(); // Fetch inbound duration

		assertTrue("Longest roundtrip duration should be found", longestRoundtripDuration != 0);

		String outboundDurationStr = convertToHoursAndMinutes(outboundDuration);
		String inboundDurationStr = convertToHoursAndMinutes(inboundDuration);

		
		    System.out.println("Outbound duration from " + startLocation + " to " + endLocation + ": " + outboundDurationStr);
		    System.out.println("Inbound duration from " + endLocation + " to " + startLocation + ": " + inboundDurationStr);

		System.out.println("The longest roundtrip duration is: " + convertToHoursAndMinutes(longestRoundtripDuration));
	}

	private String convertToHoursAndMinutes(int totalMinutes) {
		int hours = totalMinutes / 60;
		int minutes = totalMinutes % 60;
		return hours + "h " + minutes + "m";
	}
}