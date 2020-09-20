import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.machinepublishers.jbrowserdriver.JBrowserDriver;

import ca.uqac.lif.cornipickle.assertions.Dimension;
import ca.uqac.lif.cornipickle.assertions.Find;
import ca.uqac.lif.cornipickle.assertions.Function;
import ca.uqac.lif.cornipickle.assertions.Value;
import ca.uqac.lif.cornipickle.assertions.TestResult;
import ca.uqac.lif.cornipickle.driver.CornipickleDriver;

import static ca.uqac.lif.cornipickle.assertions.Logic.ForAll;
import static ca.uqac.lif.cornipickle.assertions.Numbers.IsGreaterOrEqual;
import static ca.uqac.lif.cornipickle.assertions.AssertionExplainer.explainAndDraw;


public class SimpleTest 
{
	static
	{
		//System.setProperty("webdriver.gecko.driver", "/home/sylvain/Desktop/geckodriver");
	}
	
	@Test
	public void test1()
	{
		CornipickleDriver driver = new CornipickleDriver(new JBrowserDriver())
				.check("Items have width 100", 
						ForAll("$x", Find.ByTagName("li"), IsGreaterOrEqual(Dimension.Width("$x"), 100)));
		driver.get("http://localhost/test.html");
		TestResult v = driver.getResult();
		explainAndDraw(v, false, "/tmp/out.dot");
		v.assertVerdict();
		//driver.close();
	}
}
