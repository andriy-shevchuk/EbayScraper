package page;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * PageObject BasePage class
 *
 * Created by Admin on 03.06.2017.
 */
public class BasePage {

    /**
     * WebDriver variable which is transferred to all child classes
     */
    public WebDriver webDriver;

    /**
     * BasePage constructor
     *
     * @param webDriver webDriver instance
     */
    public BasePage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    /**
     * Common method to get current Page URL
     *
     * @return String with current Page URL
     */
    public String getPageURL() {
        return webDriver.getCurrentUrl();
    }

    /**
     * Common method to get current Page title
     *
     * @return String with current Page title
     */
    public String getPageTitle() {
        return webDriver.getTitle();
    }

    /**
     * Waits until element is displayed using specific max timeout
     *
     * @param element WebElement to wait for
     * @param timeout max timeout in seconds
     * @return WebElement after expected condition
     */
    public WebElement waitUntilElementDisplayed(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(webDriver, timeout);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Waits until element is displayed with constant timeout 15 seconds
     *
     * @param element WebElement to wait for
     * @return WebElement after expected condition
     */
    public WebElement waitUntilElementDisplayed(WebElement element) {
        return waitUntilElementDisplayed(element, 15);
    }

    /**
     * Waits until element is displayed with specific timeout in seconds
     *
     * @param element WebElement to wait for
     * @param timeout max timeout in seconds
     * @return boolean value. True if element is found, false if not
     */
    public boolean isElementDisplayed(WebElement element, int timeout) {
        try {
            waitUntilElementDisplayed(element, timeout).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }
}