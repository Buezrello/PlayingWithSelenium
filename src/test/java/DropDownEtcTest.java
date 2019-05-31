import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class DropDownEtcTest {

    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test
    public void testTitle() {
        driver.get("https://www.spicejet.com/");
        Assert.assertTrue(driver.getTitle().toLowerCase().contains("spicejet"), "Incorrect title page, does not contains SpiceJet");
    }

    @Test
    public void testDropDownOneWayTrip() throws InterruptedException {
        driver.get("https://www.spicejet.com/");

        // select round-trip
        driver.findElement(By.id("ctl00_mainContent_rbtnl_Trip_1")).click();

        // now select one way trip
        driver.findElement(By.cssSelector("#ctl00_mainContent_rbtnl_Trip_0")).click();

        // check that return date is disabled (because one-way trip)
        Assert.assertTrue(driver.findElement(By.id("ctl00_mainContent_view_date2")).isDisplayed());

        // select From dynamic dropdown
        driver.findElement(By.id("ctl00_mainContent_ddl_originStation1_CTXTaction")).click();
//        Thread.sleep(2000);
        driver.findElement(By.xpath("//a[@value='BLR']")).click();
        driver.findElement(By.xpath("(//a[@value='MAA'])[2]")).click();

        // select departure date -current (active)
        driver.findElement(By.cssSelector(".ui-state-default.ui-state-highlight.ui-state-active")).click();

//        Thread.sleep(2000);
//        // select return date
//        driver.findElement(By.xpath("//*[@id='Div1']/button")).click();
//        driver.findElement(By.xpath("//*[@id='ui-datepicker-div']/table/tbody/tr[3]/td[4]")).click();



        Thread.sleep(2000);

//        WebDriverWait wait = new WebDriverWait(driver, 20);

//        driver.findElement(By.cssSelector("input[id*='StudentDiscount']")).click();
//        driver.findElement(By.xpath("//*[@id='StudentDiscount']")).click();
        driver.findElement(By.cssSelector("#ctl00_mainContent_chk_StudentDiscount")).click();

//        WebElement discount = wait.until(ExpectedConditions.
//                visibilityOfElementLocated(By.xpath("//*[@id='discount-checkbox']")));
//        discount.click();
//        driver.findElement(By.cssSelector("#ctl00_mainContent_chk_StudentDiscount")).click();


        // select Passengers list
        driver.findElement(By.id("divpaxinfo")).click();

        // select two adult passengers
        driver.findElement(By.id("hrefIncAdt")).click();
        driver.findElement(By.id("btnclosepaxoption")).click();

        // select Currency static dropdown list
        Select select = new Select(driver.findElement(By.id("ctl00_mainContent_DropDownListCurrency")));

        // select USD
        select.selectByVisibleText("USD");

        driver.findElement(By.name("ctl00$mainContent$btn_FindFlights")).click();

        // switch to new window
        String windowOriginal = switchToNewWindow();

        // check title of the new window
        checkTitle("Cheap Air Tickets");

        switchWindowBack(windowOriginal);

        // check that we back - original title
        checkTitle("spicejet");


        Thread.sleep(2000);
    }

    @Test
    public void testDropDownRoundTrip() throws InterruptedException {
        driver.get("https://www.spicejet.com/");

        // select round-trip
        driver.findElement(By.id("ctl00_mainContent_rbtnl_Trip_1")).click();


        // check that return date is enabled (because one-way trip)
        Assert.assertTrue(driver.findElement(By.id("ctl00_mainContent_view_date2")).isEnabled());

        // select From dynamic dropdown
        driver.findElement(By.id("ctl00_mainContent_ddl_originStation1_CTXTaction")).click();
//        Thread.sleep(2000);
        driver.findElement(By.xpath("//a[@value='BLR']")).click();
        driver.findElement(By.xpath("(//a[@value='MAA'])[2]")).click();

        // select departure date - current (active)
        driver.findElement(By.cssSelector(".ui-state-default.ui-state-highlight.ui-state-active")).click();

        // select return date
        Thread.sleep(2000);

//        WebDriverWait wait = new WebDriverWait(driver, 20);
//        WebElement returnDate = wait.until(ExpectedConditions.elementToBeClickable(By.id("ctl00_mainContent_view_date2")));
//        returnDate.click();

        driver.findElement(By.id("ctl00_mainContent_view_date2")).click();

        //find June
        while (!(driver.findElement(By.className("ui-datepicker-title"))).getText().toLowerCase().contains("june")) {
//            System.out.println((driver.findElement(By.className("ui-datepicker-title"))).getText());
            driver.findElement(By.cssSelector(".ui-icon.ui-icon-circle-triangle-e")).click();
        }

        //find 23
        List<WebElement> webElements = driver.findElements(By.className("ui-state-default"));
        for (WebElement day : webElements) {
            if (day.getText().equalsIgnoreCase("23")) {
                day.click();
                break;
            }
        }


        Thread.sleep(2000);

//        WebDriverWait wait = new WebDriverWait(driver, 20);

//        driver.findElement(By.cssSelector("input[id*='StudentDiscount']")).click();
//        driver.findElement(By.xpath("//*[@id='StudentDiscount']")).click();
        driver.findElement(By.cssSelector("#ctl00_mainContent_chk_StudentDiscount")).click();

//        WebElement discount = wait.until(ExpectedConditions.
//                visibilityOfElementLocated(By.xpath("//*[@id='discount-checkbox']")));
//        discount.click();
//        driver.findElement(By.cssSelector("#ctl00_mainContent_chk_StudentDiscount")).click();


        // select Passengers list
        driver.findElement(By.id("divpaxinfo")).click();

        // select two adult passengers
        driver.findElement(By.id("hrefIncAdt")).click();
        driver.findElement(By.id("btnclosepaxoption")).click();

        // select Currency static dropdown list
        Select select = new Select(driver.findElement(By.id("ctl00_mainContent_DropDownListCurrency")));

        // select USD
        select.selectByVisibleText("USD");

        driver.findElement(By.name("ctl00$mainContent$btn_FindFlights")).click();

        // switch to new window
        String windowOriginal = switchToNewWindow();

        // check title of the new window
        checkTitle("Cheap Air Tickets");

        switchWindowBack(windowOriginal);

        // check that we back - original title
        checkTitle("spicejet");


        Thread.sleep(2000);
    }

    private String switchToNewWindow() {
        String windowOriginal = driver.getWindowHandle();

        for (String windowHandle : driver.getWindowHandles()) {
            driver.switchTo().window(windowHandle);
        }

        return windowOriginal;
    }

    private void switchWindowBack(String windowOriginal) {
//        driver.close();
        driver.switchTo().window(windowOriginal);
    }

    private void checkTitle(String expectedTitle) {
        Assert.assertTrue(driver.getTitle().toLowerCase().contains(expectedTitle.toLowerCase()), "Incorrect title");
    }
}
