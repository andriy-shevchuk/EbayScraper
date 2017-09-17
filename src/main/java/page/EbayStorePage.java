package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class EbayStorePage extends BasePage{

    @FindBy(xpath = "//*[@class='lvtitle']//a")
    private List<WebElement> itemsList;

    @FindBy(xpath = "//li[@class='lvprice prc']")
    private List<WebElement> pricesList;

    @FindBy(xpath = "//*[@class='img']")
    private List<WebElement> imagesList;

    @FindBy(xpath = "//td[@class='pagn-next']//a[@aria-disabled='true']")
    private WebElement disabledNext;

    @FindBy(xpath = "//td[@class='pagn-next']//a")
    private WebElement nextButton;

    @FindBy(id = "FooterLegalNoticeContainer")
    private WebElement updatedInformation;

    /**
     * LoginPage constructor
     *
     * @param driver WebDriver instance
     */
    public EbayStorePage(WebDriver driver) {
        super(driver);
    }

    public boolean isPageLoaded() {
        return waitUntilElementDisplayed(updatedInformation).isDisplayed();
    }

    public List[] scrapeStore() {


        List<String> titles = new ArrayList<>();
        List<String> prices = new ArrayList<>();
        List<String> images = new ArrayList<>();

        do {
            for (WebElement item : itemsList) {
                titles.add(item.getText());
            }
            for (WebElement price : pricesList) {
                prices.add(price.getText());
            }
            for (WebElement image : imagesList) {
                images.add(image.getAttribute("src"));
                //System.out.println(image.getAttribute("src"));
            }

            if (isElementDisplayed(disabledNext, 1))
                break;

            if (isElementDisplayed(nextButton, 2)) {
                nextButton.click();
            } else {
                break;
            }
            isPageLoaded();


        } while (true);

        List[] productData = new List[]{titles, prices, images};

        return productData;
    }
}
