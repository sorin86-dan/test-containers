package com.testing;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.logging.Logger;

public class BlogPage extends TemplatePage {

    private WebDriver webDriver;
    private final static Logger logger = Logger.getLogger(BlogPage.class.getName());

    @FindBy(how = How.CLASS_NAME, using = "is-primary")
    private WebElement iAgreeButton;


    public BlogPage(WebDriver webDriver) {
        super(webDriver);
        this.webDriver = webDriver;
    }

    public BlogPage clickIAgreeButton() {
        logger.info("Clicking 'I Agree!' button");
        webDriver.switchTo().frame(webDriver.findElement(By.xpath("//div[@id='cmp-app-container']//iframe")));
        iAgreeButton.click();
        webDriver.switchTo().defaultContent();
        return new BlogPage(webDriver);
    }

    public String getArticleTitle(int nrCrt) {
        logger.info("Retrieving title for article no. " + nrCrt);
        return webDriver.findElement(By.xpath("//article[" + nrCrt + "]//h2")).getText();
    }

    public TemplatePage clickArticleTitle(int nrCrt) {
        logger.info("Clicking title for article no. " + nrCrt);
        webDriver.findElement(By.xpath("//article[" + nrCrt + "]//h2/a")).click();
        return new TemplatePage(webDriver);
    }

}
