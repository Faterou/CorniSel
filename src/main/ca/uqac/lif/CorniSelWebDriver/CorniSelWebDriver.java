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
	
	private List<EvaluationListener> m_listeners;
	
	public static enum UpdateMode {MANUAL, AUTOMATIC};
	
	public UpdateMode m_updateMode = UpdateMode.AUTOMATIC;
	
	public CorniSelWebDriver(RemoteWebDriver driver) {
		super(driver);
		m_interpreter = new Interpreter();
		m_script = "";
		m_listeners = new ArrayList<EvaluationListener>();
	}
	
	@Override
	public void setCornipickleProperties(String properties) throws ParseException{
		m_interpreter.parseProperties(properties);
	}

	@Override
	public void evaluateAll(WebElement event) {
		if(m_script.equals("")) {
			m_script = readJS();
		}
		
		String jsonString = (String)(((RemoteWebDriver)super.m_webDriver).executeScript(m_script, event, m_interpreter.getAttributes(), m_interpreter.getTagNames()));
		JsonElement j;
		try {
			j = new JsonParser().parse(jsonString);
			m_interpreter.evaluateAll(j);
		} catch (JsonParseException e1) {
			e1.printStackTrace();
		}
		
		for(EvaluationListener listener : m_listeners) {
			listener.evaluationEvent(this);
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
	public List<WebElement> findElements(By by) {
		List<WebElement> welist = super.findElements(by);
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
	
	/*
	 * Add a listener that will throw an event every time an evaluation has finished
	 */
	public void addListener(EvaluationListener listener) {
		m_listeners.add(listener);
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
