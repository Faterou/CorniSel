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

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

/**
 * 
 * @author Francis Guérin
 *
 */
public class CornipickleWebElement extends WebElementDecorator
{

	private TestOracle m_interpreter;

	public CornipickleWebElement(RemoteWebElement webElement, TestOracle interpreter)
	{
		super(webElement);
		m_interpreter = interpreter;
	}

	@Override
	public void click()
	{
		if (m_interpreter.getUpdateMode().equals(CornipickleDriver.UpdateMode.AUTOMATIC))
		{
			m_interpreter.evaluateAll(this.getWebElement());
			super.click();
		}
		else
		{
			super.click();
		}
	}

	@Override
	public void submit()
	{
		if (m_interpreter.getUpdateMode().equals(CornipickleDriver.UpdateMode.AUTOMATIC))
		{
			m_interpreter.evaluateAll(this.getWebElement());
			super.submit();
		}
		else
		{
			super.submit();
		}
	}

	@Override
	public WebElement findElement(By by)
	{
		WebElement we = super.findElement(by);
		CornipickleWebElement cswe = new CornipickleWebElement((RemoteWebElement) we, m_interpreter);
		return cswe;
	}

	@Override
	public List<WebElement> findElements(By by)
	{
		List<WebElement> welist = super.findElements(by);
		List<WebElement> cswelist = new ArrayList<WebElement>();
		for (WebElement element : welist)
		{
			cswelist.add(new CornipickleWebElement((RemoteWebElement) element, m_interpreter));
		}
		return cswelist;
	}

	@Override
	public WebElement findElementByXPath(String arg0)
	{
		WebElement we = super.findElementByXPath(arg0);
		CornipickleWebElement cswe = new CornipickleWebElement((RemoteWebElement) we, m_interpreter);
		return cswe;
	}

	@Override
	public List<WebElement> findElementsByXPath(String arg0)
	{
		List<WebElement> welist = super.findElementsByXPath(arg0);
		List<WebElement> cswelist = new ArrayList<WebElement>();
		for (WebElement element : welist)
		{
			cswelist.add(new CornipickleWebElement((RemoteWebElement) element, m_interpreter));
		}
		return cswelist;
	}

	@Override
	public WebElement findElementByTagName(String arg0)
	{
		WebElement we = super.findElementByTagName(arg0);
		CornipickleWebElement cswe = new CornipickleWebElement((RemoteWebElement) we, m_interpreter);
		return cswe;
	}

	@Override
	public List<WebElement> findElementsByTagName(String arg0)
	{
		List<WebElement> welist = super.findElementsByTagName(arg0);
		List<WebElement> cswelist = new ArrayList<WebElement>();
		for (WebElement element : welist)
		{
			cswelist.add(new CornipickleWebElement((RemoteWebElement) element, m_interpreter));
		}
		return cswelist;
	}

	@Override
	public WebElement findElementByName(String arg0)
	{
		WebElement we = super.findElementByName(arg0);
		CornipickleWebElement cswe = new CornipickleWebElement((RemoteWebElement) we, m_interpreter);
		return cswe;
	}

	@Override
	public List<WebElement> findElementsByName(String arg0)
	{
		List<WebElement> welist = super.findElementsByName(arg0);
		List<WebElement> cswelist = new ArrayList<WebElement>();
		for (WebElement element : welist)
		{
			cswelist.add(new CornipickleWebElement((RemoteWebElement) element, m_interpreter));
		}
		return cswelist;
	}

	@Override
	public WebElement findElementByLinkText(String arg0)
	{
		WebElement we = super.findElementByLinkText(arg0);
		CornipickleWebElement cswe = new CornipickleWebElement((RemoteWebElement) we, m_interpreter);
		return cswe;
	}

	@Override
	public WebElement findElementByPartialLinkText(String arg0)
	{
		WebElement we = super.findElementByPartialLinkText(arg0);
		CornipickleWebElement cswe = new CornipickleWebElement((RemoteWebElement) we, m_interpreter);
		return cswe;
	}

	@Override
	public List<WebElement> findElementsByLinkText(String arg0)
	{
		List<WebElement> welist = super.findElementsByLinkText(arg0);
		List<WebElement> cswelist = new ArrayList<WebElement>();
		for (WebElement element : welist)
		{
			cswelist.add(new CornipickleWebElement((RemoteWebElement) element, m_interpreter));
		}
		return cswelist;
	}

	@Override
	public List<WebElement> findElementsByPartialLinkText(String arg0)
	{
		List<WebElement> welist = super.findElementsByPartialLinkText(arg0);
		List<WebElement> cswelist = new ArrayList<WebElement>();
		for (WebElement element : welist)
		{
			cswelist.add(new CornipickleWebElement((RemoteWebElement) element, m_interpreter));
		}
		return cswelist;
	}

	@Override
	public WebElement findElementById(String arg0)
	{
		WebElement we = super.findElementById(arg0);
		CornipickleWebElement cswe = new CornipickleWebElement((RemoteWebElement) we, m_interpreter);
		return cswe;
	}

	@Override
	public List<WebElement> findElementsById(String arg0)
	{
		List<WebElement> welist = super.findElementsById(arg0);
		List<WebElement> cswelist = new ArrayList<WebElement>();
		for (WebElement element : welist)
		{
			cswelist.add(new CornipickleWebElement((RemoteWebElement) element, m_interpreter));
		}
		return cswelist;
	}

	@Override
	public WebElement findElementByCssSelector(String arg0)
	{
		WebElement we = super.findElementByCssSelector(arg0);
		CornipickleWebElement cswe = new CornipickleWebElement((RemoteWebElement) we, m_interpreter);
		return cswe;
	}

	@Override
	public List<WebElement> findElementsByCssSelector(String arg0)
	{
		List<WebElement> welist = super.findElementsByCssSelector(arg0);
		List<WebElement> cswelist = new ArrayList<WebElement>();
		for (WebElement element : welist)
		{
			cswelist.add(new CornipickleWebElement((RemoteWebElement) element, m_interpreter));
		}
		return cswelist;
	}

	@Override
	public WebElement findElementByClassName(String arg0)
	{
		WebElement we = super.findElementByClassName(arg0);
		CornipickleWebElement cswe = new CornipickleWebElement((RemoteWebElement) we, m_interpreter);
		return cswe;
	}

	@Override
	public List<WebElement> findElementsByClassName(String arg0)
	{
		List<WebElement> welist = super.findElementsByClassName(arg0);
		List<WebElement> cswelist = new ArrayList<WebElement>();
		for (WebElement element : welist)
		{
			cswelist.add(new CornipickleWebElement((RemoteWebElement) element, m_interpreter));
		}
		return cswelist;
	}
}