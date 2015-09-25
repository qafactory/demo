package pages;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SearchByPage extends AnyPage {

    @FindBy(xpath = "//section[@class='b']/ins[@id='aswift_0_expand']")
    WebElement banner;

    @FindBy(xpath = "//h1")
    WebElement header;

    @FindBy(xpath = "//section[@class='top-desc']/p")
    WebElement description;

    // Search
    @FindBy(xpath = "//div[@id='message']/a")
    WebElement searchErrorMessage;

    @FindBy(xpath = "//div[@class='no-res']")
    WebElement noResultMessage;

    // Results Table
    @FindBy(xpath = "//aside")
    WebElement resultNumber;

    @FindBy(xpath = "//table[@class='content f14']/tbody/tr/td/div/div/a")
    private List<WebElement>  domainsNamesOnPage;

    // --Pagination
    @FindBy(xpath = "//div[@class='page-new']/a")
    private List<WebElement> pagingButtons;

    @FindBy(xpath = "//div[@class='page-new']/a[contains(text(),'<<')]")
    WebElement firstPageIcon;

    @FindBy(xpath = "//div[@class='page-new']/a[contains(text(),'1')]")
    WebElement firstPageButton;

    @FindBy(xpath = "(//div[@class='page-new']/a[contains(text(),'>')])[1]")
    WebElement nextPageIcon;

    @FindBy(xpath = "(//div[@class='page-new']/a[contains(text(),'<')])[1]")
    WebElement prevPageIcon;

    @FindBy(xpath = "//div[@class='page-new']/a[contains(text(),'>>')]")
    WebElement lastPageIcon;

    @FindBy(xpath = "(//div[@class='page-new']/a)[last()]")
    WebElement lastPageButton;

    // last page button when there are 2 - 5 pages results
    @FindBy(xpath = "(//div[@class='page-new']/a[contains(text(),'>')])[1]/preceding-sibling::a[1]")
    WebElement lastVisiblePage;

    // --Domains
    @FindBy(xpath = "//tbody/tr/td[1]/div/div/a")
    List<WebElement> domainsNames;

    @FindBy(xpath = "//tbody/tr/td[2]/div/span[2]")
    List<WebElement> moonRank;

    @FindBy(xpath = "//tbody/tr/td[3]/div/span[2]")
    List<WebElement> pageRank;

    @FindBy(xpath = "//tbody/tr/td[4]/div/span[2]")
    List<WebElement> alexa;

    @FindBy(xpath = "//tbody/tr/td[5]/div/span")
    List<WebElement> dmoz;

    @FindBy(xpath = "//tbody/tr/td[6]/div/span")
    List<WebElement> yahoo;

//------------------------------------------------------------------

    public SearchByPage(WebDriver webDriver) {
        super(webDriver);
    }

    // UI
    public String getHeader() {
        return wait.until(ExpectedConditions.visibilityOf(header)).getText();
    }

    public String getDescription() {
        return description.getText();
    }

    public String getSearchPlaceholder() {
        return searchField.getAttribute("placeholder");
    }

    public boolean isBannerDisplayed() {
        return banner.isDisplayed();
    }

    // Search
    public String getSearchErrorMessage() {
        wait.until(ExpectedConditions.visibilityOf(searchErrorMessage));
        return searchErrorMessage.getText();
    }

    public String getNoResultsMessage() {
        return noResultMessage.getText();
    }

    // Results Table
    public int getResultNumber() {
        String results = wait.until(ExpectedConditions.visibilityOf(resultNumber)).getText().replaceAll(",","");
        int number = Integer.parseInt(results);
        return number;
    }

    public int getDomainsNumberOnPage() {
        if(domainsNamesOnPage.size() == 0){
            throw new Error("No domains");
        }else {
            return domainsNamesOnPage.size();
        }
    }

    public void gotoNextPage(WebElement nextPageIcon) {
        wait.until(ExpectedConditions.visibilityOf(nextPageIcon)).click();
    }

    public void gotoFirstPage(WebElement firstPageIcon) {
        wait.until(ExpectedConditions.visibilityOf(firstPageIcon)).click();
    }

    public void gotoLastPage(WebElement lastPageIcon) {
        wait.until(ExpectedConditions.visibilityOf(lastPageIcon)).click();
    }

    public int getNumberOfPages() {
        int number;
        int buttons = pagingButtons.size();

        if(buttons > 6){
            gotoLastPage(lastPageIcon);
            number = Integer.parseInt(lastPageButton.getText());

        }else if(buttons > 3 && buttons <= 6){
            number = Integer.parseInt(lastVisiblePage.getText());

        }else if(buttons == 3){
            number = 2;

        }else if(buttons == 0 ){
            number = 1;

        }else {
            throw new Error("Something wrong with paging");
        }

        return number;
    }

    public int getTotalNumberOfDomains() {

        int pages = getNumberOfPages();
        int domains = 0;

        if(pages > 6){
            int lastPage= getDomainsNumberOnPage();
            gotoFirstPage(firstPageIcon);
            int firstPage= getDomainsNumberOnPage();
            domains = firstPage*(pages-1) + lastPage;

        }else if(pages >=3 && pages <= 6) {

            gotoLastPage(lastVisiblePage);
            int lastPage = getDomainsNumberOnPage();
            gotoFirstPage(firstPageButton);
            int firstPage = getDomainsNumberOnPage();
            domains = firstPage * (pages - 1) + lastPage;

        }else if(pages == 2){
            int firstPage= getDomainsNumberOnPage();
            gotoNextPage(nextPageIcon);
            int lastPage= getDomainsNumberOnPage();
            domains = firstPage + lastPage;

        }else if(pages == 1){
            domains = getDomainsNumberOnPage();

        }else {
            throw new Error("Something wrong with paging");
        }

        return domains;
    }

    public List<String> listOfDomains(){
        List<String> names = new ArrayList<String>();
        String name;
        for(WebElement domain: domainsNames){
            try {
                wait.until(ExpectedConditions.visibilityOf(domain));
                name = domain.getText();
            }catch (StaleElementReferenceException e){
                System.out.println("StaleElementReferenceException - trying one more time");
                wait.until(ExpectedConditions.visibilityOf(domain));
                name = domain.getText();
            }
            names.add(name);
        }
        return names;
    }

    public String getDomainName(int domainNumber) {
        String name = listOfDomains().get(domainNumber-1);
        System.out.println(name + " on Backlinks page");
        return name;
    }

    // --MoonRank
    public List<String> listOfMoonRanks(){
        List<String> ranks = new ArrayList<String>();
        String value;
        for(WebElement rank: moonRank){
            value = rank.getText();
            ranks.add(value);
        }
        return ranks;
    }

    public String getMoonRank(int domainNumber) {
        String text = listOfMoonRanks().get(domainNumber-1);
        String rank = text.replace(" ","");
        return rank;
    }

    // --PageRank
    public List<String> listOfPageRanks(){
        List<String> ranks = new ArrayList<String>();
        String value;
        for(WebElement rank: pageRank){
            value = rank.getText();
            ranks.add(value);
        }
        return ranks;
    }

    public String getPageRank(int domainNumber) {
        String text = listOfPageRanks().get(domainNumber-1);
        String rank = text.replace(" ","");
        return rank;
    }

    // --Alexa
    public List<String> listOfAlexa(){
        List<String> ranks = new ArrayList<String>();
        String value;
        for(WebElement rank:alexa){
            value = rank.getText();
            ranks.add(value);
        }
        return ranks;
    }

    public String getAlexa(int domainNumber) {
        String value = listOfAlexa().get(domainNumber-1);
        return value;
    }

    // --Dmoz
    public List<String> listOfDmoz(){
        List<String> list = new ArrayList<String>();
        String value;
        for(WebElement n: dmoz){
            value = n.getAttribute("class");
            list.add(value);
        }
        return list;
    }

    public boolean isDmoz(int domainNumber){
        String value = listOfDmoz().get(domainNumber-1);
        if (value.equals("in-block stat-no-act v-align")){
            return false;
        }else if (value.equals("in-block stat-act v-align")){
            return true;
        }else{
            throw new Error("class value is wrong");
        }
    }

    // --Yahoo
    public List<String> listOfYahoo(){
        List<String> list = new ArrayList<String>();
        String value;
        for(WebElement n: yahoo){
            value = n.getAttribute("class");
            list.add(value);
        }
        return list;
    }

    public boolean isYahoo(int domainNumber) {
        String value = listOfYahoo().get(domainNumber-1);
        if (value.equals("in-block stat-no-act v-align")){
            return false;
        }else if (value.equals("in-block stat-act v-align")){
            return true;
        }else{
            throw new Error("class value is wrong");
        }
    }

    // Switch to domain window
    String originalWindow;

    public void gotoDomain(int domainNumber) {
        originalWindow = webDriver.getWindowHandle();
        Set<String> existingWindows = webDriver.getWindowHandles();

        WebElement domain = domainsNames.get(--domainNumber);
        action.moveToElement(domain).click().build().perform();

        String newWindow = wait.until(anyWindowOtherThan(existingWindows));
        webDriver.switchTo().window(newWindow);
    }

    public ExpectedCondition<String> anyWindowOtherThan(final Set<String> oldWindows) {
        return new ExpectedCondition<String>() {
            public String apply(WebDriver webDriver){
                Set<String> handles = webDriver.getWindowHandles();
                handles.removeAll(oldWindows);
                return handles.size() > 0 ? handles.iterator().next() : null;
            }
        };
    }

    public void closePage(){
        webDriver.close();
        webDriver.switchTo().window(originalWindow);
    }
}
