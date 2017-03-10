package ca.uqac.lif.CorniSelWebDriver;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.ErrorHandler;
import org.openqa.selenium.remote.FileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.SessionId;

public class WebDriverDecorator implements WebDriver
{
	private RemoteWebDriver m_webDriver;
	
	public WebDriverDecorator(RemoteWebDriver webDriver) {
		m_webDriver = webDriver;
	}

	@Override
	public void close() {
		m_webDriver.close();
	}
	
	public Object executeAsyncScript(String script, Object... objects) {
		return m_webDriver.executeAsyncScript(script, objects);
	}
	
	public Object executeScript(String script, Object... objects) {
		return m_webDriver.executeScript(script, objects);
	}

	@Override
	public WebElement findElement(By by) {
		return m_webDriver.findElement(by);
	}
	
	public WebElement findElementByClassName(String using) {
		return m_webDriver.findElementByClassName(using);
	}
	
	public WebElement findElementByCssSelector(String using) {
		return m_webDriver.findElementByCssSelector(using);
	}
	
	public WebElement findElementById(String using) {
		return m_webDriver.findElementById(using);
	}
	
	public WebElement findElementByLinkText(String using) {
		return m_webDriver.findElementByLinkText(using);
	}
	
	public WebElement findElementByName(String using) {
		return m_webDriver.findElementByName(using);
	}
	
	public WebElement findElementByPartialLinkText(String using) {
		return m_webDriver.findElementByPartialLinkText(using);
	}
	
	public WebElement findElementByTagName(String using) {
		return m_webDriver.findElementByTagName(using);
	}
	
	public WebElement findElementByXPath(String using) {
		return m_webDriver.findElementByXPath(using);
	}

	@Override
	public List<WebElement> findElements(By by) {
		return m_webDriver.findElements(by);
	}
	
	public List<WebElement> findElementsByClassName(String using) {
		return m_webDriver.findElementsByClassName(using);
	}
	
	public List<WebElement> findElementsByCssSelector(String using) {
		return m_webDriver.findElementsByCssSelector(using);
	}
	
	public List<WebElement> findElementsById(String using) {
		return m_webDriver.findElementsById(using);
	}
	
	public List<WebElement> findElementsByLinkText(String using) {
		return m_webDriver.findElementsByLinkText(using);
	}
	
	public List<WebElement> findElementsByName(String using) {
		return m_webDriver.findElementsByName(using);
	}

	public List<WebElement> findElementsByPartialLinkText(String using) {
		return m_webDriver.findElementsByPartialLinkText(using);
	}
	
	public List<WebElement> findElementsByTagName(String using) {
		return m_webDriver.findElementsByTagName(using);
	}
	
	public List<WebElement> findElementsByXPath(String using) {
		return m_webDriver.findElementsByXPath(using);
	}
	
	@Override
	public void get(String url) {
		m_webDriver.get(url);
	}
	
	public Capabilities getCapabilities() {
		return m_webDriver.getCapabilities();
	}
	
	public CommandExecutor getCommandExecutor() {
		return m_webDriver.getCommandExecutor();
	}

	@Override
	public String getCurrentUrl() {
		return m_webDriver.getCurrentUrl();
	}
	
	public ErrorHandler getErrorHandler() {
		return m_webDriver.getErrorHandler();
	}
	
	public FileDetector getFileDetector() {
		return m_webDriver.getFileDetector();
	}
	
	public Keyboard getKeyboard() {
		return m_webDriver.getKeyboard();
	}
	
	public Mouse getMouse() {
		return m_webDriver.getMouse();
	}

	@Override
	public String getPageSource() {
		return m_webDriver.getPageSource();
	}
	
	public<X> X getScreenshotAs(OutputType<X> outputType) {
		return m_webDriver.getScreenshotAs(outputType);
	}

	public SessionId getSessionId() {
		return m_webDriver.getSessionId();
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
	
	public void perform(Collection<Sequence> actions) {
		m_webDriver.perform(actions);
	}

	@Override
	public void quit() {
		m_webDriver.quit();
	}
	
	public void resetInputState() {
		m_webDriver.resetInputState();
	}
	
	public void setErrorHandler(ErrorHandler handler) {
		m_webDriver.setErrorHandler(handler);
	}
	
	public void setFileDetector(FileDetector detector) {
		m_webDriver.setFileDetector(detector);
	}
	
	public void setLogLevel(Level level) {
		m_webDriver.setLogLevel(level);
	}

	@Override
	public TargetLocator switchTo() {
		return m_webDriver.switchTo();
	}
	
	public String toString() {
		return m_webDriver.toString();
	}
}
