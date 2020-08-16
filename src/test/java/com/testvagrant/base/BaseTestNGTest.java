package com.testvagrant.base;

import com.testvagrant.config.FrameworkConfig;
import com.testvagrant.config.PropertyFileReader;
import com.testvagrant.driver.WebDriverFactory;
import com.testvagrant.pages.NDTVHomePO;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.util.Arrays;
import java.util.Properties;

import static com.testvagrant.base.context.Context.DRIVER;
import static com.testvagrant.base.context.Context.PageObject.NDTVHomePagePO;
import static java.lang.String.format;
import static org.testng.Assert.fail;

@Slf4j
public abstract class BaseTestNGTest {
    private WebDriverFactory driverFactory;
    protected NDTVHomePO homePO;
    protected WebDriver driver;
    protected Properties config;
    protected Properties testData;

    @BeforeTest(alwaysRun = true)
    public void initTest(ITestContext testContext) {
        // create a WebDriver instance on the basis of the settings
        // provided in framework config properties file
        init_test_variables();
        driver.manage().window().maximize();

        loadApplication(testContext);
        testContext.setAttribute(DRIVER.toString(), driver);
    }

    private void init_test_variables() {
        config = FrameworkConfig.getInstance().getConfigProperties();
        testData = new PropertyFileReader(new File(format("%s/src/test/resources/test_data/data.properties",
                System.getProperty("user.dir"))))
                           .getPropertyFile();
        driverFactory = WebDriverFactory.getInstance();
        driver = driverFactory.getDriver(System.getProperty("driverType", config.getProperty("DRIVERTYPE")));
    }

    protected void loadApplication(ITestContext testContext) {
        driver.navigate().to(config.getProperty("url"));
        initialize_landing_page(testContext);
    }

    private void initialize_landing_page(ITestContext testContext) {
        // initialize landing page object to null
        homePO = new NDTVHomePO(driver);
        try {
            // initialize the object here
            if (driver != null)
                testContext.setAttribute(NDTVHomePagePO.toString(), homePO);
            else throw new NullPointerException("WebDriver object was not initialized!!!");
        } catch (NullPointerException npe) {
            init_test_variables();
            loadApplication(testContext);
            testContext.setAttribute(NDTVHomePagePO.toString(), homePO);
        } catch (Exception e) {
            log.error(format("unable to navigate to Dashboard page due to %s", e.getMessage()));
            log.error(Arrays.toString(e.getStackTrace()));
            fail();
        }
    }

    @AfterTest(alwaysRun = true)
    public void teardownTest() {
        driverFactory.closeDriver();
    }
}
