package ca.uqac.lif.CorniSelWebDriver;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WebElementDecorator implements WebElement{

	private WebElement m_webElement;
	
	public WebElementDecorator(WebElement webElement) {
		m_webElement = webElement;
	}
	
	@Override
	public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
		return m_webElement.getScreenshotAs(target);
	}

	@Override
	public void clear() {
		m_webElement.clear();
	}

	@Override
	public void click() {
		m_webElement.click();
	}

	@Override
	public WebElement findElement(By by) {
		return m_webElement.findElement(by);
	}

	@Override
	public List<WebElement> findElements(By by) {
		return m_webElement.findElements(by);
	}

	@Override
	public String getAttribute(String name) {
		return m_webElement.getAttribute(name);
	}

	@Override
	public String getCssValue(String propertyName) {
		return m_webElement.getCssValue(propertyName);
	}

	@Override
	public Point getLocation() {
		return m_webElement.getLocation();
	}

	@Override
	public Rectangle getRect() {
		return m_webElement.getRect();
	}

	@Override
	public Dimension getSize() {
		return m_webElement.getSize();
	}

	@Override
	public String getTagName() {
		return m_webElement.getTagName();
	}

	@Override
	public String getText() {
		return m_webElement.getText();
	}

	@Override
	public boolean isDisplayed() {
		return m_webElement.isDisplayed();
	}

	@Override
	public boolean isEnabled() {
		return m_webElement.isEnabled();
	}

	@Override
	public boolean isSelected() {
		return m_webElement.isSelected();
	}

	@Override
	public void sendKeys(CharSequence... keysToSend) {
		m_webElement.sendKeys(keysToSend);
	}

	@Override
	public void submit() {
		m_webElement.submit();
	}

}
