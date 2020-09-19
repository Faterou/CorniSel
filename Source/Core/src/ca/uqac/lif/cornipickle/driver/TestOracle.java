package ca.uqac.lif.cornipickle.driver;

import org.openqa.selenium.WebElement;

import ca.uqac.lif.cornipickle.assertions.Function;
import ca.uqac.lif.cornipickle.assertions.TestResult;
import ca.uqac.lif.cornipickle.driver.CornipickleDriver.UpdateMode;

public interface TestOracle {
	CornipickleDriver check(String name, Function property);
	CornipickleDriver check(Function property);
	void evaluateAll(WebElement event);
	UpdateMode getUpdateMode();
	TestResult getResult();
	void resetHistory();
	void clear();
}
