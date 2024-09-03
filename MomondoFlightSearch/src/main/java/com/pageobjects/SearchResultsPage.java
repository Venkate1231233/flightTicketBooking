package com.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class SearchResultsPage {
    private WebDriver driver;
    private WebDriverWait wait;
    // Variables to store outbound and inbound durations
    private int outboundDuration = 0;
    private int inboundDuration = 0;
    // Locators
    private By priceElements = By.xpath("//div[@class='f8F1-price-text']");
    private By durationElements = By.xpath("//div[@class='xdW8']");

    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver,Duration.ofSeconds(80));
        PageFactory.initElements(driver, this);
    }

    public List<Integer> getPrices() {
        List<WebElement> priceElementsList = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(priceElements));
        List<Integer> prices = new ArrayList<>();
        for (WebElement priceElement : priceElementsList) {
            String priceText = priceElement.getText().replaceAll("[^\\d]", "");
            if (!priceText.isEmpty()) {
                try {
                    prices.add(Integer.parseInt(priceText));
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing price: " + priceText);
                }
            }
        }
        return prices;
    }

    public int findCheapestPrice() {
        List<Integer> prices = getPrices();
        return !prices.isEmpty() ? Collections.min(prices) : -1;
    }

 // Method to find the longest roundtrip duration
    public int findLongestRoundtripDuration() {
        List<WebElement> durationElements = wait.until(
            ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='xdW8']"))
        );
        List<Integer> outboundDurations = new ArrayList<>();
        List<Integer> inboundDurations = new ArrayList<>();

        // Separate outbound and inbound durations
        for (int i = 0; i < durationElements.size(); i++) {
            String durationText = durationElements.get(i).getText();
            if (durationText.contains("h") || durationText.contains("m")) {
                int totalMinutes = convertToMinutes(durationText);
                if (i % 2 == 0) {
                    outboundDurations.add(totalMinutes);
                } else {
                    inboundDurations.add(totalMinutes);
                }
            }
        }

        // Find the longest roundtrip duration and store the relevant durations
        int longestRoundtripDuration = 0;
        for (int i = 0; i < outboundDurations.size(); i++) {
            int roundtripDuration = outboundDurations.get(i) + inboundDurations.get(i);
            if (roundtripDuration > longestRoundtripDuration) {
                longestRoundtripDuration = roundtripDuration;
                outboundDuration = outboundDurations.get(i);
                inboundDuration = inboundDurations.get(i);
            }
        }

        return longestRoundtripDuration;
    }

    // Method to get outbound duration
    public int getOutboundDuration() {
        return outboundDuration; // Return the stored outbound duration
    }

    // Method to get inbound duration
    public int getInboundDuration() {
        return inboundDuration; // Return the stored inbound duration
    }

    // Helper method to convert duration string (e.g., "2h 30m") into total minutes
    private int convertToMinutes(String durationText) {
        String[] parts = durationText.split(" ");
        int totalMinutes = 0;
        for (String part : parts) {
            if (part.endsWith("h")) {
                totalMinutes += Integer.parseInt(part.replace("h", "")) * 60;
            } else if (part.endsWith("m")) {
                totalMinutes += Integer.parseInt(part.replace("m", ""));
            }
        }
        return totalMinutes;
    }

    // Helper method to convert total minutes into a readable "Xh Ym" format
    public String convertToHoursAndMinutes(int totalMinutes) {
        int hours = totalMinutes / 60;
        int minutes = totalMinutes % 60;
        return hours + "h " + minutes + "m";
    }
}
