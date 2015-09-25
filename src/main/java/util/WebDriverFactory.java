package util;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;


public class WebDriverFactory {

//	Browsers constants
	public static final String CHROME = "chrome";
	public static final String FIREFOX = "firefox";
	public static final String INTERNET_EXPLORER = "ie";

	private static WebDriver webDriver = null;

//	 return RemoteWebDriver
	public static WebDriver getInstance(String remoteUrl, String browser) {

		WebDriver webDriver = null;

		DesiredCapabilities capability = new DesiredCapabilities();
		capability.setJavascriptEnabled(true);

		// In case there is no Hub
		if (remoteUrl == null || remoteUrl.length() == 0) {
			return getInstance(browser);
		}
		if (CHROME.equals(browser)) {
			capability = DesiredCapabilities.chrome();

		} else if (FIREFOX.equals(browser)) {
			capability = DesiredCapabilities.firefox();

		} else if (INTERNET_EXPLORER.equals(browser)) {
			capability = DesiredCapabilities.internetExplorer();

		} else {
			throw new Error("Unsupported browser");
		}

		// Create Remote WebDriver
		try {
			webDriver = new RemoteWebDriver(new URL(remoteUrl), capability);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return webDriver;
	}

	public static WebDriver getInstance(String browser) {

		if (webDriver != null) {
			return webDriver;
		}

		if (CHROME.equals(browser)) {
			webDriver = new ChromeDriver();

		} else if (FIREFOX.equals(browser)) {
			webDriver = new FirefoxDriver();

		} else if (INTERNET_EXPLORER.equals(browser)) {
			webDriver = new InternetExplorerDriver();

		} else {throw new Error("Unsupported browser");
		}

		return webDriver;
	}
}