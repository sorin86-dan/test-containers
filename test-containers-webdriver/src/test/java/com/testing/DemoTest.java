package com.testing;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class DemoTest extends BaseTest{

    @BeforeClass
    public void setUp() {
        webDriver.manage().window().maximize();
    }

    @Test
    public void doDemo() {
        webDriver.get("https://www.seleniumeasy.com/test/basic-first-form-demo.html");
        DemoPage demoPage = new DemoPage(webDriver);

        demoPage.fillMessage("Test message");
        demoPage.clickMessageBtn();

        assertEquals(demoPage.getMessageText(), "Test message");

        demoPage.fillAField("3");
        demoPage.fillBField("5");
        demoPage.clickSumBtn();

        assertEquals(demoPage.getSumValue(), "8");
    }
}
