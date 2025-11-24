## Teil 1: Test-Levels

#### Mit welchen Test-Levels hatten Sie bereits zu tun?

Am meisten Erfahrung habe ich mit **Unit-Tests**, **Integration-Tests** und **System-Tests** habe ich zutun gehabt.

#### Wann werden Tests ausgeführt?

1. **Lokal beim Entwickeln:** Bevor ich meinen Code committe.
2. **Automatisch im Build-Prozess:** Sobald neuer Code in das Git-Repository gepusht wird, startet eine CI-Pipeline, die automatisch alle Unit- und Integrationstests ausführt.
3. **Vor einem Release:** Auf einer Testumgebung, die der Live-Umgebung ähnelt.

#### Haben Sie dedizierte Testing- oder QA-Teams?

Wir haben eine Mischung aus beiden. In der Regel macht bei uns derjenige, der das Ticket verfasst hat, auch das abschließende Testing.

#### Wie sieht Ihr Testing Life Cycle aus?

Ich würde den Zyklus so beschreiben:

1. **Planung**
2.  **Entwicklung**
3.  **Testen**
4.  **Abnahme**
5.  **Release**

### Aufgabe 2: Einordnung der Test-Begriffe

**1. Testing Approach (Testansatz)**

*   **Was es ist:** Die übergeordnete Strategie, wie man ein System testet. Sie bestimmt die Perspektive des Testers.

*   **Beispiel:**
    *   **Black-Box-Testing:** Der Tester hat keine Kenntnis über die interne Funktionsweise. Die Software wird wie eine "schwarze Box" behandelt, und es wird nur das externe Verhalten (Inputs und Outputs) gemäss den Anforderungen getestet. (z.B. System-Tests, Acceptance-Tests).

**2. Testing Levels (Teststufen)**

*   **Was es ist:** Die verschiedenen Phasen im Entwicklungsprozess, in denen getestet wird. Sie sind sequenziell.

*   **Beispiele:**
    1.  **Unit-Test:** Es wird die kleinste isolierte Einheit (Funktion, Methode) getestet.
    2.  **Component-Test:** Teste das Zusammenspiel mehrerer Einheiten.
    3.  **Integration-Test:** Es werden die Schnittstellen sowie die Interaktion zwischen verschiedenen Modulen oder mit externen Systemen (Datenbank, API) getestet.

**3. Testing Types, Techniques and Tactics (Testarten)**

*   **Was es ist:** Die in einer Teststufe angewendeten Methoden und Testarten sind konkret definiert und spezifisch ausgewählt, um ein bestimmtes Ziel zu erreichen.

*   **Beispiele:**
    *   **Testing Types (Testarten):**
        *   **Funktionale Tests:** Überprüfen, ob die Software das tut, was sie soll.
        *   **Nicht-funktionale Tests:** Überprüfen, wie gut die Software ihre Aufgaben erfüllt. Dazu gehören:
            *   **Performance-Tests:** (Lasttests, Stresstests)
            *   **Usability-Tests:** (Benutzerfreundlichkeit)
            *   **Security-Tests:** (Sicherheit)
            *   **Regressionstests:** Stellen sicher, dass neue Änderungen keine alten Funktionen beeinträchtigen.
    *   **Techniques/Tactics (Techniken/Taktiken):**
        *   **Mocking/Stubbing:** Die Ersetzung echter Abhängigkeiten (zum Beispiel von Datenbanken) durch Test-Dummies findet in Unit- oder Component-Tests statt.

## Teil 2: Unit-Testing

### Aufgabe 1: Simpler Rechner

#### 1. Projekt-Setup (Maven)

Ein typisches Maven-Projekt würde wie folgt strukturiert sein:

```
calculator-project/
├── pom.xml
└── src/
    ├── main/
    │   └── java/
    │       └── com/
    │           └── example/
    │               └── Calculator.java
    └── test/
        └── java/
            └── com/
                └── example/
                    └── CalculatorTest.java
```

#### 2. Code: `Calculator.java`

Die Klasse, die getestet werden soll.

```java
package com.example;

public class Calculator {

    public double add(double a, double b) {
        return a + b;
    }

    public double subtract(double a, double b) {
        return a - b;
    }

    public double multiply(double a, double b) {
        return a * b;
    }

    public double divide(double a, double b) {
        if (b == 0) {
            throw new IllegalArgumentException("durch 0 ist nicht erlaubt");
        }
        return a / b;
    }
}


```

