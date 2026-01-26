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

    // 1️⃣ Stations loaded
    @Test
    void stationDropdownIsVisible() {
        assertTrue(driver.findElement(By.id("entryStation")).isDisplayed());
    }

    // 2️⃣ Entry station selectable
    @Test
    void canSelectEntryStation() {
        driver.findElement(By.id("entryStation")).sendKeys("Central");
    }

    // 3️⃣ Exit station selectable
    @Test
    void canSelectExitStation() {
        driver.findElement(By.id("exitStation")).sendKeys("West");
    }

    // 4️⃣ Fare shown after trip
    @Test
    void fareIsDisplayedAfterTrip() {
        driver.findElement(By.id("calculateFare")).click();
        assertTrue(driver.findElement(By.id("fareResult")).isDisplayed());
    }

    // 5️⃣ Peak indicator visible
    @Test
    void peakIndicatorShown() {
        assertTrue(driver.findElement(By.id("peakIndicator")).isDisplayed());
    }

    // 6️⃣ Daily cap message
    @Test
    void dailyCapMessageShown() {
        assertTrue(driver.getPageSource().contains("Daily Cap"));
    }

    // 7️⃣ Weekly cap message
    @Test
    void weeklyCapMessageShown() {
        assertTrue(driver.getPageSource().contains("Weekly Cap"));
    }

    // 8️⃣ Missing exit shows error
    @Test
    void missingExitShowsError() {
        driver.findElement(By.id("calculateFare")).click();
        assertTrue(driver.getPageSource().contains("Exit station required"));
    }

    // 9️⃣ Penalty fare displayed
    @Test
    void penaltyFareIsDisplayed() {
        assertTrue(driver.getPageSource().contains("Penalty Fare"));
    }

    // 🔟 Fare history updated
    @Test
    void fareHistoryUpdates() {
        assertTrue(driver.findElement(By.id("fareHistory")).isDisplayed());
    }
}