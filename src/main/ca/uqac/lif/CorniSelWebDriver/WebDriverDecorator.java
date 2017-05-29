package ca.uqac.lif.CorniSelWebDriver;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WebDriverDecorator implements WebDriver
{
	protected WebDriver m_webDriver;
	
	public WebDriverDecorator(RemoteWebDriver webDriver) {
		m_webDriver = webDriver;
	}

	@Override
	public void close() {
		m_webDriver.close();
	}

	@Override
	public WebElement findElement(By by) {
		return m_webDriver.findElement(by);
	}
	
	@Override
	public List<WebElement> findElements(By by) {
		return m_webDriver.findElements(by);
	}
	
	@Override
	public void get(String url) {
		m_webDriver.get(url);
	}

	@Override
	public String getCurrentUrl() {
		return m_webDriver.getCurrentUrl();
	}
	
	@Override
	public String getPageSource() {
		return m_webDriver.getPageSource();
	}
	
	@Override
	public String getTitle() {
		return m_webDriver.getTitle();
	}

	@Override
	public String getWindowHandle() {
		return m_webDriver.getWindowHandle();
	}

	@Override
	public Set<String> getWindowHandles() {
		return m_webDriver.getWindowHandles();
	}

	@Override
	public Options manage() {
		return m_webDriver.manage();
	}

	@Override
	public Navigation navigate() {
		return m_webDriver.navigate();
	}
	
	@Override
	public void quit() {
		m_webDriver.quit();
	}

	@Override
	public TargetLocator switchTo() {
		return m_webDriver.switchTo();
	}
	
	public String toString() {
		return m_webDriver.toString();
	}
}
