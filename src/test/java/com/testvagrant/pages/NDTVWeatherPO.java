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

public class NDTVWeatherPO extends BasePageObject {
    @FindBy(id = "searchBox")
    private WebElement searchWeatherForCity;

    private List<WebElement> citySuggestions;
    private final List<WebElement> citySuggestionCheckboxes;
    private WebElement city;


    public void searchForCity(String searchValue, String cityName) {
        searchWeatherForCity.sendKeys(searchValue);
        wait.until(ExpectedConditions
                           .visibilityOfAllElements(citySuggestions = driver.findElements(By.xpath("//*[@id='messages']//div[not(contains(@style, 'none'))]"))));
        if (isCitySelectable(cityName)) {
            city.click();
        }
    }

    private boolean isCitySelectable(String cityName) {
        citySuggestions.forEach(suggestion -> citySuggestionCheckboxes.add(suggestion.findElement(By.xpath("//input"))));
        city = citySuggestionCheckboxes
                       .stream()
                       .filter(item -> item.getAttribute("id").equalsIgnoreCase(cityName))
                       .findFirst()
                       .get();

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
}
