# Grundlagen

## Aufgabe 1

**Testformen**
- **Unit-Test:** Test einzelner Methoden oder Klassen (z. B. JUnit).
- **Integrationstest:** Test des Zusammenspiels mehrerer Module.
- **Performance-/Lasttest:** Messung unter hoher Belastung.

**Praxisbeispiele:**
1. Unit-Test: Automatisierte Tests für Preisberechnung.
2. Integrationstest: Datenbank + API-Schnittstelle.
3. Lasttest: Webservice mit JMeter unter hoher Benutzerzahl.

## Aufgabe 2

- **SW-Fehler:** Arrayzugriffsfehler.
- **SW-Mangel:** Fehlende Mehrmandantenfähigkeit trotz Anforderung.
- **Hoher Schaden:** Banksoftware überweist Beträge doppelt → finanzielle Verluste.

## Aufgabe 3

```java
public class PriceCalculatorTestDriver {
    
    static boolean test_calculate_price() {
        double price;
        boolean test_ok = true;

        // Testfall 1: keine Extras --> Kein Rabatt
        price = PriceCalculator.calculatePrice(100, 50, 20, 0, 0);
        System.out.println("Test 1: " + price);
        if (Math.abs(price - 170.0) > 0.001)
            test_ok = false;

        // Testfall 2: 10% Rabatt auf Extras
        price = PriceCalculator.calculatePrice(100, 50, 20, 3, 0);
        System.out.println("Test 2: " + price);
        if (Math.abs(price - 168.0) > 0.001)
            test_ok = false;

        // Testfall 3: 15% Rabatt auf Extras
        price = PriceCalculator.calculatePrice(100, 50, 20, 5, 0);
        System.out.println("Test 3: " + price);
        if (Math.abs(price - 167.0) > 0.001)
            test_ok = false;

        // Testfall 4: zusätzlicher Rabatt
        price = PriceCalculator.calculatePrice(200, 50, 100, 2, 20);
        System.out.println("Test 4: " + price);
        if (Math.abs(price - 270.0) > 0.001)
            test_ok = false;

        return test_ok;
    }

    public static void main(String[] args) {
        boolean ok = test_calculate_price();
        if (ok)
            System.out.println("Alle Tests erfolgreich!");
        else
            System.out.println("Einige Tests sind fehlgeschlagen!");
    }
}
```

## Aufgabe 3 - Bonus

### Fehler
Der Befund lautet wie folgt:

```java
if (extras >= 3) 
    addon_discount = 10;
else if (extras >= 5)
    addon_discount = 15;
```
Wenn Extras beispielsweise 5 beträgt, trifft zuerst Extras ≥ 3 zu und Addon_Discount wird auf 10 gesetzt. Der Zweig Extras ≥ 5 wird nie ausgeführt.

### Verbesserung
Zunächst muss die Bedingung für ≥ 5 geprüft werden, dann ≥ 3 als else-if.

```java
if (extras >= 5)
    addon_discount = 15;
else if (extras >= 3)
    addon_discount = 10;
else
    addon_discount = 0;
```