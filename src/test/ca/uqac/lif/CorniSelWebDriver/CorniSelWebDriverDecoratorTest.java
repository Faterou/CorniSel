package ca.uqac.lif.CorniSelWebDriver;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import ca.uqac.lif.cornipickle.CornipickleParser.ParseException;
import ca.uqac.lif.cornipickle.Interpreter.StatementMetadata;
import ca.uqac.lif.cornipickle.Verdict;

public class CorniSelWebDriverDecoratorTest {

	private RemoteWebDriver m_driver;
	private CorniSelWebDriver m_corniSelDriver;
	
	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
		m_driver = new ChromeDriver();
		m_corniSelDriver = new CorniSelWebDriver(m_driver);
	}
	
	@Test
	public void testFindElementBy() throws ParseException {
		InputStream is;
		String properties = "";
		try {
			is = getClass().getResourceAsStream("resources/Misaligned-elements.cp");
			BufferedReader bf = new BufferedReader(new InputStreamReader(is));
			String inputLine;
	        while ((inputLine = bf.readLine()) != null) {
	        	properties = properties + inputLine + "\n";
	        }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		m_corniSelDriver.setCornipickleProperties(properties);
		
		m_corniSelDriver.get("https://www.xkcd.com");
		m_corniSelDriver.findElement(By.id("topContainer")).click();
		
		for (Entry<StatementMetadata, Verdict> entry : m_corniSelDriver.getVerdicts().entrySet())
		{
			assertEquals(entry.getValue().getValue(), Verdict.Value.FALSE);
		}
		
		m_corniSelDriver.close();
	}

}