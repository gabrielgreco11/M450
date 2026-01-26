import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FareCalculatorTest {

    private FareCalculator calculator;

    @BeforeEach
    void setup() {
        calculator = new FareCalculator();
    }

    
    @Test
    void sameZoneTripCostsBaseFare() {
        Trip trip = Trip.sameZone(false);
        assertEquals(2.50, calculator.calculateFare(trip));
    }

   
    @Test
    void twoZoneTripCostsMore() {
        Trip trip = Trip.twoZones(false);
        assertEquals(3.50, calculator.calculateFare(trip));
    }


    @Test
    void threeZoneTripCostsMost() {
        Trip trip = Trip.threeZones(false);
        assertEquals(4.50, calculator.calculateFare(trip));
    }

    
    @Test
    void peakTimeAddsSurcharge() {
        Trip trip = Trip.sameZone(true);
        assertEquals(3.00, calculator.calculateFare(trip));
    }

 
    @Test
    void offPeakHasNoSurcharge() {
        Trip trip = Trip.sameZone(false);
        assertEquals(2.50, calculator.calculateFare(trip));
    }

 
    @Test
    void dailyCapIsNotExceeded() {
        calculator.addFare(6.00);
        calculator.addFare(6.00);
        assertEquals(7.00, calculator.getDailyTotal());
    }

   
    @Test
    void weeklyCapIsNotExceeded() {
        calculator.addWeeklyFare(30.00);
        calculator.addWeeklyFare(30.00);
        assertEquals(35.00, calculator.getWeeklyTotal());
    }

    @Test
    void missingExitAppliesPenaltyFare() {
        Trip trip = Trip.missingExit();
        assertEquals(6.00, calculator.calculateFare(trip));
    }


    @Test
    void exitWithoutEntryThrowsException() {
        Trip trip = Trip.exitWithoutEntry();
        assertThrows(IllegalStateException.class,
                () -> calculator.calculateFare(trip));
    }


    @Test
    void dailyFareResetsNextDay() {
        calculator.addFare(7.00);
        calculator.resetDaily();
        assertEquals(0.0, calculator.getDailyTotal());
    }
}
