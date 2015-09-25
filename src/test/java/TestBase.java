import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import util.PropertyLoader;
import util.WebDriverFactory;

import java.util.concurrent.TimeUnit;


public class TestBase {

	protected WebDriver driver;
	protected String remoteUrl;
    protected String browser;
	protected String baseUrl;

	@BeforeClass
	public void init() {
        remoteUrl = PropertyLoader.loadProperty("remote.url");
        browser = PropertyLoader.loadProperty("browser.name");
        baseUrl = PropertyLoader.loadProperty("site.url");

		driver = WebDriverFactory.getInstance(remoteUrl,browser);
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
	}


    @AfterClass
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}


}
