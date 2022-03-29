package base;

import helper.ElementHelper;
import helper.StoreHelper;
import model.ElementInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class BaseStep {

    protected static Logger logger = LogManager.getLogger(BaseStep.class);

    public static WebDriver webDriver;
    public static String baseUrl = "https://www.hepsiburada.com/";

    public void switchToNewTab() {

        webDriver.switchTo().window(webDriver.getWindowHandle());
        Set<String> winHandles = webDriver.getWindowHandles();
        for (String win : winHandles)
            webDriver.switchTo().window(win);
        logger.info("Switched to a new tab");
    }

    public void initializeDriver() {
        webDriver = DriverFactory.getDriver();
        webDriver.get(baseUrl);
        webDriver.manage().window().maximize();
        logger.info("webdriver is started");

    }

    public void hoverElement(String hover) {
        WebElement hoverElement = findElement(hover);
        Actions action = new Actions(webDriver);
        action.moveToElement(hoverElement).perform();
        logger.info("Hovered element : " + hover);
    }

    public void closeDriver() {
        webDriver.close();
        webDriver.quit();
        logger.info("Webdriver is closed");
    }

    public void clicktoElement(String clickElement) {
        WebElement element = findElement(clickElement);
        jsclickElement(element);
        logger.info("Clicked element : " + clickElement);
    }

    public void hardClick(String key) {
        WebElement element = findElement(key);
        waitByMilliSeconds(500);
        clickElement(element);
        logger.info("HardClicked element : " + key);
    }

    public void waitBySeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
            logger.info("Waited " + seconds + " Seconds");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendKeys(String text, String key) {
        if (!key.equals("")) {
            findElement(key).clear();
            findElement(key).sendKeys(text);
        } else {
            findElement(key).sendKeys(text);
        }
        logger.info("Writed  " + text + " to : " + key);
    }

    public void keysDownScroll(int downLoop) {

        for (int i = 0; i < downLoop; i++) {
            try {
                webDriver.findElement(By.tagName("body")).sendKeys(Keys.chord(Keys.DOWN));
            } catch (Exception e) {
                logger.info("Scroll to continue");
            }
        }
    }

    public void waitByMilliSeconds(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
            logger.info("Waited " + milliseconds + " miliseconds");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void jsclickElement(WebElement element) {
        javaScriptClicker(webDriver, element);
        logger.info("Clicked by javascript");
    }

    public void scrollToElement(String key) {
        WebElement element = findElement(key);
        scrollToElement(element);
        logger.info("Scrolled to element : " + key);
    }

    public Object getRandomContent(List<?> contentList) {
        Random rand = new Random();
        int n = rand.nextInt(contentList.size());
        return contentList.get(n);
    }

    public void scrollToElement(WebElement element) {
        if (element != null) {
            scrollTo(element.getLocation().getX(), element.getLocation().getY() - 350);
            waitBySeconds(2);
        }
    }

    private void clickElement(WebElement element) {
        javaScriptClicker(webDriver, element);
    }

    protected void scrollTo(int x, int y) {
        String jsStmt = String.format("window.scrollTo(%d, %d);", x, y);
        executeJS(jsStmt, true);

    }

    protected Object executeJS(String jsStmt, boolean wait) {
        return wait ? getJSExecutor().executeScript(jsStmt, "")
                : getJSExecutor().executeAsyncScript(jsStmt, "");
    }

    protected JavascriptExecutor getJSExecutor() {
        return (JavascriptExecutor) webDriver;
    }

    public void javaScriptClicker(WebDriver driver, WebElement element) {
        JavascriptExecutor jse = ((JavascriptExecutor) driver);
        jse.executeScript("var evt = document.createEvent('MouseEvents');"
                + "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"
                + "arguments[0].dispatchEvent(evt);" + "arguments[0].style.border='6px dotted blue'", element);
    }

    private List<WebElement> findElements(String key) {
        ElementInfo elementInfo = StoreHelper.INSTANCE.findElementInfoByKey(key);
        By infoParam = ElementHelper.getElementInfoToBy(elementInfo);
        return webDriver.findElements(infoParam);
    }

    private WebElement findElement(String key) {
        try {
            ElementInfo elementInfo = StoreHelper.INSTANCE.findElementInfoByKey(key);
            By infoParam = ElementHelper.getElementInfoToBy(elementInfo);
            WebDriverWait webDriverWait = new WebDriverWait(webDriver, 5, 300);
            WebElement webElement = webDriverWait
                    .until(ExpectedConditions.presenceOfElementLocated(infoParam));
            ((JavascriptExecutor) webDriver).executeScript(
                    "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'center'})",
                    webElement);
            return webElement;

        } catch (TimeoutException e) {
            Assert.fail("' The element to be created did not occur in the specified time:  '" + key);
            return null;
        }

    }
}
