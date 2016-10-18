package CorniSelWebDriver;

import org.openqa.selenium.remote.RemoteWebDriver;

import ca.uqac.lif.cornipickle.CornipickleParser.ParseException;
import ca.uqac.lif.cornipickle.Interpreter;

public class CorniSelWebDriver {
	private RemoteWebDriver m_webDriver;
	
	private Interpreter m_interpreter;
	
	public CorniSelWebDriver(RemoteWebDriver webDriver) {
		m_webDriver = webDriver;
		m_interpreter = new Interpreter();
	}
	
	public RemoteWebDriver getWebDriver() {
		return m_webDriver;
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
}
