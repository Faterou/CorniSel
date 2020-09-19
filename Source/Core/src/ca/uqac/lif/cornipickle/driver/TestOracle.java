package ca.uqac.lif.cornipickle.driver;

import org.openqa.selenium.WebElement;

import ca.uqac.lif.cornipickle.assertions.Function;
import ca.uqac.lif.cornipickle.driver.CornipickleDriver.UpdateMode;

public interface TestOracle {
	void check(Function property);
	void evaluateAll(WebElement event);
	UpdateMode getUpdateMode();
	Verdict getVerdict();
	void resetHistory();
	void clear();
}
