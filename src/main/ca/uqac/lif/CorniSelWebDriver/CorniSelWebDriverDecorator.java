package ca.uqac.lif.CorniSelWebDriver;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import ca.uqac.lif.cornipickle.Interpreter;
import ca.uqac.lif.cornipickle.CornipickleParser.ParseException;
import ca.uqac.lif.json.JsonElement;
import ca.uqac.lif.json.JsonParser;
import ca.uqac.lif.json.JsonParser.JsonParseException;

public class CorniSelWebDriverDecorator extends WebDriverDecorator implements ICornipickleInterpreter{

	private Interpreter m_interpreter;
	
	private String m_script;
	
	public enum UpdateMode {MANUAL, AUTOMATIC};
	
	public UpdateMode updateMode = UpdateMode.AUTOMATIC;
	
	public CorniSelWebDriverDecorator(RemoteWebDriver webDriver) {
		super(webDriver);
		m_interpreter = new Interpreter();
		m_script = "";
	}
	
	public boolean setCornipickleProperties(String properties) {
		try {
			m_interpreter.parseProperties(properties);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public void evaluateAll(WebElement event) {
		if(m_script.equals("")) {
			m_script = readJS();
		}
		
		String jsonString = (String)(super.executeScript(m_script, event));
		JsonElement j;
		try {
			j = new JsonParser().parse(jsonString);
			m_interpreter.evaluateAll(j);
		} catch (JsonParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
