package com.testvagrant.functional_tests;

import com.google.gson.Gson;
import com.testvagrant.base.BaseTestNGTest;
import com.testvagrant.base.api.ApiBase;
import com.testvagrant.base.api.EndPoints;
import com.testvagrant.model.WeatherInfo;
import com.testvagrant.pages.NDTVWeatherPO;
import groovy.json.JsonOutput;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
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
    private WeatherInfo weatherInfoWeb, weatherInfoApi;
    private final Gson gson = new Gson();

    @BeforeMethod
    public void setupTest(ITestContext testContext) throws InterruptedException {
        driver = (WebDriver) testContext.getAttribute(DRIVER.toString());
        String cityName = testData.getProperty("cityName");
        initAPIBase(testContext, cityName);

        captureWeatherInfoFromWeb(cityName);
        captureWeatherInfoFromApi(cityName);
    }

    private void captureWeatherInfoFromApi(String cityName) {
        response = apiBase.get_response(Method.GET, EndPoints.WEATHER.toString());

        response
                .then()
                .statusCode(200);

        // need to do this as an int is being returned and autoboxing or casting is not working
        float tempDegrees = Float.parseFloat(response.jsonPath().get("main.temp") + "");
        Map<String, Object> weatherResponseMap = new HashMap<>();
        weatherResponseMap.put("condition", response.jsonPath().getList("weather.main"));
        weatherResponseMap.put("cityName", cityName);
        weatherResponseMap.put("windSpeed", response.jsonPath().get("wind.speed"));
        weatherResponseMap.put("windGust", response.jsonPath().get("wind.gust"));
        weatherResponseMap.put("tempDegrees", tempDegrees);
        weatherResponseMap.put("tempFahrenheit", tempDegrees * 1.8 + 32);
        weatherResponseMap.put("humidity", response.jsonPath().get("main.humidity"));

        weatherInfoApi = gson.fromJson(JsonOutput.toJson(weatherResponseMap), WeatherInfo.class);
    }

    private void captureWeatherInfoFromWeb(String cityName) throws InterruptedException {
        weatherPO = homePO.navigateToWeatherPage();
        Map<String, Object> weatherWeb = new HashMap<>();
        weatherWeb = weatherPO
                             .searchForCity(testData.getProperty("searchTerm"), cityName)
                             .displayCityWeatherInfo(cityName)
                             .storeWeatherInfo(cityName);
        weatherInfoWeb = gson.fromJson(JsonOutput.toJson(weatherWeb), WeatherInfo.class);
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
        log.info("Weather data from API: {}", weatherInfoApi.toString());
        log.info("Weather data from Web: {}", weatherInfoWeb.toString());
        assertTrue(weatherInfoApi.equals(weatherInfoWeb));
    }
}
