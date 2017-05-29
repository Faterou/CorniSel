package ca.uqac.lif.CorniSelWebDriver;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CorniSelWebElement extends WebElementDecorator{
	
	private ICornipickleInterpreter m_interpreter;

	public CorniSelWebElement(WebElement webElement, ICornipickleInterpreter interpreter) {
		super(webElement);
		m_interpreter = interpreter;
	}
	
	@Override
	public void click() {
		if(m_interpreter.getUpdateMode().equals(CorniSelWebDriver.UpdateMode.AUTOMATIC)) {
			m_interpreter.evaluateAll(this.getWebElement());
			super.click();
		}
		else {
			super.click();
		}
	}
	
	@Override
	public void submit() {
		if(m_interpreter.getUpdateMode().equals(CorniSelWebDriver.UpdateMode.AUTOMATIC)) {
			m_interpreter.evaluateAll(this.getWebElement());
			super.submit();
		}
		else {
			super.submit();
		}
	}
	
	@Override
	public WebElement findElement(By by) {
		WebElement we = super.findElement(by);
		CorniSelWebElement cswe = new CorniSelWebElement(we,m_interpreter);
		return cswe;
	}
	
	@Override
	public List<WebElement> findElements(By by) {
		List<WebElement> welist = super.findElements(by);
		List<WebElement> cswelist = new ArrayList<WebElement>();
		for(WebElement element : welist) {
			cswelist.add(new CorniSelWebElement(element,m_interpreter));
		}
		return cswelist;
	}
}