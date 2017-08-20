package actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.*;
import page.EbayStorePage;

import java.util.List;

import static java.lang.Thread.sleep;

public class EbayStoreAction extends BaseAction {

    WebDriver webDriver;
    EbayStorePage ebayStorePage;
    String storepage = "https://www.ebay.com/sch/hellojack03/m.html?item=152147685290&epid=1846702999&hash=item236cb56faa%3Ag%3AV3UAAOSwuLZY4c3u&rt=nc&_trksid=p2047675.l2562";
    //String storepage = "https://www.ebay.com/sch/hiqualife786/m.html?item=232390926249&hash=item361b941fa9%3Am%3Amm3X_IzckMqJtDrpOAnBSUg&var=&rt=nc&_trksid=p2047675.l2562";



    /**
     * Initialises FirefoxDriver and navigates to LoginPage
     */
    @Parameters({ "browserName" })
    @BeforeClass
    public void beforeClass(@Optional("firefox") String browserName) {
        webDriver = StartBrowser(browserName);
        webDriver.navigate().to(storepage);
        ebayStorePage = PageFactory.initElements(webDriver, EbayStorePage.class);
        ebayStorePage.isPageLoaded();
    }

    /**
     * Closes WebDriver instance
     */
    @AfterClass
    public void afterClass() {
        webDriver.quit();
    }


    public List<String> doScrape() {
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return ebayStorePage.scrapeFirstPage();

    }

    public EbayStoreAction(String website) {
        webDriver = StartBrowser("firefox");
        webDriver.navigate().to(website);
        ebayStorePage = PageFactory.initElements(webDriver, EbayStorePage.class);
        ebayStorePage.isPageLoaded();
    }


    public void closeBrowser() {
        webDriver.quit();
    }
}