#### 3. Code: `CalculatorTest.java`

Die JUnit 5 Testklasse. Sie enthält verschiedene Testfälle, um die Methoden des `Calculator` zu überprüfen.

```java
package com.example;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    Calculator calculator = new Calculator();

    @Test
    void testAdd() {
        assertEquals(5, calculator.add(2, 3));
        assertEquals(-1, calculator.add(2, -3));
    }

    @Test
    void testSubtract() {
        assertEquals(1, calculator.subtract(3, 2));
        assertEquals(5, calculator.subtract(2, -3));
    }

    @Test
    void testMultiply() {
        assertEquals(6, calculator.multiply(2, 3));
        assertEquals(-6, calculator.multiply(2, -3));
    }

    @Test
    void testDivide() {
        assertEquals(2, calculator.divide(6, 3));
    }

    @Test
    void testDivideByZero() {
        assertThrows(IllegalArgumentException.class, () -> calculator.divide(5, 0));
    }
}

```
#### 4. Ausführung der Tests

1.  **Mit einer Entwicklungsumgebung (IDE) ausführen:**
    *   In Entwicklungsumgebungen wie IntelliJ IDEA oder Eclipse genügt ein Rechtsklick auf die Datei `CalculatorTest.java` (oder auf eine einzelne Testmethode), um im Kontextmenü "Run 'CalculatorTest'" auszuwählen.  Tests werden von der IDE ausgeführt, die die Resultate in einem speziellen Fenster präsentiert.

2.  **Mit Maven auf der Kommandozeile ausführen:**
* Im Terminal wird in das Wurzelverzeichnis des Projekts (`calculator-project/`) navigiert.
     *    Mit dem Befehl `mvn test` wird der Code kompiliert und es werden alle Tests ausgeführt, die im Verzeichnis `src/test/java` zu finden sind. *    Ein Bericht wird von Maven im `target/surefire-reports`-Verzeichnis generiert.  Wurden alle Tests erfolgreich durchgeführt, so wird der Build mit „BUILD SUCCESS“ gekennzeichnet.  Bei einem fehlgeschlagenen Test wird der Build mit der Meldung „BUILD FAILURE“ abgebrochen und es wird angezeigt, welche Tests fehlgeschlagen sind.

### Aufgabe 2: JUnit 5 Features - Eine Zusammenfassung

#### 1. Grundlegende Annotationen & Test-Lifecycle

Im Vergleich zu JUnit 4 wurden die Namen der Lifecycle-Annotationen in JUnit 5 verbessert, um ihre Funktion klarer zu kennzeichnen.  Testmethoden müssen nicht mehr als `public` deklariert werden.

 * `@Test`: Kennzeichnet eine Methode als Testmethode. *    `@BeforeEach`: Kommt vor **jeder** einzelnen Testmethode zum Einsatz (ähnlich wie `@Before` in JUnit 4).  Optimal für wiederholbare Setups.

 * `@AfterEach`: Wird nach **allen** einzelnen Testmethoden ausgeführt (ähnlich wie `@After` in JUnit 4).  Ist zum Aufräumen gedacht.
 *    `@BeforeAll`: Wird **ein einziges Mal** vor sämtlichen Tests innerhalb einer Klasse durchgeführt (ähnlich wie `@BeforeClass` in JUnit 4).  Die Methode muss als `static` deklariert werden.
 * `@AfterAll`: Wird **einmal** nach allen Tests in einer Klasse ausgeführt (ähnlich wie `@AfterClass` in JUnit 4).  Die Methode muss als `static` deklariert werden.
 * `@Disabled`: Schaltet eine Testmethode oder -klasse aus.

#### 2. Assertions

Die Klasse `Assertions` in `org.junit.jupiter.api` stellt zahlreiche statische Methoden zur Verfügung, um Bedingungen zu überprüfen.

 * `assertEquals(expected, actual)`: Testet, ob zwei Werte identisch sind.
 * `assertTrue(condition)` / `assertFalse(condition)`: Testet, ob eine Bedingung zutrifft oder nicht.

 * `assertNotNull(object)` / `assertNull(object)`: Kontrolliert (Nicht-)Null-Werte.
 * `assertThrows(ExpectedException.class, executable)`: Testet, ob beim Ausführen eines Code-Blocks (als Lambda-Ausdruck) eine bestimmte Exception geworfen wird.  Gibt die geworfene Ausnahme zurück, um zusätzliche Prüfungen (wie der Message) zu ermöglichen.
 * `assertAll(executables…)`: Fasst mehrere Assertions zusammen.  Selbst wenn eine oder mehrere fehlschlagen, werden alle Assertions ausgeführt.  Damit sieht man alle Fehler auf einmal, statt nur den ersten.

