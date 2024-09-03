package com.pageobjects;

import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By startLocationField = By.xpath("//input[@placeholder='From?']");
    private By endLocationField = By.xpath("//input[@placeholder='To?']");
    private By searchButton = By.xpath("//button[@title='Search']");
    private By removeDefaultLocationButton = By.xpath("//body/div[@id='root']/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/*[1]");
    private By selectAirport = By.xpath("//input[@id='0']");
    private By selectDestinationAirport = By.xpath("//input[@id='0']");
    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver,Duration.ofSeconds(80));
        PageFactory.initElements(driver, this);
    }

    public void removeDefaultLocation() {
        WebElement button = wait.until(ExpectedConditions.presenceOfElementLocated(removeDefaultLocationButton));
        button.click();
    }

    public void enterStartLocation(String location) {
        WebElement field = wait.until(ExpectedConditions.presenceOfElementLocated(startLocationField));
        field.sendKeys(location);
    }
    public void selectStartAirport() {
        WebElement airport = wait.until(ExpectedConditions.presenceOfElementLocated(selectAirport));
        airport.click();
    }

    public void enterEndLocation(String location) {
        WebElement field = wait.until(ExpectedConditions.presenceOfElementLocated(endLocationField));
        field.sendKeys(location);
    }

    public void selectEndAirport() {
        WebElement airport = wait.until(ExpectedConditions.presenceOfElementLocated(selectDestinationAirport));
        airport.click();
    }

    
    public void clickSearch() {
        WebElement button = wait.until(ExpectedConditions.presenceOfElementLocated(searchButton));
        button.click();
    }
    public void switchToNewTab() {
        String originalWindow = driver.getWindowHandle();
        Set<String> allWindows = driver.getWindowHandles();

        // Wait until there is either a new window or confirm that no new window will be opened
        wait.until(ExpectedConditions.numberOfWindowsToBe(allWindows.size() == 1 ? 1 : 2));

        // Re-fetch the set of window handles after the wait
        allWindows = driver.getWindowHandles();

        // If a new window is opened, switch to it
        if (allWindows.size() > 1) {
            for (String windowHandle : allWindows) {
                if (!windowHandle.equals(originalWindow)) {
                    driver.switchTo().window(windowHandle);
                    break;
                }
            }
        } else {
            // If no new window is opened, remain on the current window
            driver.switchTo().window(originalWindow);
        }
    }
//    public void switchToNewTab() {
//        String originalWindow = driver.getWindowHandle();
//        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
//        Set<String> allWindows = driver.getWindowHandles();
//        for (String windowHandle : allWindows) {
//            if (!windowHandle.equals(originalWindow)) {
//                driver.switchTo().window(windowHandle);
//                break;
//            }
//        }
//    }
}