package ca.uqac.lif.CorniSelWebDriver;

import org.openqa.selenium.WebElement;

import ca.uqac.lif.cornipickle.Interpreter;

public class CorniSelWebElementDecorator extends WebElementDecorator{
	
	private ICornipickleInterpreter m_interpreter;

	public CorniSelWebElementDecorator(WebElement webElement, ICornipickleInterpreter interpreter) {
		super(webElement);
		m_interpreter = interpreter;
	}
	
	@Override
	public void click() {
		m_interpreter.evaluateAll(this.getWebElement());
		super.click();
	}
	
}