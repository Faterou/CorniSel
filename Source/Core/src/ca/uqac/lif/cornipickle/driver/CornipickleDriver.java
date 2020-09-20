/*
    Cornipickle, validation of layout bugs in web applications
    Copyright (C) 2015-2020 Laboratoire d'informatique formelle
    Université du Québec à Chicoutimi, Canada
    
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.
    
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
    
    You should have received a copy of the GNU Lesser General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ca.uqac.lif.cornipickle.driver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;

import ca.uqac.lif.cornipickle.assertions.Function;
import ca.uqac.lif.cornipickle.assertions.Path;
import ca.uqac.lif.cornipickle.assertions.TestCondition;
import ca.uqac.lif.cornipickle.assertions.TestResult;
import ca.uqac.lif.cornipickle.assertions.Verdict;
import ca.uqac.lif.petitpoucet.DesignatedObject;

/**
 * 
 * @author Francis Guérin, Sylvain Hallé
 *
 */
public class CornipickleDriver extends WebDriverDecorator implements TestOracle{

	private Interpreter m_interpreter;
	
	private String m_highlightScript;
	
	private List<EvaluationListener> m_listeners;
	
	public static enum UpdateMode {MANUAL, AUTOMATIC};
	
	public UpdateMode m_updateMode = UpdateMode.AUTOMATIC;
	
	protected boolean m_evaluatedOnce = false;
	
	public CornipickleDriver(RemoteWebDriver driver) {
		super(driver);
		m_interpreter = new Interpreter();
		m_highlightScript = "";
		m_listeners = new ArrayList<EvaluationListener>();
	}
	
	@Override
	public CornipickleDriver check(String name, Function property) {
		m_interpreter.check(new TestCondition(name, property));
		return this;
	}
	
	@Override
	public CornipickleDriver check(Function property) {
		m_interpreter.check(property);
		return this;
	}
	
	public CornipickleDriver check() {
		evaluateAll(null);
		return this;
	}

	@Override
	public void evaluateAll(WebElement event) {
		WebElement root = m_webDriver.findElement(By.tagName("body"));
		m_interpreter.evaluateAll(root);
		
		highlightElements();
		
		for(EvaluationListener listener : m_listeners) {
			listener.evaluationEvent(this, m_interpreter);
		}
	}
	
	@Override
	public void resetHistory(){
		m_evaluatedOnce = false;
		m_interpreter.resetHistory();
	}
	
	@Override
	public void clear(){
		m_evaluatedOnce = false;
		m_interpreter.clear();
	}
	
	public void outputEvaluation(String filename) throws IOException{
		TestResult verdict = m_interpreter.getVerdict();
		String currentURL = super.m_webDriver.getCurrentUrl();
		String width = String.valueOf(super.m_webDriver.manage().window().getSize().getWidth());
		String height = String.valueOf(super.m_webDriver.manage().window().getSize().getHeight());
		
		FileWriter fw = new FileWriter(new File(filename), true);
		
		fw.write("-----------------------------------------------\n");
		fw.write("Evaluation\n");
		fw.write("URL: " + currentURL + "\n");
		fw.write("Width: " + width + " px\n");
		fw.write("Height: " + height + " px\n");
		fw.write("Overall result: ");
		
		Boolean overallVerdict = verdict.getResult();
		fw.write(overallVerdict.toString() + "\n\n");
		fw.write(verdict.toString());		
		fw.write("\n");
		fw.close();
	}
	
	@Override
	public TestResult getResult() {
		if (!m_evaluatedOnce)
		{
			check();
		}
		return m_interpreter.getVerdict();
	}
	
	@Override
	public UpdateMode getUpdateMode() {
		return m_updateMode;
	}
	
	public void setAutomaticMode() {
		m_updateMode = UpdateMode.AUTOMATIC;
	}
	
	public void setManualMode() {
		m_updateMode = UpdateMode.MANUAL;
	}
	
	/*
	 * Add a listener that will throw an event every time an evaluation has finished
	 */
	public void addListener(EvaluationListener listener) {
		m_listeners.add(listener);
	}
	
	@Override
	public WebElement findElement(By by) {
		WebElement we = super.findElement(by);
		CornipickleWebElement cswe = new CornipickleWebElement((RemoteWebElement)we,this);
		return cswe;
	}
	
	@Override
	public List<WebElement> findElements(By by) {
		List<WebElement> welist = super.findElements(by);
		List<WebElement> cswelist = new ArrayList<WebElement>();
		for(WebElement element : welist) {
			cswelist.add(new CornipickleWebElement((RemoteWebElement)element,this));
		}
		return cswelist;
	}
	
	@Override
	public WebElement findElementByXPath(String arg0) {
		WebElement we = super.findElementByXPath(arg0);
		CornipickleWebElement cswe = new CornipickleWebElement((RemoteWebElement)we,this);
		return cswe;
	}

	@Override
	public List<WebElement> findElementsByXPath(String arg0) {
		List<WebElement> welist = super.findElementsByXPath(arg0);
		List<WebElement> cswelist = new ArrayList<WebElement>();
		for(WebElement element : welist) {
			cswelist.add(new CornipickleWebElement((RemoteWebElement)element,this));
		}
		return cswelist;
	}

