# Playwright E2E Tests für Angular Student Management App

## Übersicht

Diese E2E (End-to-End) Tests wurden mit **Playwright** erstellt, um die Angular Student Management Applikation automatisiert im Browser zu testen.

## 🎯 Test-Abdeckung

Die Test-Suite umfasst folgende Bereiche:

### 1. **Navigation Tests** (`student-navigation.spec.ts`)

- ✅ Hauptseite mit Logo und Navigation anzeigen
- ✅ Navigation zur Studentenliste
- ✅ Navigation zum Formular "Student hinzufügen"

### 2. **Studentenliste Tests** (`student-list.spec.ts`)

- ✅ Tabelle mit Headern anzeigen (#, Name, Email)
- ✅ Studenten in der Tabelle anzeigen
- ✅ Klickbare E-Mail-Links (mailto:)
- ✅ Korrekte CSS-Klassen (Bootstrap) überprüfen

### 3. **Formular Tests** (`student-form.spec.ts`)

- ✅ Formular mit allen Feldern anzeigen
- ✅ Submit-Button initial deaktiviert
- ✅ Submit-Button aktivieren bei vollständigen Daten
- ✅ Formular mit gültigen Daten absenden
- ✅ Validierung für Pflichtfelder (Name, Email)
- ✅ Korrekte Eingabeverarbeitung
- ✅ Bootstrap-Styling überprüfen

### 4. **End-to-End Flow Tests** (`end-to-end-flow.spec.ts`)

- ✅ Kompletter User Journey: Navigation → Formular ausfüllen → Liste anzeigen
- ✅ Navigation zwischen allen Seiten
- ✅ Responsive Design Elemente überprüfen
- ✅ Alle Seiten ohne Fehler laden

## 📋 Voraussetzungen

- Node.js (v16 oder höher)
- npm oder yarn
- Angular Dev Server läuft auf `http://localhost:4200`
- Backend läuft (optional, für vollständige Tests)

## 🚀 Installation

1. **Playwright installieren:**

```bash
cd src/main/js/my-app
npm install --save-dev @playwright/test @types/node
```

2. **Browser installieren:**

```bash
npx playwright install
```

## 🧪 Tests ausführen

### Alle Tests im Headless-Modus

```bash
npm run e2e
```

### Tests mit UI-Modus (interaktiv)

```bash
npm run e2e:ui
```

### Tests mit sichtbarem Browser

```bash
npm run e2e:headed
```

### Test-Report anzeigen

```bash
npm run e2e:report
```

### Einzelne Test-Datei ausführen

```bash
npx playwright test e2e/student-form.spec.ts
```

### Tests in bestimmtem Browser

```bash
npx playwright test --project=chromium
npx playwright test --project=firefox
npx playwright test --project=webkit
```

## 📊 Test-Reports

Nach der Testausführung wird automatisch ein HTML-Report generiert:

- Speicherort: `playwright-report/index.html`
- Öffnen mit: `npm run e2e:report`

Der Report enthält:

- ✅ Erfolgreiche Tests
- ❌ Fehlgeschlagene Tests
- 📸 Screenshots bei Fehlern
- 🎬 Traces zur Fehleranalyse

## 🔧 Konfiguration

Die Playwright-Konfiguration befindet sich in `playwright.config.ts`:

```typescript
export default defineConfig({
  testDir: "./e2e",
  baseURL: "http://localhost:4200",
  use: {
    trace: "on-first-retry",
    screenshot: "only-on-failure",
  },
  webServer: {
    command: "npm run start",
    url: "http://localhost:4200",
    reuseExistingServer: true,
  },
});
```

### Wichtige Einstellungen:

- **testDir**: Verzeichnis für Test-Dateien (`./e2e`)
- **baseURL**: Basis-URL der Angular App
- **webServer**: Startet automatisch den Dev-Server
- **trace**: Erstellt Traces bei fehlgeschlagenen Tests
- **screenshot**: Erstellt Screenshots bei Fehlern

## 📁 Projekt-Struktur

```
my-app/
├── e2e/
│   ├── student-navigation.spec.ts  # Navigation Tests
│   ├── student-list.spec.ts        # Studentenliste Tests
│   ├── student-form.spec.ts        # Formular Tests
│   └── end-to-end-flow.spec.ts     # Komplette User Journeys
├── playwright.config.ts             # Playwright Konfiguration
├── playwright-report/               # Test Reports (generiert)
└── test-results/                    # Test Ergebnisse (generiert)
```

## 🎭 Playwright Vorteile

### ✨ Warum Playwright für E2E Tests?

1. **Multi-Browser Support**

   - Chromium, Firefox, WebKit (Safari)
   - Alle Tests in allen Browsern

2. **Auto-Wait Mechanismus**

   - Wartet automatisch auf Elemente
   - Keine manuellen `setTimeout()` nötig

3. **Starke Selektoren**

   - CSS, XPath, Text, Role-based
   - Robust gegen UI-Änderungen

4. **Debugging Tools**

   - UI-Modus für interaktive Tests
   - Trace Viewer für detaillierte Analyse
   - Screenshots und Videos

5. **CI/CD Integration**

   - Docker Support
   - GitHub Actions Integration
   - Parallele Testausführung

6. **TypeScript Support**
   - Vollständige Type-Safety
   - IntelliSense in IDEs

## 📝 Beispiel-Test

```typescript
import { test, expect } from "@playwright/test";

test("should add a new student", async ({ page }) => {
  // Navigation
  await page.goto("/addstudents");

  // Formular ausfüllen
  await page.fill("input#name", "Max Mustermann");
  await page.fill("input#email", "max@example.com");

  // Absenden
  await page.click('button[type="submit"]');

  // Verifizierung
  await page.goto("/students");
  await expect(page.locator("table")).toContainText("Max Mustermann");
});
```

## 🐛 Debugging

### Test im Debug-Modus ausführen

```bash
npx playwright test --debug
```

### Playwright Inspector öffnen

```bash
PWDEBUG=1 npx playwright test
```

### Traces anzeigen

```bash
npx playwright show-trace test-results/[test-name]/trace.zip
```

## 🔄 CI/CD Integration

Beispiel für GitHub Actions (`.github/workflows/e2e.yml`):

```yaml
name: E2E Tests
on: [push, pull_request]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - uses: actions/setup-node@v3
      - run: npm ci
      - run: npx playwright install --with-deps
      - run: npm run e2e
      - uses: actions/upload-artifact@v3
        if: always()
        with:
          name: playwright-report
          path: playwright-report/
```

## 📚 Best Practices

1. **Eindeutige Selektoren verwenden**

   ```typescript
   // ✅ Gut
   await page.locator("input#name").fill("Test");

   // ❌ Schlecht
   await page.locator("input").first().fill("Test");
   ```

2. **Auto-Wait nutzen**

   ```typescript
   // ✅ Playwright wartet automatisch
   await page.click("button");

   // ❌ Nicht nötig
   await page.waitForTimeout(1000);
   await page.click("button");
   ```

3. **Assertions verwenden**

   ```typescript
   // ✅ Mit Assertion
   await expect(page.locator("h1")).toBeVisible();

   // ❌ Ohne Assertion
   const isVisible = await page.locator("h1").isVisible();
   ```

4. **Page Objects für Wiederverwendbarkeit**
   ```typescript
   class StudentPage {
     constructor(private page: Page) {}

     async addStudent(name: string, email: string) {
       await this.page.fill("input#name", name);
       await this.page.fill("input#email", email);
       await this.page.click('button[type="submit"]');
     }
   }
   ```

## ✅ Ergebnis

Die Playwright E2E Tests bieten:

- ✅ **Vollautomatisierte Browser-Tests**
- ✅ **Multi-Browser Unterstützung** (Chrome, Firefox, Safari)
- ✅ **Umfassende Test-Abdeckung** (Navigation, CRUD, Validierung)
- ✅ **CI/CD Integration** möglich
- ✅ **Übersichtliche HTML-Reports**
- ✅ **Screenshots & Traces** bei Fehlern
- ✅ **TypeScript Support** mit IntelliSense

---

**Erstellt für: Übung 2 - Automatisiertes Frontend Testing**  
**Tool: Playwright**  
**Datum: Dezember 2025**