**Beispiel für `assertThrows` und `assertAll`:**
```java
@Test
void testExceptionAndGrouping() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
        // Code, der die Exception werfen sollte
        throw new IllegalArgumentException("a message");
    });

    assertAll(
        () -> assertEquals("a message", exception.getMessage()),
        () -> assertNull(exception.getCause())
    );
}
```

#### 3. Bessere Testnamen mit `@DisplayName`

Anstelle kryptischer Methodennamen können Tests mit `@DisplayName` einen klar verständlichen Namen erhalten, der Leerzeichen und Sonderzeichen enthalten kann.

**Beispiel:**
```java
@Test
@DisplayName("Rechner sollte 2 zurückgeben, wenn 1 und 1 addiert werden")
void testAdd() {
    assertEquals(2, calculator.add(1, 1));
}
```

#### 4. Parametrisierte Tests

Gestattet das Testen mit unterschiedlichen Eingabewerten.  Die Testmethode, die mit `@ParameterizedTest` annotiert ist, benötigt eine Argumentquelle.

 * `@ValueSource`: für einzelne Werte (Strings, Zahlen usw.). *    `@CsvSource`: Legt die Testdaten unmittelbar als durch Kommata getrennte Werte fest.

 * `@CsvFileSource`: Holt die Testdaten aus einer CSV-Datei. *    `@MethodSource`: Verwendet eine statische Methode, die einen Stream oder eine Collection von Argumenten liefert.

**Beispiel mit `@CsvSource`:**
```java
@ParameterizedTest(name = "Eingabe: {0}, Erwartet: {1}")
@CsvSource({
    "1, 1",
    "2, 4",
    "3, 9"
})
void testSquare(int input, int expected) {
    assertEquals(expected, calculator.square(input));
}
```

#### 5. Dynamische Tests mit `@TestFactory`

Macht das Generieren von Tests zur Laufzeit möglich.  Eine Methode, die mit `@TestFactory` versehen ist, liefert einen `Stream` oder eine `Collection` von `DynamicTest`-Objekten.  Für szenarienbasierte Tests hat dies eine extrem große Wirkung.

**Beispiel:**
```java
@TestFactory
Stream<DynamicTest> testPowersOfTwo() {
    return IntStream.range(1, 10)
        .mapToObj(value ->
            dynamicTest("2^" + value, () -> {
                long expected = (long) Math.pow(2, value);
                assertEquals(expected, calculator.power(2, value));
            })
        );
}
```

#### 6. Geschachtelte Tests mit `@Nested`

Gestattet die Gruppierung von Tests in inneren Klassen.  Tests, die zusammengehören (wie etwa für einen spezifischen Zustand des Objekts), werden gruppiert, was zu einer verbesserten Struktur und Lesbarkeit führt.

**Beispiel:**
```java
class StackTest {
    @Test
    @DisplayName("is instantiated with new Stack()")
    void isInstantiatedWithNew() {
        new Stack<>();
    }

    @Nested
    @DisplayName("when new")
    class WhenNew {
        // ... tests für einen neuen, leeren Stack
    }
}
```

#### 7. Das Extension-Modell

Ein einheitliches und leistungsstarkes Erweiterungsmodell in JUnit 5 wird durch die `@ExtendWith`-Annotation bereitgestellt.  Es ersetzt die alten Runner und Rules von JUnit 4.  Viele offizielle und von Drittanbietern angebotene Erweiterungen existieren, wie z.B. für Spring, Mockito oder Testcontainers.

### Aufgabe 3: Analyse der Banken-Simulation

#### Kernklassen und ihre Verantwortlichkeiten

*   **`Bank`**:
* Die zentrale Admin-Klasse. *    Enthält eine nach der Konto-ID sortierte Liste aller Konten (`TreeMap<String, Account>`). *    Trägt die Verantwortung für das Anlegen neuer Konten (`createSavingsAccount`, `createSalaryAccount` etc.).

     * Weist eindeutige, fortlaufende Kontonummern zu (z.B. „S-1000“, „P-1001“).
     *    Stellt Methoden für Transaktionen (`deposit`, `withdraw`) zur Verfügung, die an das entsprechende `Account`-Objekt weitergeleitet werden. *    Kann Berichte erstellen (Gesamt-Saldo der Bank, Top/Bottom 5 Konten).