	@Override
	public WebElement findElementByTagName(String arg0) {
		WebElement we = super.findElementByTagName(arg0);
		CornipickleWebElement cswe = new CornipickleWebElement((RemoteWebElement)we,this);
		return cswe;
	}

	@Override
	public List<WebElement> findElementsByTagName(String arg0) {
		List<WebElement> welist = super.findElementsByTagName(arg0);
		List<WebElement> cswelist = new ArrayList<WebElement>();
		for(WebElement element : welist) {
			cswelist.add(new CornipickleWebElement((RemoteWebElement)element,this));
		}
		return cswelist;
	}

	@Override
	public WebElement findElementByName(String arg0) {
		WebElement we = super.findElementByName(arg0);
		CornipickleWebElement cswe = new CornipickleWebElement((RemoteWebElement)we,this);
		return cswe;
	}

	@Override
	public List<WebElement> findElementsByName(String arg0) {
		List<WebElement> welist = super.findElementsByName(arg0);
		List<WebElement> cswelist = new ArrayList<WebElement>();
		for(WebElement element : welist) {
			cswelist.add(new CornipickleWebElement((RemoteWebElement)element,this));
		}
		return cswelist;
	}

	@Override
	public WebElement findElementByLinkText(String arg0) {
		WebElement we = super.findElementByLinkText(arg0);
		CornipickleWebElement cswe = new CornipickleWebElement((RemoteWebElement)we,this);
		return cswe;
	}

	@Override
	public WebElement findElementByPartialLinkText(String arg0) {
		WebElement we = super.findElementByPartialLinkText(arg0);
		CornipickleWebElement cswe = new CornipickleWebElement((RemoteWebElement)we,this);
		return cswe;
	}

	@Override
	public List<WebElement> findElementsByLinkText(String arg0) {
		List<WebElement> welist = super.findElementsByLinkText(arg0);
		List<WebElement> cswelist = new ArrayList<WebElement>();
		for(WebElement element : welist) {
			cswelist.add(new CornipickleWebElement((RemoteWebElement)element,this));
		}
		return cswelist;
	}

	@Override
	public List<WebElement> findElementsByPartialLinkText(String arg0) {
		List<WebElement> welist = super.findElementsByPartialLinkText(arg0);
		List<WebElement> cswelist = new ArrayList<WebElement>();
		for(WebElement element : welist) {
			cswelist.add(new CornipickleWebElement((RemoteWebElement)element,this));
		}
		return cswelist;
	}

	@Override
	public WebElement findElementById(String arg0) {
		WebElement we = super.findElementById(arg0);
		CornipickleWebElement cswe = new CornipickleWebElement((RemoteWebElement)we,this);
		return cswe;
	}

	@Override
	public List<WebElement> findElementsById(String arg0) {
		List<WebElement> welist = super.findElementsById(arg0);
		List<WebElement> cswelist = new ArrayList<WebElement>();
		for(WebElement element : welist) {
			cswelist.add(new CornipickleWebElement((RemoteWebElement)element,this));
		}
		return cswelist;
	}

	@Override
	public WebElement findElementByCssSelector(String arg0) {
		WebElement we = super.findElementByCssSelector(arg0);
		CornipickleWebElement cswe = new CornipickleWebElement((RemoteWebElement)we,this);
		return cswe;
	}

	@Override
	public List<WebElement> findElementsByCssSelector(String arg0) {
		List<WebElement> welist = super.findElementsByCssSelector(arg0);
		List<WebElement> cswelist = new ArrayList<WebElement>();
		for(WebElement element : welist) {
			cswelist.add(new CornipickleWebElement((RemoteWebElement)element,this));
		}
		return cswelist;
	}

	@Override
	public WebElement findElementByClassName(String arg0) {
		WebElement we = super.findElementByClassName(arg0);
		CornipickleWebElement cswe = new CornipickleWebElement((RemoteWebElement)we,this);
		return cswe;
	}

	@Override
	public List<WebElement> findElementsByClassName(String arg0) {
		List<WebElement> welist = super.findElementsByClassName(arg0);
		List<WebElement> cswelist = new ArrayList<WebElement>();
		for(WebElement element : welist) {
			cswelist.add(new CornipickleWebElement((RemoteWebElement)element,this));
		}
		return cswelist;
	}
	
	private String readJS(String path) {
		InputStream is;
		try {
			is = getClass().getResourceAsStream(path);
			BufferedReader bf = new BufferedReader(new InputStreamReader(is));
			String inputLine;
			String script = "";
	        while ((inputLine = bf.readLine()) != null) {
	        	script = script + inputLine + "\n";
	        }
	        
	        return script;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public CornipickleDriver highlightElements()
	{
		TestResult result = m_interpreter.getVerdict();
		for (Verdict v : result.getVerdicts())
		{
			if (v.getResult())
			{
				continue;
			}
			List<Object> witnesses = v.getWitness();
			for (Object o : witnesses)
			{
				if (o instanceof DesignatedObject)
				{
					Path p = getPath((DesignatedObject) o);
					if (p != null)
					{
						highlightElement(p);
					}
				}
			}
		}
		return this;
	}
	
	protected static Path getPath(DesignatedObject dob)
	{
		return null;
	}
	
	protected void highlightElement(Path p)
	{
		
	}
}
