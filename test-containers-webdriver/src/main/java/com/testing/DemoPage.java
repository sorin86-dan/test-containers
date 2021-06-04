package com.testing;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.logging.Logger;

public class DemoPage extends PageFactory {

    private WebDriver webDriver;
    private final static Logger logger = Logger.getLogger(DemoPage.class.getName());

    private final static String INPUT_MESSAGE_BTN = "//form[@id='get-input']/button";
    private final static String SUM_BTN = "//form[@id='gettotal']/button";

    @FindBy(how = How.ID, using = "at-cv-lightbox-close")
    private WebElement noBannerLink;

    @FindBy(how = How.ID, using = "user-message")
    private WebElement inputMessageField;

    @FindBy(how = How.XPATH, using = INPUT_MESSAGE_BTN)
    private WebElement inputMessageBtn;

    @FindBy(how = How.ID, using = "display")
    private WebElement inputMessageText;

    @FindBy(how = How.ID, using = "sum1")
    private WebElement aField;

    @FindBy(how = How.ID, using = "sum2")
    private WebElement bField;

    @FindBy(how = How.XPATH, using = SUM_BTN)
    private WebElement sumBtn;

    @FindBy(how = How.ID, using = "displayvalue")
    private WebElement sumText;


    public DemoPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void clickNoBanner() {
        logger.info("Removing banner");
        noBannerLink.click();
    }

    public void fillMessage(String text) {
        logger.info("Filling message field with text: " + text);
        inputMessageField.click();
        inputMessageField.clear();
        inputMessageField.sendKeys(text);
    }

    public void clickMessageBtn() {
        logger.info("Clicking message button");
        inputMessageBtn.click();
    }

    public String getMessageText() {
        logger.info("Retrieving output message");
        return inputMessageText.getText();
    }

    public void fillAField(String text) {
        logger.info("Filling field for first number");
        aField.click();
        aField.clear();
        aField.sendKeys(text);
    }

    public void fillBField(String text) {
        logger.info("Filling field for second number");
        bField.click();
        bField.clear();
        bField.sendKeys(text);
    }

    public void clickSumBtn() {
        logger.info("Clicking sum button");
        sumBtn.click();
    }

    public String getSumValue() {
        logger.info("Retrieving sum");
        return sumText.getText();
    }

}
