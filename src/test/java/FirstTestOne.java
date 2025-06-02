import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FirstTestOne {
    private WebDriver driver;
    private WebDriverWait wait;
    private final String BASE_URL = "http://mts.by";

@BeforeAll
    public static void setupClass() {
    WebDriverManager.chromedriver().create();
}
@BeforeEach
    public void setupTest(){
    driver = new ChromeDriver();
    wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    driver.manage().window().maximize();
    driver.get(BASE_URL);
}
@DisplayName("Проверить название указанного блока")
@Test

    public void testBlockTitle(){
    WebElement blockTitle = driver.findElement(By.xpath("//*[@id=\"pay-section\"]/div/div/div[2]/section/div/h2"));
    Assertions.assertEquals(blockTitle.getText().replaceAll("\\s+", " "),"Онлайн пополнение без комиссии");

}
@DisplayName("Проверить наличие логотипов платёжных систем")
@Test
public void testLogotip(){
    WebElement visa = driver.findElement(By.xpath("//img[contains(@src, 'visa-verified')]"));
    Assertions.assertTrue(visa.isDisplayed());
}
@DisplayName("Проверить работу ссылки <Подробнее о сервисе>")
@Test
public void testLink() {
    WebElement detailsLink = driver.findElement(By.xpath("//*[@id=\"pay-section\"]/div/div/div[2]/section/div/a"));
    String expectedHref = "/help/poryadok-oplaty-i-bezopasnost-internet-platezhey/";
    Assertions.assertTrue(detailsLink.getAttribute("href").contains(expectedHref), "Ссылка 'Подробнее о сервисе' ведет не на ожидаемую страницу");
    Assertions.assertTrue(detailsLink.isEnabled(), "Ссылка 'Подробнее о сервисе' неактивна");
}

@DisplayName("Заполнить поля и проверить работу кнопки <Продолжить>")
@Test
public void testPaymentForm(){
    WebElement serviceType = driver.findElement(By.xpath("//*[@id=\"pay-section\"]/div/div/div[2]/section/div/div[1]/div[1]/div[2]/button"));
    serviceType.click();
    WebElement phoneInput = driver.findElement(By.xpath("//*[@id=\"connection-phone\"]"));
    phoneInput.sendKeys("297777777");
    WebElement buttonContinue = driver.findElement(By.xpath("//*[@id=\"pay-connection\"]/button"));
    Assertions.assertTrue(buttonContinue.isEnabled(),"Кнопка 'Продолжить' неактивна после заполнения формы");
    Assertions.assertEquals("Продолжить", buttonContinue.getText(),"Текст на кнопке не соответствует ожидаемому");
}

@AfterEach
public void teardown() {
    if (driver != null)
        driver.quit();
    }
}








