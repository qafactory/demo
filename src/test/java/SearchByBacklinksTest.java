import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AnyPage;
import pages.DomainPage;
import pages.SearchByPage;

public class SearchByBacklinksTest extends TestBase {

    SearchByPage searchByPage;
    AnyPage anyPage;
    DomainPage domainPage;

    String domain;

    @BeforeClass
    public void testInit() {
        driver.get(baseUrl);
        searchByPage = PageFactory.initElements(driver, SearchByPage.class);
        anyPage =  PageFactory.initElements(driver, AnyPage.class);
        domainPage =  PageFactory.initElements(driver, DomainPage.class);
    }

    @BeforeMethod
    public void startPage(){
        anyPage.gotoSearchByBacklinksPage();
    }


    @Test
    public void checkUI(){
        Assert.assertEquals(searchByPage.getHeader(), "Search domains by backlinks");
        Assert.assertEquals(searchByPage.getDescription(), "This search tool allows you to see a list of all websites that have backlinks to the website in the search query. Although this function can be found in the standard search, if you would like to avoid all extra information and simply see a list of backlinks leading to a website, this tool provides you with that opportunity. Simply enter your website’s URL (or that of your competitor) and hit “search”. Moonsearch will provide you with a list of all websites that link to that URL. The results will be sorted from highest Moon Rank to lowest Moon Rank, but you have the opportunity to switch to PageRank or Alexa sorting.");
        Assert.assertEquals(searchByPage.getSearchPlaceholder(), "Enter a domain name");
        Assert.assertTrue(searchByPage.isBannerDisplayed());
    }

    @Test
    public void checkSearchValidDomain(){
        // 1 page results
        domain = "life-trip.ru";
        searchByPage.search(domain);
        Assert.assertEquals(searchByPage.getHeader(), "Backlinks Report: " + domain);
        Assert.assertEquals(searchByPage.getTableHeader(), "Backlinks for " + domain + "\n" + "External links leading to the site");
        Assert.assertEquals(searchByPage.getResultNumber(), searchByPage.getTotalNumberOfDomains());

        // 2 pages results
        domain = "rozetka.com.ua";
        searchByPage.search(domain);
        Assert.assertEquals(searchByPage.getHeader(), "Backlinks Report: " + domain);
        Assert.assertEquals(searchByPage.getTableHeader(), "Backlinks for " + domain + "\n" + "External links leading to the site");
        Assert.assertEquals(searchByPage.getResultNumber(), searchByPage.getTotalNumberOfDomains());

        // 3 - 5 pages results
        domain = "hotellook.ru";
        searchByPage.search(domain);
        Assert.assertEquals(searchByPage.getHeader(), "Backlinks Report: " + domain);
        Assert.assertEquals(searchByPage.getTableHeader(), "Backlinks for " + domain + "\n" + "External links leading to the site");
        Assert.assertEquals(searchByPage.getResultNumber(), searchByPage.getTotalNumberOfDomains());

        // > 6 pages results
        domain = "korrespondent.net";
        searchByPage.search(domain);
        Assert.assertEquals(searchByPage.getHeader(), "Backlinks Report: " + domain);
        Assert.assertEquals(searchByPage.getTableHeader(), "Backlinks for " + domain + "\n" + "External links leading to the site");
        Assert.assertEquals(searchByPage.getResultNumber(), searchByPage.getTotalNumberOfDomains());
    }

    @Test
    public void checkDomain(){
        domain = "facebook.com";
        searchByPage.search(domain);

        int domainNumber =  anyPage.generateRandomNumber(1,10);
        String domainName = searchByPage.getDomainName(domainNumber);
        String moonRank = searchByPage.getMoonRank(domainNumber);
        String pageRank = searchByPage.getPageRank(domainNumber);
        String alexa = searchByPage.getAlexa(domainNumber);
        boolean dmoz = searchByPage.isDmoz(domainNumber);
        boolean yahoo = searchByPage.isYahoo(domainNumber);

        searchByPage.gotoDomain(domainNumber);
        Assert.assertEquals(domainName,domainPage.getDomainName());
        Assert.assertEquals(moonRank,domainPage.getMoonRank());
        Assert.assertEquals(pageRank,domainPage.getPageRank());
        Assert.assertEquals(alexa,domainPage.getAlexa());
        Assert.assertEquals(dmoz,domainPage.idDmoz());
        Assert.assertEquals(yahoo,domainPage.isYahoo());

        searchByPage.closePage();
    }

    @Test
    public void checkSearchInvalidDomain(){
        domain = "google com";
        searchByPage.search(domain);
        Assert.assertEquals(searchByPage.getSearchErrorMessage(), "Invalid domain name");
    }

    @Test
    public void checkEmptySearch(){
        domain = "";
        searchByPage.search(domain);
        Assert.assertEquals(searchByPage.getSearchErrorMessage(), "Invalid domain name");
    }

    @Test
    public void checkNoResultsSearch(){
        domain = "newshub.org";
        searchByPage.search(domain);
        Assert.assertEquals(searchByPage.getHeader(), "Backlinks Report: " + domain);
        Assert.assertEquals(searchByPage.getNoResultsMessage(), "No backlinks in our database for domain " + domain);
    }





}
