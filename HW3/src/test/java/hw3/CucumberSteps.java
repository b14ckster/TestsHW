package hw3;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import hw3.PageObjects.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class CucumberSteps {
    WebDriver webDriver;

    HomePage homePage;
    DifElPage difElPage;

    Properties commonProperties;
    Properties ex1Properies;
    Properties ex2Properties;

    @Before
    public void init() {
        commonProperties = new Properties();
        ex1Properies = new Properties();
        ex2Properties = new Properties();

        FileInputStream fin;
        try {
            fin = new FileInputStream("src/test/resources/hw3/common.properties");
            commonProperties.load(fin);
        }
        catch (IOException e) {
            System.err.println("Common properties file not found");
        }

        try {
            fin = new FileInputStream("src/test/resources/hw3/ex1.properties");
            ex1Properies.load(fin);
        }
        catch (IOException e) {
            System.err.println("Ex1 properties file not found");
        }

        try {
            fin = new FileInputStream("src/test/resources/hw3/ex2.properties");
            ex2Properties.load(fin);
        }
        catch (IOException e) {
            System.err.println("Ex2 properties file not found");
        }

        WebDriverManager.chromedriver().setup();
        webDriver = new ChromeDriver();
    }

    @After
    public void shutDown() {
        webDriver.quit();
    }

    @Given("Web page is opened")
    public void openWebPage() {
        webDriver.navigate().to(commonProperties.getProperty("page.url"));
        webDriver.manage().window().maximize();
    }

    @Given("User is logged in as {string} with password {string}")
    public void performLogIn(String name, String password) {
        Login login = new Login(webDriver);
        login.performLogin(name, password);
    }

    @Then("Browser has a correct title")
    public void checkBrowserTitle() {
        BrowserTitle titleElement = new BrowserTitle(webDriver);
        String browserTitle = titleElement.getBrowserTitle();

        String refTitle = commonProperties.getProperty("page.title");
        
        Assert.assertEquals(browserTitle, refTitle);
    }

    @Then("Username is {string}")
    public void checkLogIn(String name) {
        Login login = new Login(webDriver);
        String userName = login.getUserName();

        Assert.assertEquals(userName, name);
    }

    @Then("There are {int} items in the header")
    public void checkHeaderTitlesNum(int titlesNUm) {
        MainHeader mainHeader = new MainHeader(webDriver);

        Assert.assertTrue(mainHeader.checkHeaderTitlesNum(titlesNUm));
    }

    @Then("Header items have correct names")
    public void checkHeaderTitles() {
        MainHeader mainHeader = new MainHeader(webDriver);
        List<String> headerTitles = mainHeader.getHeaderTitles();
        List<String> refTitles = Arrays.asList(ex1Properies.getProperty("top.menu.titles").split(","));

        Assert.assertEquals(headerTitles, refTitles);
    }

    @When("Focus is on the Home Page")
    public void initHomePage() {
        homePage = new HomePage(webDriver);
    }

    @Then("There are {int} benefit images")
    public void checkImagesNum(int imagesNum) {
        Assert.assertTrue(homePage.checkImagesNum(imagesNum));
    }

    @Then("Benefit images has correct captions")
    public void checkImagesCaptions() {
        List<String> captions = homePage.getCaptionsText();
        List<String> refCaptions = new ArrayList<String>();
        refCaptions.add(ex1Properies.getProperty("benefit.caption1"));
        refCaptions.add(ex1Properies.getProperty("benefit.caption2"));
        refCaptions.add(ex1Properies.getProperty("benefit.caption3"));
        refCaptions.add(ex1Properies.getProperty("benefit.caption4"));

        Assert.assertEquals(captions, refCaptions);
    }

    @Then("Home page has correct header")
    public void checkHomePageTitle() {
        String title = homePage.getMainHeaderTitle();
        String refTitle = ex1Properies.getProperty("home.main.header");

        Assert.assertEquals(title, refTitle);
    }

    @Then("Home page has correct text")
    public void checkHomePageText() {
        String text = homePage.getMianHeaderText();
        String refText = ex1Properies.getProperty("home.main.text");

        Assert.assertEquals(text, refText);
    }

    @When("Focus is switched to the central Iframe")
    public void switchToIframe() {
        homePage.switchToIframe();
    }

    @Then("Central iframe is displayed")
    public void isIframeDisplayed() {
        Assert.assertTrue(homePage.isCenterIframeDisplayed());
    }

    @Then("EPAM logo is displayed")
    public void isEpamLogoDisplayed() {
        Assert.assertTrue(homePage.isLogoDisplayed());
    }

    @Then("Focus is switched to the Home Page")
    public void switchToHomePage() {
        homePage.switchToParentFrame();
    }

    @Then("There is correct text in a sub header")
    public void checkSubHeaderText() {
        String subHeader = homePage.getSubHeaderText();
        String refSubHeader = ex1Properies.getProperty("home.sub.header");

        Assert.assertEquals(subHeader, refSubHeader);
    }

    @Then("Sub header has correct link")
    public void checkSubHeaderLink() {
        String subHeaderLink = homePage.getSubHeaderLink();
        String refSubHeaderLink = ex1Properies.getProperty("home.sub.header.link");

        Assert.assertEquals(subHeaderLink, refSubHeaderLink);
    }

    @Then("Home Page has left section and footer")
    public void checkSideSections() {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(homePage.isLeftSectionDisplayed());
        softAssert.assertTrue(homePage.isFooterDisplayed());

        softAssert.assertAll();
    }

    @When("Header Service menu is opened")
    public void openHeaderServiceMenu() {
        MainHeader mainHeader = new MainHeader(webDriver);
        mainHeader.openHeaderServiceMenu();
    }

    @Then("Header Service menu has {int} options")
    public void checkHeaderServiceOptionsNum(int optionsNum) {
        MainHeader mainHeader = new MainHeader(webDriver);

        Assert.assertTrue(mainHeader.checkServiceTitlesNum(optionsNum));
    }
    
    @Then("Header Service menu has correct options")
    public void checkHeaderServiceOptions() {
        MainHeader mainHeader = new MainHeader(webDriver);
        List<String> options = mainHeader.getServiceTitles();
        List<String> refOptions = Arrays.asList(ex2Properties.getProperty("top.drop.options").split(","));

        Assert.assertEquals(options, refOptions);
    }

    @When("Left side Service menu is opened")
    public void openSideServiceMenu() {
        LeftSection leftSection = new LeftSection(webDriver);
        leftSection.openSideServiceMenu();
    }

    @Then("Left side Service menu has {int} options")
    public void checkSideServiceOptionsNum(int optionsNum) {
        LeftSection leftSection = new LeftSection(webDriver);

        Assert.assertTrue(leftSection.checkSideServiceTitlesNum(optionsNum));
    }

    @Then("Left side Service menu has correct options")
    public void checkSideServiceOptions() {
        LeftSection leftSection = new LeftSection(webDriver);
        List<String> options = leftSection.getSideServiseTitles();
        List<String> refOptions = Arrays.asList(ex2Properties.getProperty("side.drop.options").split(","));

        Assert.assertEquals(options, refOptions);
    }

    @When("Different Elements page is opened")
    public void openDifElementsPage() {
        difElPage = new DifElPage(webDriver);
    }

    @Then("There are {int} Check Boxes")
    public void checkCheckBoxesNum(int checkBoxesNum) {
        Assert.assertTrue(difElPage.checkCheckboxesNum(checkBoxesNum));
        Assert.assertTrue(difElPage.areCheckBoxesDisplayed());
    }

    @Then("There are {int} Radio Buttons")
    public void checkRadioButtonsNum(int radioButtonsNum) {
        Assert.assertTrue(difElPage.checkRadioButtonsNum(radioButtonsNum));
        Assert.assertTrue(difElPage.areRadioButtonsDisplayed());
    }

    @Then("There are Select Box")
    public void isSelectBoxDisplayed() {
        Assert.assertTrue(difElPage.isSelectBoxDisplayed());
    }

    @Then("There are Buttons")
    public void sreButtonsDisplayed() {
        Assert.assertTrue(difElPage.areButtonsDisplayed());
    }

    @Then("Different Elements page has left and right sections")
    public void checkDifElementsPageSideSections() {
        Assert.assertTrue(difElPage.areSideSectionsDisplayed());
    }

    @When("Check Boxes: {string} are clicked")
    public void clickButtons(String buttonNames) {
        String[] buttonNamesList = buttonNames.split(",");
        for (String name : buttonNamesList) {
            String buttonName = name.trim();
            switch (buttonName) {
                case "Water":
                    difElPage.clickCheckBox(0);
                    break;
                case "Earth":
                    difElPage.clickCheckBox(1);
                    break;
                case "Wind":
                    difElPage.clickCheckBox(2);
                    break;
                case "Fire":
                    difElPage.clickCheckBox(3);
                    break;
                default:
                    break;
            }
        }
    }

    @Then("Check Boxes: {string} are selected and there is log info")
    public void checkSelectedButtons(String buttonNames) {
        String[] buttonNamesList = buttonNames.split(",");
        int buttonsCount = buttonNamesList.length - 1;

        SoftAssert softAssert = new SoftAssert();

        for (String name : buttonNamesList) {
            String log = difElPage.getLogText(buttonsCount--);
            String buttonName = name.trim();
            switch (buttonName) {
                case "Water":
                    softAssert.assertTrue(difElPage.isCheckBoxSelected(0));
                    softAssert.assertTrue(log.contains("Water"));
                    break;
                case "Earth":
                    softAssert.assertTrue(difElPage.isCheckBoxSelected(1));
                    softAssert.assertTrue(log.contains("Earth"));
                    break;
                case "Wind":
                    softAssert.assertTrue(difElPage.isCheckBoxSelected(2));
                    softAssert.assertTrue(log.contains("Wind"));
                    break;
                case "Fire":
                    softAssert.assertTrue(difElPage.isCheckBoxSelected(3));
                    softAssert.assertTrue(log.contains("Fire"));
                    break;
                default:
                    break;
            }

            softAssert.assertTrue(log.contains("true"));
        }

        softAssert.assertAll();
    }

    @Then("Check Boxes: {string} are unselected and there is log info")
    public void checkUnselectedButtoms(String buttonNames) {
        String[] buttonNamesList = buttonNames.split(",");
        int buttonsCount = buttonNamesList.length - 1;

        SoftAssert softAssert = new SoftAssert();

        for (String name : buttonNamesList) {
            String log = difElPage.getLogText(buttonsCount--);
            String buttonName = name.trim();
            switch (buttonName) {
                case "Water":
                    softAssert.assertFalse(difElPage.isCheckBoxSelected(0));
                    softAssert.assertTrue(log.contains("Water"));
                    break;
                case "Earth":
                    softAssert.assertFalse(difElPage.isCheckBoxSelected(1));
                    softAssert.assertTrue(log.contains("Earth"));
                    break;
                case "Wind":
                    softAssert.assertFalse(difElPage.isCheckBoxSelected(2));
                    softAssert.assertTrue(log.contains("Wind"));
                    break;
                case "Fire":
                    softAssert.assertFalse(difElPage.isCheckBoxSelected(3));
                    softAssert.assertTrue(log.contains("Fire"));
                    break;
                default:
                    break;
            }

            softAssert.assertTrue(log.contains("false"));
        }

        softAssert.assertAll();
    }

    @When("Radio Button {string} is clicked")
    public void selectRadioButton(String radioButtonName) {
        switch (radioButtonName) {
            case "Gold":
                difElPage.clickRadioButton(0);
                break;
            case "Silver":
                difElPage.clickRadioButton(1);
                break;
            case "Bronze":
                difElPage.clickRadioButton(2);
                break;
            case "Selen":
                difElPage.clickRadioButton(3);
                break;
        
            default:
                break;
        }
    }

    @Then("Radio Button {string} is selected and there is log info")
    public void checkSelectedRadioButtons(String radioButtonName) {
        SoftAssert softAssert = new SoftAssert();

        String log = difElPage.getLogText(0);

        switch (radioButtonName) {
            case "Gold":
                softAssert.assertTrue(difElPage.isRadioButtonSelected(0));
                softAssert.assertTrue(log.contains("Gold"));
                break;
            case "Silver":
                softAssert.assertTrue(difElPage.isRadioButtonSelected(1));
                softAssert.assertTrue(log.contains("Silver"));
                break;
            case "Bronze":
                softAssert.assertTrue(difElPage.isRadioButtonSelected(2));
                softAssert.assertTrue(log.contains("Bronze"));
                break;
            case "Selen":
                softAssert.assertTrue(difElPage.isRadioButtonSelected(3));
                softAssert.assertTrue(log.contains("Selen"));
                break;
        
            default:
                break;
        }

        softAssert.assertTrue(log.contains("metal"));
        softAssert.assertAll();
    }

    @When("Select Box option {string} is clicked")
    public void clickSelectBoxOption(String optionName) {
        switch (optionName) {
            case "Red":
                difElPage.selectOption(1);
                break;
            case "Green":
                difElPage.selectOption(2);
                break;
            case "Blue":
                difElPage.selectOption(3);
                break;
            case "Yellow":
                difElPage.selectOption(4);
                break;
        
            default:
                break;
        }
    }

    @Then("Select Box option {string} is selected and there is log info")
    public void checkSelectedselctBoxOption(String optionName) {
        SoftAssert softAssert = new SoftAssert();

        String log = difElPage.getLogText(0);

        switch (optionName) {
            case "Red":
                softAssert.assertTrue(log.contains("Red"));
                break;
            case "Green":
                softAssert.assertTrue(log.contains("Green"));
                break;
            case "Blue":
                softAssert.assertTrue(log.contains("Blue"));
                break;
            case "Yellow":
                softAssert.assertTrue(log.contains("Yellow"));
                break;
        
            default:
                break;
        }

        softAssert.assertTrue(log.contains("Colors"));
    }
}
