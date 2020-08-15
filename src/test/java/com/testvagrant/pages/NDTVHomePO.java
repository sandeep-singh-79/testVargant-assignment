package com.testvagrant.pages;

import com.testvagrant.base.BasePageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NDTVHomePO extends BasePageObject {

    public NDTVHomePO(WebDriver driver) {
        super(driver);
    }

    @Override
    protected By getUniqueElement() {
        return By.id("_embedhtml");
    }
}
