package com.testing;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.logging.Logger;

public class ContactPage extends TemplatePage {

    private WebDriver webDriver;
    private final static Logger logger = Logger.getLogger(ContactPage.class.getName());

    private final static String TITLE = "//h2";
    private final static String NAME = "//input[@class='name']";
    private final static String EMAIL = "//input[@class='email']";
    private final static String WEBSITE = "//input[@class='url']";
    private final static String SUBMIT = "//button[@type='submit']";
    private final static String RESPONSE_TITLE = "//h3";
    private final static String MESSAGE = "//textarea[@class='textarea']";

    @FindBy(how = How.XPATH, using = TITLE)
    private WebElement title;

    @FindBy(how = How.XPATH, using = NAME)
    private WebElement nameField;

    @FindBy(how = How.XPATH, using = EMAIL)
    private WebElement emailField;

    @FindBy(how = How.XPATH, using = WEBSITE)
    private WebElement websiteField;

    @FindBy(how = How.XPATH, using = MESSAGE)
    private WebElement messageField;

    @FindBy(how = How.XPATH, using = SUBMIT)
    private WebElement submitButton;

    @FindBy(how = How.XPATH, using = RESPONSE_TITLE)
    private WebElement responseTitle;

    @FindBy(how = How.CLASS_NAME, using = "is-primary")
    private WebElement iAgreeButton;


    public ContactPage(WebDriver webDriver) {
        super(webDriver);
        this.webDriver = webDriver;
    }

    public ContactPage clickIAgreeButton() {
        logger.info("Clicking 'I Agree!' button");
        webDriver.switchTo().frame(webDriver.findElement(By.xpath("//div[@id='cmp-app-container']//iframe")));
        iAgreeButton.click();
        webDriver.switchTo().defaultContent();
        return new ContactPage(webDriver);
    }

    public String getTitle() {
        logger.info("Retrieving Contact title");
        return title.getText();
    }

    public boolean checkNameFieldPresent() {
        logger.info("Checking if field 'Name' is present");
        return nameField.isDisplayed();
    }

    public boolean checkEmailFieldPresent() {
        logger.info("Checking if field 'Email' is present");
        return emailField.isDisplayed();
    }

    public boolean checkWebsiteFieldPresent() {
        logger.info("Checking if field 'Website' is present");
        return websiteField.isDisplayed();
    }

    public boolean checkMessageFieldPresent() {
        logger.info("Checking if field 'Message' is present");
        return messageField.isDisplayed();
    }

    public boolean checkSubmitButtonPresent() {
        logger.info("Checking if 'Submit' button is present");
        return submitButton.isDisplayed();
    }

    public void fillNameField(String text) {
        logger.info("Filling 'Name' field with value: " + text);
        nameField.click();
        nameField.clear();
        nameField.sendKeys(text);
    }

    public void fillEmailField(String text) {
        logger.info("Filling 'Email' field with value: " + text);
        emailField.click();
        emailField.clear();
        emailField.sendKeys(text);
    }

    public void fillWebsiteField(String text) {
        logger.info("Filling 'Website' field with value: " + text);
        websiteField.click();
        websiteField.clear();
        websiteField.sendKeys(text);
    }

    public void fillMessageField(String text) {
        logger.info("Filling 'Message' field with value: " + text);
        messageField.click();
        messageField.clear();
        messageField.sendKeys(text);
    }

    public ContactPage clickSubmitButton() {
        logger.info("Clicking 'Submit' button");
        submitButton.click();
        return new ContactPage(webDriver);
    }

    public String getSubmittedName() {
        logger.info("Retrieving submitted name");
        return webDriver.findElements(By.xpath("//blockquote[@class='contact-form-submission']/p"))
                .get(0)
                .getText().substring(6);
    }

    public String getSubmittedEmail() {
        logger.info("Retrieving submitted email");
        return webDriver.findElements(By.xpath("//blockquote[@class='contact-form-submission']/p"))
                .get(1)
                .getText().substring(7);
    }

    public String getSubmittedWebsite() {
        logger.info("Retrieving submitted website");
        return webDriver.findElements(By.xpath("//blockquote[@class='contact-form-submission']/p"))
                .get(2)
                .getText().substring(8);
    }

    public String getSubmittedMessage() {
        logger.info("Retrieving submitted message");
        return webDriver.findElements(By.xpath("//blockquote[@class='contact-form-submission']/p"))
                .get(3)
                .getText().substring(9);
    }
}
