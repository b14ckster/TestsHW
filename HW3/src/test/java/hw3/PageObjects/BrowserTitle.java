package hw3.PageObjects;

import org.openqa.selenium.WebDriver;

public class BrowserTitle {
    private String browserTitle;

    public BrowserTitle(WebDriver webDriver) {
        browserTitle = webDriver.getTitle();
    }

    public String getBrowserTitle() {
        return browserTitle.toUpperCase();
    }
}