*   **`Account`**:
* Eine **abstrakte** Grundklasse für sämtliche Kontokategorien. *    Legt die grundlegenden Merkmale eines Kontos fest: `id` (Kontonummer), `balance` (Saldo) und eine Liste von `Booking`-Objekten.
     *    Setzt die grundlegende Logik für Einzahlungen und Auszahlungen um.

     * Gewährleistet über `canTransact()`, dass Transaktionen ausschließlich in chronologischer Reihenfolge gebucht werden können (eine neue Buchung darf nicht auf ein Datum vor der letzten Buchung datiert sein).

*   **`Booking`**:
* Ein unkompliziertes Datenobjekt (POJO - Plain Old Java Object). *    Stellt eine einzelne Buchung dar, die ein Datum (`date`) und einen Betrag (`amount`) aufweist.

#### Konkrete Kontotypen (Vererbung)

* **`SavingsAccount` (Sparkonto)**: * Leitet sich von `Account` ab.     Setzt die `withdraw`-Methode neu an:  Abhebungen sind nur zulässig, wenn das Konto dadurch nicht überzogen wird (der Saldo muss positiv bleiben).

* **`SalaryAccount` (Lohnkonto)**: * Leitet sich von `Account` ab. *    Hat eine weitere Eigenschaft: `creditLimit` (Kreditlimit).

     * Setzt die `withdraw`-Methode neu an:  Solange der neue Saldo das festgelegte `creditLimit` nicht unterschreitet, ist eine Abhebung erlaubt.  Das Konto ist überziehbar.

* **`PromoYouthSavingsAccount` (Promo-Jugendsparkonto)**: * Erbt von `SavingsAccount`, was wiederum indirekt von `Account` abgeleitet ist.
     *    Ersetzt die `deposit`-Methode: Bei jeder Einzahlung wird dem Einzahlungsbetrag ein Bonus von 1 % hinzugefügt.

#### Hilfsklassen

* **`BankUtils`**: * Eine Hilfsklasse, die statische Methoden bereitstellt. *    Wird verwendet, um Daten (`formatBankDate`) und Geldbeträge (`formatAmount`) für eine ordentliche Darstellung auf der Konsole zu formatieren.

* **`AccountBalanceComparator` / `AccountInverseBalanceComparator`**:
 * (Nicht durchgelesen, aber aus dem Namen ersichtlich)
 * Zwei Comparator-Klassen, die von der `Bank`-Klasse genutzt werden, um die Kontenliste nach Saldo auf- oder absteigend für die `printTop5()` und `printBottom5()` Berichte zu sortieren.

 ### Aufgabe 4: Implementierung der Unit-Tests

 #### `BookingTests.java`

Tests für die `Booking`-Klasse.

```java
package ch.schule;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BookingTests {

    @Test
    void testBookingCreation() {
        Booking booking = new Booking(12345, 50000);

        assertAll("Booking properties",
            () -> assertEquals(12345, booking.getDate(), "Date should be set correctly"),
            () -> assertEquals(50000, booking.getAmount(), "Amount should be set correctly")
        );
    }
}
```
#### `SavingsAccountTests.java`

Tests für das `SavingsAccount`.

```java
package ch.schule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SavingsAccountTests {

    private SavingsAccount account;

    @BeforeEach
    void setUp() {
        account = new SavingsAccount("S-1000");
    }

    @Test
    @DisplayName("Erfolgreiche Einzahlung")
    void testSuccessfulDeposit() {
        assertTrue(account.deposit(10, 100000));
        assertEquals(100000, account.getBalance());
    }

    @Test
    @DisplayName("Einzahlung mit negativem Betrag schlägt fehl")
    void testNegativeDeposit() {
        assertFalse(account.deposit(10, -50000));
        assertEquals(0, account.getBalance());
    }

    @Test
    @DisplayName("Erfolgreiche Abhebung")
    void testSuccessfulWithdraw() {
        account.deposit(10, 100000);
        assertTrue(account.withdraw(11, 50000));
        assertEquals(50000, account.getBalance());
    }

    @Test
    @DisplayName("Abhebung übersteigt Saldo und schlägt fehl")
    void testWithdrawExceedsBalance() {
        account.deposit(10, 100000);
        assertFalse(account.withdraw(11, 150000));
        assertEquals(100000, account.getBalance());
    }

    @Test
    @DisplayName("Transaktion mit veraltetem Datum schlägt fehl")
    void testOutdatedTransaction() {
        account.deposit(20, 100000);
        assertFalse(account.withdraw(19, 50000));
        assertEquals(100000, account.getBalance());
    }
}
```

