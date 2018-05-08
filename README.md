# CorniSel

A selenium web driver wrapper in Java that uses Cornipickle's tests.

With CorniSel, it is possible to use Selenium to drive your tests like you always did while automatically testing your web pages using properties in the [Cornipickle](https://github.com/liflab/cornipickle) language.

## Dependencies

- [Selenium](http://www.seleniumhq.org/download/)
- [Cornipickle](https://github.com/liflab/cornipickle)

## Usage

```
RemoteWebDriver chromeDriver = new ChromeDriver();
CorniSelWebDriver corniSelDriver = new CorniSelWebDriver(chromeDriver);
corniSelDriver.setProperties(yourStringOfProperties);
```

You can then use corniSelDriver as if it were the Chrome Driver. You can also use any other browser driver.

## Test modes

There are two ways to test your website with CorniSel.

### Automatic mode

Every `click()` and `submit()` prompt Cornipickle to evaluate the page with the properties that you set.

### Manual mode

You have to call the function `evaluateAll(WebElement element)` to evaluate the page at its current state with `element` being the last element you clicked.

## Results

The results can be gathered with the function `getVerdicts()` that returns a map where the keys are your statements and the values are the verdicts (TRUE, FALSE, INCONCLUSIVE).
