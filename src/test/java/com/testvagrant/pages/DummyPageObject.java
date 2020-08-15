package com.testvagrant.pages;

import com.testvagrant.base.BasePageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DummyPageObject extends BasePageObject {

    public DummyPageObject(WebDriver driver) {
        super(driver);
    }

    @Override
    protected By getUniqueElement() {
        return null;
    }
}
