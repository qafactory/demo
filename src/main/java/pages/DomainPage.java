package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class DomainPage extends AnyPage {

    @FindBy(xpath = "//div[@class='site-name']/h1")
    WebElement header;

    @FindBy(xpath = "//div[@class='in-block moonR']/span[@class='in-block v-align-t']")
    WebElement moonRank;

    @FindBy(xpath = "//div[@class='in-block pageR']/span[@class='in-block v-align-t']")
    WebElement pageRank;

    @FindBy(xpath = "//div[@class='in-block alexa']/span[@class='in-block v-align-t']")
    WebElement alexa;

    @FindBy(xpath = "(//span[@class='in-block v-align pl10'])[1]")
    WebElement dmoz;

    @FindBy(xpath = "(//span[@class='in-block v-align pl10'])[2]")
    WebElement yahoo;

//------------------------------------------------------------------

    public DomainPage(WebDriver webDriver) {
        super(webDriver);
    }

    public String getDomainName() {
        wait.until(ExpectedConditions.visibilityOf(header));
        String str = header.getText();
        int from = str.indexOf(":");
        int to = str.lastIndexOf("-");
        String name = str.substring(from+2,to-1);
        System.out.println(name + " on Domain page");
        return name;
    }

    public String getMoonRank() {
        String text = moonRank.getText();
        String rank = text.replace(" ","");
        return rank;
    }

    public String getPageRank() {
        String text = pageRank.getText();
        String rank = text.replace(" ","");
        return rank;
    }

    public String getAlexa() {
        String text = alexa.getText();
        int from = text.lastIndexOf(" ");
        String alexa = text.substring(from+1);
        return alexa;
    }

    public boolean idDmoz() {
        if (dmoz.getText().equals("no")) {
            return false;
        } else if (dmoz.getText().equals("yes")) {
            return true;
        } else {
            throw new Error();
        }
    }

    public boolean isYahoo() {
        if (yahoo.getText().equals("no")) {
            return false;
        } else if (yahoo.getText().equals("yes")) {
            return true;
        } else {
            throw new Error();
        }
    }
}
