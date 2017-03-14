package ca.uqac.lif.CorniSelWebDriver;

import org.openqa.selenium.WebElement;

import ca.uqac.lif.cornipickle.CornipickleParser.ParseException;

public interface ICornipickleInterpreter {
	void setCornipickleProperties(String properties) throws ParseException;
	void evaluateAll(WebElement event);
}
