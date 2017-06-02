package ca.uqac.lif.CorniSelWebDriver;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import ca.uqac.lif.CorniSel.CorniSelWebDriver;
import ca.uqac.lif.CorniSel.CorniSelWebElement;

public class WebElementDecoratorTest {
	private RemoteWebDriver m_driver;
	private CorniSelWebDriver m_corniSelDriver;
	
	@Before
	public void setUp() throws Exception {
		m_driver = new ChromeDriver();
		m_corniSelDriver = new CorniSelWebDriver(m_driver);
	}
	
	@Test
	public void getWrappedDriverTest() {
		m_corniSelDriver.get("https://www.xkcd.com");
		
		WebDriver wrappedDriver = ((CorniSelWebElement)m_corniSelDriver.findElementById("topContainer")).getWrappedDriver();
		
		assertTrue(wrappedDriver instanceof org.openqa.selenium.chrome.ChromeDriver);
	}
}
