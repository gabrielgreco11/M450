import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FareCalculatorTest {

    private FareCalculator calculator;

    @BeforeEach
    void setup() {
        calculator = new FareCalculator();
    }

    //1️⃣ Same-zone trip
    @Test
    void sameZoneTripCostsBaseFare() {
        Trip trip = Trip.sameZone(false);
        assertEquals(2.50, calculator.calculateFare(trip));
    }

    // 2️⃣ Two-zone trip
    @Test
    void twoZoneTripCostsMore() {
        Trip trip = Trip.twoZones(false);
        assertEquals(3.50, calculator.calculateFare(trip));
    }

    // 3️⃣ Three-zone trip
    @Test
    void threeZoneTripCostsMost() {
        Trip trip = Trip.threeZones(false);
        assertEquals(4.50, calculator.calculateFare(trip));
    }

    // 4️⃣ Peak time surcharge
    @Test
    void peakTimeAddsSurcharge() {
        Trip trip = Trip.sameZone(true);
        assertEquals(3.00, calculator.calculateFare(trip));
    }

    // 5️⃣ Off-peak has no surcharge
    @Test
    void offPeakHasNoSurcharge() {
        Trip trip = Trip.sameZone(false);
        assertEquals(2.50, calculator.calculateFare(trip));
    }

    // 6️⃣ Daily cap applied
    @Test
    void dailyCapIsNotExceeded() {
        calculator.addFare(6.00);
        calculator.addFare(6.00);
        assertEquals(7.00, calculator.getDailyTotal());
    }

    // 7️⃣ Weekly cap applied
    @Test
    void weeklyCapIsNotExceeded() {
        calculator.addWeeklyFare(30.00);
        calculator.addWeeklyFare(30.00);
        assertEquals(35.00, calculator.getWeeklyTotal());
    }

    // 8️⃣ Missing exit applies penalty
    @Test
    void missingExitAppliesPenaltyFare() {
        Trip trip = Trip.missingExit();
        assertEquals(6.00, calculator.calculateFare(trip));
    }

    // 9️⃣ Exit without entry is invalid
    @Test
    void exitWithoutEntryThrowsException() {
        Trip trip = Trip.exitWithoutEntry();
        assertThrows(IllegalStateException.class,
                () -> calculator.calculateFare(trip));
    }

    // 🔟 Daily reset works
    @Test
    void dailyFareResetsNextDay() {
        calculator.addFare(7.00);
        calculator.resetDaily();
        assertEquals(0.0, calculator.getDailyTotal());
    }
}
