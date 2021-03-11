package com.testing;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.containers.DefaultRecordingFileFactory;
import org.testcontainers.containers.VncRecordingContainer;
import org.testcontainers.shaded.okhttp3.OkHttpClient;
import org.testng.annotations.*;

import java.io.File;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.testcontainers.containers.BrowserWebDriverContainer.VncRecordingMode.RECORD_ALL;

public class BaseTest {

    protected RemoteWebDriver webDriver;
    protected String browserName;
    protected BrowserWebDriverContainer<?> browser;

    @Parameters("browser")
    @BeforeClass
    protected void setUp(@Optional("chrome") String browserName) {
        this.browserName = browserName;

        Logger.getLogger(OkHttpClient.class.getName()).setLevel(Level.ALL);

        if(browserName.equals("firefox")) {
            browser = new BrowserWebDriverContainer<> ()
                    .withCapabilities(new FirefoxOptions())
                    .withRecordingMode(RECORD_ALL, new File("target/"))
                    .withRecordingFileFactory(new DefaultRecordingFileFactory());
        } else {
            browser = new BrowserWebDriverContainer<> ()
                    .withCapabilities(new ChromeOptions())
                    .withRecordingMode(RECORD_ALL, new File("target/"))
                    .withRecordingFileFactory(new DefaultRecordingFileFactory());
        }

        browser.start();
        webDriver = browser.getWebDriver();

        webDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        webDriver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
        webDriver.manage().timeouts().setScriptTimeout(15, TimeUnit.SECONDS);


    }

    @AfterClass
    protected void tearDown() {
        browser.stop();
        browser.close();
        webDriver.quit();
    }

    protected String getErrorMessage(String message) {
        return message.substring(0, message.indexOf("\n"));
    }

    public static void takeSnapShot(WebDriver webdriver, String fileWithPath) throws Exception{
        TakesScreenshot scrShot =((TakesScreenshot)webdriver);
        File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
        File DestFile = new File(fileWithPath);

        FileUtils.copyFile(SrcFile, DestFile);
    }

}
