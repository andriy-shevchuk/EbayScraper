package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class EbayStorePage extends BasePage{

    @FindBy(xpath = "//*[@class='lvtitle']//a")
    private List<WebElement> itemsList;

    @FindBy(xpath = "//li[@class='lvprice prc']")
    private List<WebElement> pricesList;

    /**
     * LoginPage constructor
     *
     * @param driver WebDriver instance
     */
    public EbayStorePage(WebDriver driver) {
        super(driver);
    }

    public void isPageLoaded() {
    }

    public List<String> scrapeFirstPage() {

        List<String> titles = new ArrayList<>();

        for (WebElement item : itemsList) {
            titles.add(item.getText());
        }


        return titles;
    }
}
