package ca.uqac.lif.cornipickle.driver;

import java.util.Map;

import org.openqa.selenium.WebElement;

import ca.uqac.lif.cornipickle.assertions.Function;

public interface TestOracle {
	void check(Function property);
	void evaluateAll(WebElement event);
	CornipickleDriver.UpdateMode getUpdateMode();
	Map<StatementMetadata,Verdict> getVerdicts();
	void resetHistory();
	void clear();
}
