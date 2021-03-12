package com.testing;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

public class BaseTest {

    protected RemoteWebDriver webDriver;
    protected String browserName;
    protected BrowserWebDriverContainer browser;

    @Parameters("browser")
    @BeforeClass
    protected void setUp(@Optional("chrome") String browserName) {
        this.browserName = browserName;

        if(browserName.equals("firefox")) {
            browser = new BrowserWebDriverContainer<>()
                    .withCapabilities(new FirefoxOptions());
        } else {
            browser = new BrowserWebDriverContainer<>()
                    .withCapabilities(new ChromeOptions());
        }

        browser.start();
//        System.out.println("VNC address: " + browser.getVncAddress());
        webDriver = browser.getWebDriver();


        webDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        webDriver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
        webDriver.manage().timeouts().setScriptTimeout(15, TimeUnit.SECONDS);

    }

    @AfterClass
    protected void tearDown() {
        webDriver.quit();
        browser.stop();
        browser.close();
    }

}
