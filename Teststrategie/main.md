# Übung 1

## 1. Abstrakte Testfälle

| Testfall-ID | Eingabebereich (Kaufpreis) | Erwarteter Rabatt | Art des Tests |
|--------------|----------------------------|-------------------|----------------|
| T1 | Kaufpreis < 15’000 | Rabatt = 0 % | Äquivalenzklasse |
| T2 | Kaufpreis = 15’000 | Rabatt = 5 % | Grenzwert |
| T3 | 15’000 < Kaufpreis ≤ 20’000 | Rabatt = 5 % | Äquivalenzklasse |
| T4 | Kaufpreis = 20’000 | Rabatt = 5 % | Grenzwert |
| T5 | 20’000 < Kaufpreis < 25’000 | Rabatt = 7 % | Äquivalenzklasse |
| T6 | Kaufpreis = 25’000 | Rabatt = 8.5 % | Grenzwert |
| T7 | Kaufpreis > 25’000 | Rabatt = 8.5 % | Äquivalenzklasse |


## 2. Konkrete Testfälle

| Testfall-ID | Kaufpreis (CHF) | Erwarteter Rabatt (%) | Erwarteter Endpreis (CHF) | Beschreibung |
|--------------|------------------|------------------|------------------|----------------|
| T1 | 14’000 | 0 % | 14’000 | Kein Rabatt unter 15’000 |
| T2 | 15’000 | 5 % | 14’250 | Grenzwert: genau 15’000 |
| T3 | 18’000 | 5 % | 17’100 | Im Bereich 15’001–20’000 |
| T4 | 20’000 | 5 % | 19’000 | Grenzwert: genau 20’000 |
| T5 | 22’000 | 7 % | 20’460 | Im Bereich 20’001–24’999 |
| T6 | 25’000 | 8.5 % | 22’875 | Grenzwert: genau 25’000 |
| T7 | 30’000 | 8.5 % | 27’450 | Oberhalb 25’000 |

# Übung 2

## Beschreibung

| ID | Beschreibung | Erwartetes Resultat | Effektives Resultat | Status | Mögliche Ursache |
|----|---------------|--------------------|---------------------|--------|------------------|
| 1 | Benutzer kann ein Fahrzeug für ein bestimmtes Datum und Ort suchen | Nach Eingabe von Ort, Datum und Uhrzeit werden verfügbare Fahrzeuge angezeigt | Fahrzeuge werden korrekt angezeigt | OK | – |
| 2 | Benutzer kann ein Fahrzeug online buchen | Nach Auswahl eines Fahrzeugs und Eingabe persönlicher Daten wird eine Buchungsbestätigung angezeigt | Fehlermeldung: “Buchung konnte nicht abgeschlossen werden” | Fehler | Verbindung zur Datenbank oder API-Problem |
| 3 | Bezahlung mit Kreditkarte funktioniert | Nach Eingabe gültiger Kreditkartendaten wird die Zahlung akzeptiert und Buchung abgeschlossen | Zahlung wird akzeptiert | OK | – |
| 4 | Login für registrierte Benutzer funktioniert | Nach Eingabe korrekter Zugangsdaten wird der Benutzer zum Dashboard weitergeleitet | Benutzer landet auf der Startseite ohne Fehlermeldung | Fehler | Session-Verwaltung oder Cookie-Problem |
| 5 | Stornierung einer bestehenden Buchung ist möglich | Nach Auswahl einer Buchung und Klick auf “Stornieren” wird eine Bestätigung angezeigt und Buchung entfernt | Buchung bleibt bestehen, keine Bestätigung | Fehler | Backend-Update-Fehler oder API-Timeout |

# Übung 3

## Benutzersicht

| ID | Beschreibung | Erwartetes Resultat | Effektives Resultat | Status | Mögliche Ursache |
|----|-------------|-------------------|-------------------|--------|----------------|
| BB1 | Konto erstellen | Konto wird erfolgreich angelegt | – | Offen | – |
| BB2 | Geld einzahlen | Kontostand erhöht sich korrekt | – | Offen | – |
| BB3 | Geld abheben (ausreichend Guthaben) | Kontostand reduziert sich korrekt | – | Offen | – |
| BB4 | Geld abheben (zu wenig Guthaben) | Fehlermeldung „Nicht genügend Guthaben“ | – | Offen | – |
| BB5 | Überweisung an anderes Konto | Betrag wird korrekt übertragen | – | Offen | – |

## Entwicklersicht

| ID | Methode | Erwartetes Verhalten |
|----|--------|-------------------|
| WB1 | deposit(amount) | Kontostand erhöht sich korrekt |
| WB2 | withdraw(amount) | Betrag wird korrekt abgezogen, kein Negativsaldo |
| WB3 | transfer(from, to, amount) | Betrag wird zwischen Konten korrekt übertragen |
| WB4 | getBalance() | Rückgabe des korrekten Kontostands |

## Codeverbesserungen

| Bereich | Verbesserung | Begründung |
|---------|-------------|------------|
| Architektur | MVC- oder Layered-Architektur einführen | Trennt UI, Logik und Daten und erhöht Wartbarkeit |
| Fehlermanagement | Einheitliches Exception-Handling | Saubere Fehlerausgaben statt unstrukturierter Konsolenmeldungen |
| Tests | Unit-Tests mit JUnit/Mockito | Sichert die Funktionalität und erhöht Zuverlässigkeit |
| Logging | SLF4J oder Log4j nutzen | Bessere Nachvollziehbarkeit von Programmabläufen |
| Validierung | Eingaben prüfen (z. B. keine negativen Beträge) | Verhindert fehlerhafte oder unsichere Daten |