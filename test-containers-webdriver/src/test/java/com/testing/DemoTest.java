package com.testing;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class DemoTest extends BaseTest {

    private final static int SLEEP_TIME = 1000;

    @BeforeClass
    public void setUp() {
        webDriver.manage().window().maximize();
    }

    @Test
    public void doDemo() throws InterruptedException {
        webDriver.get("https://t3ch5tuff5.wordpress.com");

        MainPage mainPage = new TemplatePage(webDriver).clickHomeMenu();

        assertEquals(mainPage.getTitle(), "Welcome!");
        assertEquals(mainPage.getArticleTitle(1), "Containerization in test automation (III) – UI testing using Selenium Grid and Testcontainers");
        assertEquals(mainPage.getArticleTitle(2),"Test automation and Cloud technologies — Part II: Using AWS ECR and AWS EC2");
        assertEquals(mainPage.getArticleTitle(3),"Containerization in test automation (II) – Containerization with microservices testing");

        Thread.sleep(SLEEP_TIME);

        BlogPage blogPage = mainPage.clickBlogMenu();

        assertEquals(blogPage.getPageTitle(), "Blog");
        assertEquals(blogPage.getArticleTitle(1), "Containerization in test automation (III) – UI testing using Selenium Grid and Testcontainers");
        assertEquals(blogPage.getArticleTitle(2),"Test automation and Cloud technologies — Part II: Using AWS ECR and AWS EC2");
        assertEquals(blogPage.getArticleTitle(3),"Containerization in test automation (II) – Containerization with microservices testing");
        assertEquals(blogPage.getArticleTitle(4),"Containerization in test automation (I) – Containerization with UI testing");
        assertEquals(blogPage.getArticleTitle(5),"Test automation and Cloud technologies — Part I: Using AWS EC2");

        Thread.sleep(SLEEP_TIME);

        AboutPage aboutPage = blogPage.clickAboutMenu();

        assertEquals(aboutPage.getPageTitle(), "About");
        assertEquals(aboutPage.getTitle(), "Some stuffs about me and this blog");
        assertTrue(aboutPage.getDescription().contains("Cloud technologies"));

        Thread.sleep(SLEEP_TIME);

        ContactPage contactPage = aboutPage.clickContactMenu();
        contactPage = contactPage.clickIAgreeButton();

        assertEquals(contactPage.getPageTitle(), "Contact");
        assertEquals(contactPage.getTitle(), "Send Us a Message");
        assertTrue(contactPage.checkNameFieldPresent());
        assertTrue(contactPage.checkEmailFieldPresent());
        assertTrue(contactPage.checkWebsiteFieldPresent());
        assertTrue(contactPage.checkMessageFieldPresent());
        assertTrue(contactPage.checkSubmitButtonPresent());

        Thread.sleep(SLEEP_TIME);

        contactPage.fillNameField("Test Name");
        contactPage.fillEmailField("email@test.com");
        contactPage.fillWebsiteField("https://t3ch5tuff5.wordpress.com");
        contactPage.fillMessageField("This is a test message!");
        contactPage = contactPage.clickSubmitButton();

        assertEquals("Test Name", contactPage.getSubmittedName().strip());
        assertEquals("email@test.com", contactPage.getSubmittedEmail().strip());
        assertEquals("https://t3ch5tuff5.wordpress.com", contactPage.getSubmittedWebsite().strip());
        assertEquals("This is a test message!", contactPage.getSubmittedMessage().strip());

        Thread.sleep(SLEEP_TIME);
    }
}
