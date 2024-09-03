package com.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
    features = "src/test/resources/features/flight_search.feature",
    glue = "com.stepdefinitions",
    tags = "@smoke or @regression", // Use tags to filter the execution of specific scenarios
    plugin = {"pretty", "html:target/cucumber-reports"}
    
)
public class TestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}