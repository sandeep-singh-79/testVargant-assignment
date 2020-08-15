package com.testvagrant.pages;

import com.testvagrant.base.BasePageObject;
import com.testvagrant.config.FrameworkConfig;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Properties;

@Slf4j
public class NDTVHomePO extends BasePageObject {

    @FindBy(how = How.ID, using = "h_sub_menu")
    private WebElement ellipses;

    @FindBy(xpath = "//div[@class='seclevelnav']")
    private WebElement secLevelNavDiv;

    @FindBy(xpath = "//a[contains(text(),'WEATHER')]")
    private WebElement weatherLink;

    public NDTVHomePO(WebDriver driver) {
        this(driver, FrameworkConfig.getInstance().getConfigProperties());
    }

    public NDTVHomePO(WebDriver driver, Properties config) {
        super(driver, config);
        PageFactory.initElements(ajaxElementLocatorFactory, this);
    }

    public NDTVWeatherPO navigateToWeatherPage() {
        ellipses.click();
        wait.until(ExpectedConditions.visibilityOf(weatherLink)).click();
        log.info("navigating to weather page");
        return new NDTVWeatherPO(driver);
    }

    @Override
    protected By getUniqueElement() {
        return By.id("_embedhtml");
    }
}
