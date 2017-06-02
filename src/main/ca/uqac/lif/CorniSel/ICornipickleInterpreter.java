package ca.uqac.lif.CorniSel;

import org.openqa.selenium.WebElement;

import ca.uqac.lif.cornipickle.CornipickleParser.ParseException;

public interface ICornipickleInterpreter {
	void setCornipickleProperties(String properties) throws ParseException;
	void evaluateAll(WebElement event);
	CorniSelWebDriver.UpdateMode getUpdateMode();
}
