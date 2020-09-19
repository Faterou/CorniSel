package ca.uqac.lif.cornisel;

import java.util.Map;

import org.openqa.selenium.WebElement;

public interface TestOracle {
	void setCornipickleProperties(String properties);
	void evaluateAll(WebElement event);
	CorniSelWebDriver.UpdateMode getUpdateMode();
	Map<StatementMetadata,Verdict> getVerdicts();
	void resetHistory();
	void clear();
}
