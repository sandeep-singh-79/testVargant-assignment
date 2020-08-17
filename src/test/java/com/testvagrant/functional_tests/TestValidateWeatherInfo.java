package com.testvagrant.functional_tests;

import com.testvagrant.base.BaseTestNGTest;
import com.testvagrant.base.api.ApiBase;
import com.testvagrant.base.api.EndPoints;
import com.testvagrant.pages.NDTVWeatherPO;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static com.testvagrant.base.context.Context.DRIVER;
import static org.testng.Assert.assertTrue;

@Slf4j
public class TestValidateWeatherInfo extends BaseTestNGTest {

    private NDTVWeatherPO weatherPO;
    private ApiBase apiBase;
    private Response response;

    @BeforeMethod
    public void setupTest(ITestContext testContext) throws InterruptedException {
        driver = (WebDriver) testContext.getAttribute(DRIVER.toString());
        String cityName = testData.getProperty("cityName");
        initAPIBase(testContext, cityName);

        //captureWeatherInfoFromWeb(cityName);
        captureWeatherInfoFromApi();
    }

    private void captureWeatherInfoFromApi() {
        response = apiBase.get_response(Method.GET, EndPoints.WEATHER.toString());

        response
                .then()
                .statusCode(200);
    }

    private void captureWeatherInfoFromWeb(String cityName) throws InterruptedException {
        weatherPO = homePO.navigateToWeatherPage();
        weatherPO
                .searchForCity(testData.getProperty("searchTerm"), cityName)
                .displayCityWeatherInfo(cityName)
                .storeWeatherInfo(cityName);
    }

    public void initAPIBase(ITestContext testContext, String cityName) {
        apiBase = new ApiBase(config.getProperty("baseUrl"),
                Integer.parseInt(config.getProperty("basePort")),
                config.getProperty("basePath"));
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("appid", config.getProperty("APIKey"));
        queryParams.put("q", cityName);
        queryParams.put("units", "metric");
        apiBase.set_query_params(queryParams);
    }

    @AfterMethod(alwaysRun = true)
    public void deleteAllCookies() {
        driver.manage().deleteAllCookies();
        RestAssured.reset();
    }

    @Test
    public void validateCityWeatherInfoIsDisplayed() {
        assertTrue(weatherPO.isWeatherInfoDisplayed());
        response.then().body("weather.main", Matchers.instanceOf(String.class));
    }
}
