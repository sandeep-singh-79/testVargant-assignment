package com.testvagrant.pages;

import com.testvagrant.base.BasePageObject;
import com.testvagrant.config.FrameworkConfig;
import com.testvagrant.model.WeatherInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.*;

import static java.lang.String.format;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElements;

public class NDTVWeatherPO extends BasePageObject {
    @FindBy(id = "searchBox")
    private WebElement citySearchtxtBox;

    private WebElement cityWeatherInfo, city, cityContainer;
    private List<WebElement> citySuggestions;
    private final List<WebElement> citySuggestionCheckboxes;
    private List<WebElement> weatherConditions;


    public NDTVWeatherPO searchForCity(final String searchValue, final String cityName) throws InterruptedException {
        // wait for the page scripts to finish loading. Temporarily using thread.sleep till we figure out how to
        // wait for the script on page load complete before we continue forward
        Thread.sleep(5000);
        citySearchtxtBox.sendKeys(searchValue);
        wait.until(visibilityOfAllElements(citySuggestions = driver.findElements(By.xpath("//*[@id='messages']//div[not(contains(@style, 'none'))]"))));
        if (isCitySelectable(cityName)) city.click();
        return this;
    }

    public NDTVWeatherPO displayCityWeatherInfo(final String cityName) {
        String cityTempContainerLocator = format(".outerContainer[title='%s']", cityName);
        cityContainer = wait.until(visibilityOf(cityWeatherInfo = driver.findElement(By.cssSelector(cityTempContainerLocator))));
        cityContainer.click();
        return this;
    }

    public WeatherInfo storeWeatherInfo(final String cityName) {
        WebElement weatherInfoContainer = wait.until(visibilityOf(driver.findElement(By.className("leaflet-popup-content"))));
        weatherConditions = weatherInfoContainer.findElements(By.cssSelector(".leaflet-popup-content spa.heading"));
        Map<String, String> weatherConditionsMap = populateWeatherConditions(weatherConditions, cityName);

        WeatherInfo weatherInfo = new WeatherInfo();

        return weatherInfo;
    }

    private Map<String, String> populateWeatherConditions(List<WebElement> weatherConditions, String cityName) {
        Map<String, String> weatherConditionsMap = new HashMap<>();
        weatherConditionsMap.put("City", cityName);
        for (WebElement weatherState : weatherConditions) {
            String[] splits = weatherState.getText().split(":");
            weatherConditionsMap.put(splits[0].trim(), splits[1]);
        }
        return weatherConditionsMap;
    }

    private boolean isCitySelectable(final String cityName) {
        citySuggestions.forEach(suggestion -> citySuggestionCheckboxes.add(suggestion.findElement(By.xpath(".//input"))));
        for (WebElement element : citySuggestionCheckboxes)
            if (cityName.equalsIgnoreCase(element.getAttribute("id"))) {
                city = element;
                break;
            }

        return city != null;
    }

    public NDTVWeatherPO(WebDriver driver) {
        this(driver, FrameworkConfig.getInstance().getConfigProperties());
    }

    public NDTVWeatherPO(WebDriver driver, Properties config) {
        super(driver, config);
        PageFactory.initElements(ajaxElementLocatorFactory, this);
        citySuggestionCheckboxes = new ArrayList<>();
    }

    @Override
    protected By getUniqueElement() {
        return By.xpath("//div[@class='comment_cont']");
    }

    public boolean isWeatherInfoDisplayed() {
        return cityWeatherInfo.isDisplayed();
    }
}
