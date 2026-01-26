public package fare;


public class Trip {
int zones;
boolean peak;
boolean missingExit;
boolean exitWithoutEntry;


private Trip(int zones, boolean peak) {
this.zones = zones;
this.peak = peak;
}


public static Trip sameZone(boolean peak) {
return new Trip(1, peak);
}


public static Trip twoZones(boolean peak) {
return new Trip(2, peak);
}


public static Trip threeZones(boolean peak) {
return new Trip(3, peak);
}


public static Trip missingExit() {
Trip t = new Trip(0, false);
t.missingExit = true;
return t;
}


public static Trip exitWithoutEntry() {
Trip t = new Trip(0, false);
t.exitWithoutEntry = true;
return t;
}

