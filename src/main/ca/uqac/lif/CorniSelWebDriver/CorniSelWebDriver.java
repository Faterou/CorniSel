package ca.uqac.lif.CorniSelWebDriver;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import ca.uqac.lif.cornipickle.Interpreter;
import ca.uqac.lif.cornipickle.Interpreter.StatementMetadata;
import ca.uqac.lif.cornipickle.Verdict;
import ca.uqac.lif.cornipickle.CornipickleParser.ParseException;
import ca.uqac.lif.json.JsonElement;
import ca.uqac.lif.json.JsonParser;
import ca.uqac.lif.json.JsonParser.JsonParseException;

public class CorniSelWebDriver extends WebDriverDecorator implements ICornipickleInterpreter{

	private Interpreter m_interpreter;
	
	private String m_script;
	
	public static enum UpdateMode {MANUAL, AUTOMATIC};
	
	public UpdateMode m_updateMode = UpdateMode.AUTOMATIC;
	
	public CorniSelWebDriver(RemoteWebDriver driver) {
		super(driver);
		m_interpreter = new Interpreter();
		m_script = "";
	}
	
	@Override
	public void setCornipickleProperties(String properties) throws ParseException{
		m_interpreter.parseProperties(properties);
	}

	@Override
	public void evaluateAll(WebElement event) {
		System.out.println("Evaluation");
		if(m_script.equals("")) {
			m_script = readJS();
		}
		
		String jsonString = (String)(super.executeScript(m_script, event, m_interpreter.getAttributes(), m_interpreter.getTagNames()));
		JsonElement j;
		try {
			j = new JsonParser().parse(jsonString);
			m_interpreter.evaluateAll(j);
		} catch (JsonParseException e1) {
			e1.printStackTrace();
		}
	}
	
	public Map<StatementMetadata,Verdict> getVerdicts() {
		return m_interpreter.getVerdicts();
	}
	
	@Override
	public WebElement findElement(By by) {
		WebElement we = super.findElement(by);
		CorniSelWebElement cswe = new CorniSelWebElement(we,this);
		return cswe;
	}
	
	@Override
	public WebElement findElementByClassName(String using) {
		WebElement we = super.findElementByClassName(using);
		CorniSelWebElement cswe = new CorniSelWebElement(we,this);
		return cswe;
	}
	
	@Override
	public WebElement findElementByCssSelector(String using) {
		WebElement we = super.findElementByCssSelector(using);
		CorniSelWebElement cswe = new CorniSelWebElement(we,this);
		return cswe;
	}
	
	@Override
	public WebElement findElementById(String using) {
		WebElement we = super.findElementById(using);
		CorniSelWebElement cswe = new CorniSelWebElement(we,this);
		return cswe;
	}
	
	@Override
	public WebElement findElementByLinkText(String using) {
		WebElement we = super.findElementByLinkText(using);
		CorniSelWebElement cswe = new CorniSelWebElement(we,this);
		return cswe;
	}
	
	@Override
	public WebElement findElementByName(String using) {
		WebElement we = super.findElementByName(using);
		CorniSelWebElement cswe = new CorniSelWebElement(we,this);
		return cswe;
	}
	
	@Override
	public WebElement findElementByPartialLinkText(String using) {
		WebElement we = super.findElementByPartialLinkText(using);
		CorniSelWebElement cswe = new CorniSelWebElement(we,this);
		return cswe;
	}
	
	@Override
	public WebElement findElementByTagName(String using) {
		WebElement we = super.findElementByTagName(using);
		CorniSelWebElement cswe = new CorniSelWebElement(we,this);
		return cswe;
	}
	
	@Override
	public WebElement findElementByXPath(String using) {
		WebElement we = super.findElementByXPath(using);
		CorniSelWebElement cswe = new CorniSelWebElement(we,this);
		return cswe;
	}
	
	@Override
	public List<WebElement> findElements(By by) {
		List<WebElement> welist = super.findElements(by);
		List<WebElement> cswelist = new ArrayList<WebElement>();
		for(WebElement element : welist) {
			cswelist.add(new CorniSelWebElement(element,this));
		}
		return cswelist;
	}
	
	@Override
	public List<WebElement> findElementsByClassName(String using) {
		List<WebElement> welist = super.findElementsByClassName(using);
		List<WebElement> cswelist = new ArrayList<WebElement>();
		for(WebElement element : welist) {
			cswelist.add(new CorniSelWebElement(element,this));
		}
		return cswelist;
	}
	
	@Override
	public List<WebElement> findElementsByCssSelector(String using) {
		List<WebElement> welist = super.findElementsByCssSelector(using);
		List<WebElement> cswelist = new ArrayList<WebElement>();
		for(WebElement element : welist) {
			cswelist.add(new CorniSelWebElement(element,this));
		}
		return cswelist;
	}
	
	@Override
	public List<WebElement> findElementsById(String using) {
		List<WebElement> welist = super.findElementsById(using);
		List<WebElement> cswelist = new ArrayList<WebElement>();
		for(WebElement element : welist) {
			cswelist.add(new CorniSelWebElement(element,this));
		}
		return cswelist;
	}
	
	@Override
	public List<WebElement> findElementsByLinkText(String using) {
		List<WebElement> welist = super.findElementsByLinkText(using);
		List<WebElement> cswelist = new ArrayList<WebElement>();
		for(WebElement element : welist) {
			cswelist.add(new CorniSelWebElement(element,this));
		}
		return cswelist;
	}
	
	@Override
	public List<WebElement> findElementsByName(String using) {
		List<WebElement> welist = super.findElementsByName(using);
		List<WebElement> cswelist = new ArrayList<WebElement>();
		for(WebElement element : welist) {
			cswelist.add(new CorniSelWebElement(element,this));
		}
		return cswelist;
	}
	
	@Override
	public List<WebElement> findElementsByPartialLinkText(String using) {
		List<WebElement> welist = super.findElementsByPartialLinkText(using);
		List<WebElement> cswelist = new ArrayList<WebElement>();
		for(WebElement element : welist) {
			cswelist.add(new CorniSelWebElement(element,this));
		}
		return cswelist;
	}
	
	@Override
	public List<WebElement> findElementsByTagName(String using) {
		List<WebElement> welist = super.findElementsByTagName(using);
		List<WebElement> cswelist = new ArrayList<WebElement>();
		for(WebElement element : welist) {
			cswelist.add(new CorniSelWebElement(element,this));
		}
		return cswelist;
	}
	
	@Override
	public List<WebElement> findElementsByXPath(String using) {
		List<WebElement> welist = super.findElementsByXPath(using);
		List<WebElement> cswelist = new ArrayList<WebElement>();
		for(WebElement element : welist) {
			cswelist.add(new CorniSelWebElement(element,this));
		}
		return cswelist;
	}
	
	@Override
	public UpdateMode getUpdateMode() {
		return m_updateMode;
	}
	
	public void setAutomaticMode() {
		m_updateMode = UpdateMode.AUTOMATIC;
	}
	
	public void setManualMode() {
		m_updateMode = UpdateMode.MANUAL;
	}
	
	private String readJS() {
		InputStream is;
		try {
			is = getClass().getResourceAsStream("resources/serialization.js");
			BufferedReader bf = new BufferedReader(new InputStreamReader(is));
			String inputLine;
			String script = "";
	        while ((inputLine = bf.readLine()) != null) {
	        	script = script + inputLine + "\n";
	        }
	        
	        return script;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