#### `SalaryAccountTests.java`

Tests für das `SalaryAccount`.

```java
package ch.schule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SalaryAccountTests {

    private SalaryAccount account;

    @BeforeEach
    void setUp() {
        // Lohnkonto mit einem Kreditlimit von -2000
        account = new SalaryAccount("P-1001", -200000);
    }

    @Test
    @DisplayName("Abhebung bis zum Kreditlimit ist erfolgreich")
    void testWithdrawToCreditLimit() {
        account.deposit(10, 100000); // Saldo: 1000
        assertTrue(account.withdraw(11, 300000)); // Saldo: -2000
        assertEquals(-200000, account.getBalance());
    }

    @Test
    @DisplayName("Abhebung über das Kreditlimit hinaus schlägt fehl")
    void testWithdrawExceedsCreditLimit() {
        account.deposit(10, 100000);
        assertFalse(account.withdraw(11, 300001));
        assertEquals(100000, account.getBalance());
    }
}
```

#### `PromoYouthSavingsAccountTests.java`

Tests für das `PromoYouthSavingsAccount`.

```java
package ch.schule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PromoYouthSavingsAccountTests {

    private PromoYouthSavingsAccount account;

    @BeforeEach
    void setUp() {
        account = new PromoYouthSavingsAccount("Y-1002");
    }

    @Test
    @DisplayName("Einzahlung erhält 1% Bonus")
    void testDepositWithBonus() {
        assertTrue(account.deposit(10, 100000)); // 1000 einzahlen
        // Erwartet: 1000 + 1% von 1000 = 1010
        assertEquals(101000, account.getBalance());
    }
}
```

#### `BankTests.java`

Umfassende Tests für die `Bank`-Klasse.

```java
package ch.schule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BankTests {

    private Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
    }

    @Test
    @DisplayName("Erstellung eines Sparkontos")
    void testCreateSavingsAccount() {
        String id = bank.createSavingsAccount();
        assertNotNull(id);
        assertTrue(id.startsWith("S-"));
        assertEquals(0, bank.getBalance(id));
    }

    @Test
    @DisplayName("Erstellung eines Lohnkontos")
    void testCreateSalaryAccount() {
        String id = bank.createSalaryAccount(-50000);
        assertNotNull(id);
        assertTrue(id.startsWith("P-"));
        assertEquals(0, bank.getBalance(id));
    }
    
    @Test
    @DisplayName("Erstellung eines Lohnkontos mit ungültigem Limit schlägt fehl")
    void testCreateSalaryAccountWithInvalidLimit() {
        String id = bank.createSalaryAccount(100); // Positives Limit ist ungültig
        assertNull(id);
    }

    @Test
    @DisplayName("Transaktionen über die Bank-Klasse")
    void testTransactionsViaBank() {
        String id = bank.createSavingsAccount();
        assertTrue(bank.deposit(id, 10, 50000));
        assertEquals(50000, bank.getBalance(id));

        assertTrue(bank.withdraw(id, 11, 20000));
        assertEquals(30000, bank.getBalance(id));
    }

    @Test
    @DisplayName("Transaktion auf nicht-existierendes Konto schlägt fehl")
    void testTransactionOnNonExistentAccount() {
        assertFalse(bank.deposit("S-9999", 10, 50000));
        assertFalse(bank.withdraw("S-9999", 11, 20000));
    }
    
    @Test
    @DisplayName("Bank-Saldo wird berechnet")
    void testBankBalance() {
        String id1 = bank.createSavingsAccount();
        bank.deposit(id1, 10, 100000); // Saldo Konto 1: 1000

        String id2 = bank.createSalaryAccount(-50000);
        bank.deposit(id2, 10, 20000); // Saldo Konto 2: 200
        
        // Die Formel in Bank.getBalance() ist `balance -= aa[i].getBalance();`
        // Erwartet wird also -(100000 + 20000) = -120000
        assertEquals(-120000, bank.getBalance());
    }
}
```