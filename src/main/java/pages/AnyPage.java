package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Random;

public class AnyPage extends Page {

    // Search by
    @FindBy(xpath = "//div[@class='block left search mn']//span")
    WebElement searchBy;

    @FindBy(xpath = "//div[@class='block left search mn']//li/a[text()='Backlinks']")
    WebElement searchByBacklinks;

    // Search
    @FindBy(xpath = "//input[@id='q']")
    WebElement searchField;

    @FindBy(xpath = "//input[@type='submit']")
    WebElement searchButton;

    // Table
    @FindBy(xpath = "//span[@class='in-block v-align-t f14']")
    WebElement tableHeader;

//------------------------------------------------------------------

    public AnyPage(WebDriver webDriver) {
        super(webDriver);
    }

    public void pause(int milis) {
        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void type(WebElement locator, String text){
        locator.clear();
        locator.sendKeys(text);
    }

    public int generateRandomNumber(int min,int max){
        Random rand = new Random();
        int  number = rand.nextInt((max - min) + 1) + min;
        return number;
    }

    // --- Navigation
    public void gotoSearchByBacklinksPage() {
        wait.until(ExpectedConditions.visibilityOf(searchBy));
        action.moveToElement(searchBy).moveToElement(searchByBacklinks).click().build().perform();
        wait.until(ExpectedConditions.titleIs("Backlink Checker Tool - MoonSearch"));
    }

    // --- Table
    public String getTableHeader() {
        return tableHeader.getText();
    }

    // --- Search
    public String getSearchPlaceholder() {
        return searchField.getAttribute("placeholder");
    }

    public void search(String query) {
        type(searchField, query);
        searchButton.click();
    }
}
