package fare;


public class FareCalculator {


private double dailyTotal = 0.0;
private double weeklyTotal = 0.0;


public double calculateFare(Trip trip) {
if (trip.exitWithoutEntry) {
throw new IllegalStateException("Exit without entry");
}


if (trip.missingExit) {
return 6.00;
}


double base;
switch (trip.zones) {
case 1: base = 2.50; break;
case 2: base = 3.50; break;
case 3: base = 4.50; break;
default: base = 0;
}


if (trip.peak) base += 0.50;


addFare(base);
addWeeklyFare(base);


return base;
}


public void addFare(double fare) {
dailyTotal = Math.min(7.00, dailyTotal + fare);
}


public void addWeeklyFare(double fare) {
weeklyTotal = Math.min(35.00, weeklyTotal + fare);
}


public double getDailyTotal() {
return dailyTotal;
}


public double getWeeklyTotal() {
return weeklyTotal;
}


public void resetDaily() {
dailyTotal = 0.0;
}
}