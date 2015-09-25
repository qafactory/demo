package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class Page {

	protected WebDriver webDriver;
	protected WebDriverWait wait;
	protected Actions action;

	public Page(WebDriver webDriver) {
		this.webDriver = webDriver;
		wait = new WebDriverWait(webDriver,10);
		action = new Actions(webDriver);
	}
}
