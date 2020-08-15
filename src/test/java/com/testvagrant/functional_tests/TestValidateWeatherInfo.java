package com.testvagrant.functional_tests;

import com.testvagrant.base.BaseTestNGTest;
import com.testvagrant.pages.NDTVWeatherPO;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.testvagrant.base.context.Context.DRIVER;
import static org.testng.Assert.assertTrue;

@Slf4j
public class TestValidateWeatherInfo extends BaseTestNGTest {

    private NDTVWeatherPO weatherPO;

    @BeforeMethod
    public void setupTest(ITestContext testContext) {
        driver = (WebDriver) testContext.getAttribute(DRIVER.toString());
        weatherPO = homePO.navigateToWeatherPage();
        weatherPO.searchForCity("ja", "Jabalpur");
    }

    @AfterMethod(alwaysRun = true)
    public void deleteAllCookies() {
        driver.manage().deleteAllCookies();
    }

    @Test
    public void isSiteTitleCorrect() {
        assertTrue(driver.getTitle().contains("Weather"));
    }
}
