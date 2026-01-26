package ui;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;


import static org.junit.jupiter.api.Assertions.*;


class FareUiTest {


WebDriver driver;


@BeforeEach
void setup() {
WebDriverManager.chromedriver().setup();
driver = new ChromeDriver();
driver.get("http://localhost:3000");
}


@AfterEach
void tearDown() {
driver.quit();
}


@Test
void stationDropdownIsVisible() {
assertTrue(driver.findElement(By.id("entryStation")).isDisplayed());
}


@Test
void canSelectEntryStation() {
driver.findElement(By.id("entryStation")).sendKeys("Central");
}


@Test
void canSelectExitStation() {
driver.findElement(By.id("exitStation")).sendKeys("West");
}


@Test
void fareIsDisplayedAfterTrip() {
driver.findElement(By.id("calculateFare")).click();
assertTrue(driver.findElement(By.id("fareResult")).isDisplayed());
}


@Test
void peakIndicatorShown() {
assertTrue(driver.findElement(By.id("peakIndicator")).isDisplayed());
}


@Test
void dailyCapMessageShown() {
assertTrue(driver.getPageSource().contains("Daily Cap"));
}


@Test
void weeklyCapMessageShown() {
assertTrue(driver.getPageSource().contains("Weekly Cap"));
}


@Test
void missingExitShowsError() {
driver.findElement(By.id("calculateFare")).click();
assertTrue(driver.getPageSource().contains("Exit station required"));
}
}