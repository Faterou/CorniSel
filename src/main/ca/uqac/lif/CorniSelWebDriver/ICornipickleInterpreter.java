package ca.uqac.lif.CorniSelWebDriver;

import org.openqa.selenium.WebElement;

public interface ICornipickleInterpreter {
	boolean setCornipickleProperties(String properties);
	void evaluateAll(WebElement event);
}
