package steps;

import base.BaseStep;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.By;

public class AddBasket extends BaseStep {
    protected String productName;

    @Given("a web browser is at the home page")
    public void aWebBrowserIsAtTheHomePage() {
        initializeDriver();
    }

    @And("hover moda under navigation bar")
    public void hoverOnModa() {
        hoverElement("modaSelection");
    }

    @And("scroll until find women watch")
    public void scrollUntilFindWomenWatch() {
        scrollToElement("womenWatch");
        waitBySeconds(2);
    }

    @And("click women watch title")
    public void clickWomenWatch() {
        clicktoElement("womenWatch");
        waitBySeconds(2);
    }

    @And("scroll until find guess brand")
    public void scrollUntilFindGuessBrand() {
        scrollToElement("guessBrand");
        waitBySeconds(2);
    }

    @And("click guess brand to filter")
    public void clickGuessBrand() {
        clicktoElement("guessBrand");
        waitBySeconds(2);
    }

    @And("click third product from list")
    public void clickThirdProduct() {
        productName = webDriver.findElement(By.xpath("(//ul[@id= \"1\"]/li/div/a)[3]")).getAttribute("title");
        clicktoElement("thirdProduct");
        waitBySeconds(2);
    }

    @And("go to new tab")
    public void goNewTab() {
        switchToNewTab();
        waitBySeconds(2);
    }

    @And("check product on product page")
    public void checkProductOnProductPage() {
        String productNameOnPage = webDriver.findElement(By.xpath("//h1[@id = \"product-name\"]")).getText();
        Assert.assertEquals(productName, productNameOnPage);
        waitBySeconds(2);
    }

    @And("click add basket button")
    public void clickAddbasket() {
        clicktoElement("addToChart");
        waitBySeconds(2);
    }

    @And("click basket button")
    public void clickGoBasket() {
        clicktoElement("goToChart");
        waitBySeconds(2);
    }

    @Then("check product on basket page")
    public void checkProductOnBasketPage() {
        String productNameOnPage = webDriver.findElement(By.xpath("//*[@class= \"product_name_3Lh3t\"]")).getText();
        Assert.assertEquals(productName, productNameOnPage);
        waitBySeconds(2);
    }

}
