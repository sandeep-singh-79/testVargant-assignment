package com.testvagrant.pages;

import com.testvagrant.base.BasePageObject;
import com.testvagrant.config.FrameworkConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static java.lang.String.format;

public class NDTVWeatherPO extends BasePageObject {
    @FindBy(id = "searchBox")
    private WebElement citySearchtxtBox;

    private WebElement cityWeatherInfo, city, cityContainer;
    private List<WebElement> citySuggestions;
    private final List<WebElement> citySuggestionCheckboxes;


    public NDTVWeatherPO searchForCity(final String searchValue, final String cityName) throws InterruptedException {
        // wait for the page scripts to finish loading. Temporarily using thread.sleep till we figure out how to
        // wait for the script on page load complete before we continue forward
        Thread.sleep(5000);
        citySearchtxtBox.sendKeys(searchValue);
        wait.until(ExpectedConditions
                           .visibilityOfAllElements(citySuggestions = driver.findElements(By.xpath("//*[@id='messages']//div[not(contains(@style, 'none'))]"))));
        if (isCitySelectable(cityName)) city.click();
        return this;
    }

    public NDTVWeatherPO displayCityWeatherInfo(final String cityName) {
        String cityTempContainerLocator = format(".outerContainer[title='%s']", cityName);
        cityContainer = wait.until(ExpectedConditions
                                           .visibilityOf(cityWeatherInfo = driver.findElement(By.cssSelector(cityTempContainerLocator))));
        cityContainer.click();
        return this;
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
