# CI/CD Pipeline Dokumentation

## Übersicht

Die GitLab CI/CD Pipeline ist in `.gitlab-ci.yml` definiert und führt automatisch folgende Schritte aus:

1. **Build**: Kompiliert die Spring Boot Anwendung
2. **Test**: Führt alle Unit Tests aus und generiert Test-Reports
3. **Report**: Erstellt Code Coverage Reports mit JaCoCo
4. **Pages**: Veröffentlicht Coverage-Reports auf GitLab Pages (optional)

---

## Pipeline-Stages

### Stage 1: Build
```yaml
build:
  stage: build
  script:
    - mvn clean compile -q
```

**Was passiert:**
- Clean (alte Builds löschen)
- Kompilierung des Codes
- Artifacts für nächste Stage speichern

**Trigger:** Jeder Push auf `main` oder Merge Requests

---

### Stage 2: Test
```yaml
test:
  stage: test
  script:
    - mvn test -q
```

**Was passiert:**
- Alle Unit Tests ausführen (JUnit 5)
- Surefire generiert Test-Reports (XML)
- JaCoCo instrumentiert Code und sammelt Coverage-Daten
- Test-Reports als Artifacts speichern

**Artifacts:**
- `target/surefire-reports/TEST-*.xml`: JUnit XML Reports
- `target/site/jacoco/`: HTML Coverage Reports
- `target/site/jacoco/index.html`: Coverage-Summary

---

### Stage 3: Report
```yaml
report:
  stage: report
  script:
    - mvn jacoco:report -q
```

**Was passiert:**
- JaCoCo Report explizit generieren (falls nicht in Test-Phase)
- Reports in Artifacts speichern für 90 Tage

---

### Stage 4: Pages (optional)
```yaml
pages:
  stage: report
```

**Was passiert:**
- Coverage Reports auf GitLab Pages veröffentlichen
- Abrufbar unter: `https://<your-username>.gitlab.io/<project-name>/`

---

## Unit Tests in der Pipeline

### Controller Tests (MockMvc)

Datei: `src/test/java/ch/tbz/m450/testing/tools/controller/StudentControllerTests.java`

**Getestete Funktionen:**
- `GET /students` → Alle Studenten abrufen
- `POST /students` → Neuen Studenten hinzufügen
- Leere Liste handhaben
- Ungültige Daten validieren

**Beispiel:**
```java
@Test
public void testGetStudents() throws Exception {
    when(studentRepository.findAll()).thenReturn(Arrays.asList(student1, student2));
    
    mockMvc.perform(get("/students"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)));
}
```

### Entity Tests (mit SoftAssertions)

Datei: `src/test/java/ch/tbz/m450/testing/tools/repository/entities/StudentEntityTests.java`

**Vorteil von SoftAssertions:**
- Alle Assertions werden ausgeführt (nicht nur bis zum ersten Fehler)
- Alle Fehler werden gesammelt und am Ende gemeldet

**Beispiel:**
```java
@Test
public void testStudentWithSoftAssertions() {
    SoftAssertions softly = new SoftAssertions();
    
    softly.assertThat(student.getName()).isEqualTo("Max Mustermann");
    softly.assertThat(student.getEmail()).isEqualTo("max@example.com");
    
    softly.assertAll(); // Alle Assertions abfragen, dann Fehler melden
}
```

---

## Code Coverage Reports

### JaCoCo Metriken

JaCoCo misst folgende Coverage-Arten:

1. **Line Coverage**: Wieviel Prozent der Zeilen wurden ausgeführt?
2. **Branch Coverage**: Wieviel Prozent der Verzweigungen wurden getestet?
3. **Method Coverage**: Wieviel Methoden wurden aufgerufen?

### Report öffnen

**Lokal nach `mvn test`:**
```
target/site/jacoco/index.html
```

**In GitLab Pipeline:**
- Artifacts unter `Pipelines > Build > Test` herunterladen
- Oder unter `Environments > Deployments` (wenn Pages aktiviert)

---

## Maven Plugins

### Surefire Plugin
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>3.0.0-M9</version>
</plugin>
```

**Funktion:**
- Führt Unit Tests aus
- Generiert Test-Reports in `target/surefire-reports/`
- Unterstützt XML, HTML und Text-Format

### JaCoCo Plugin
```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.10</version>
</plugin>
```

**Funktion:**
- Instrumentiert Java-Bytecode während Test-Execution
- Sammelt Execution-Daten
- Generiert HTML Reports in `target/site/jacoco/`

---

## Lokales Ausführen

### Tests lokal mit Report-Generierung

```bash
# Tests ausführen + Coverage Report
mvn clean test jacoco:report

# Reports öffnen
start target/site/jacoco/index.html
```

### Nur Tests, ohne Report

```bash
mvn test
```

### Surefire Test-Report

```bash
mvn surefire-report:report
start target/site/surefire-report.html
```

---

## Pipeline-Workflow in GitLab

1. **Entwickler pusht Code** auf `main` oder erstellt Merge Request
2. **GitLab erkennt `.gitlab-ci.yml`** und startet Pipeline
3. **Build-Stage**: `mvn clean compile`
   - Falls erfolgreich → Artifacts speichern
   - Falls Fehler → Pipeline stopp, Entwickler benachrichtigt
4. **Test-Stage**: `mvn test`
   - Alle Unit Tests ausführen
   - Test-Reports und Coverage-Daten sammeln
   - Artifacts speichern
5. **Report-Stage**: `mvn jacoco:report`
   - Coverage Report generieren
   - 90 Tage speichern
6. **Pages-Stage** (optional): Coverage Reports auf GitLab Pages
   - Public zugänglich unter `https://<user>.gitlab.io/<project>`

---

## Coverage-Ziele

Empfohlene Code Coverage Metriken:

| Metrik | Ziel |
|--------|------|
| Line Coverage | ≥ 70% |
| Branch Coverage | ≥ 60% |
| Method Coverage | ≥ 80% |

**Für dieses Projekt (Spring Boot Angular):**
- Fokus auf Controller Tests (Integration)
- Entity/Repository Tests (Geschäftslogik)
- Service Layer Tests (mit Mockito)

---

## Troubleshooting

### Pipeline schlägt fehl mit `mvn: command not found`
- → Maven nicht im Docker Image
- → `.gitlab-ci.yml` Image-Zeile prüfen: `image: maven:3.9.0-eclipse-temurin-17`

### Test-Reports nicht sichtbar in GitLab
- → Artifacts-Pfade prüfen in `.gitlab-ci.yml`
- → Sicherstellen, dass `junit: target/surefire-reports/TEST-*.xml` korrekt ist

### JaCoCo Report nicht generiert
- → Sicherstellen, dass `mvn test` erfolgreich war
- → JaCoCo Plugin in `pom.xml` hinzufügen (siehe oben)

---

## Nächste Schritte

1. **GitHub Actions Alternative**: `.github/workflows/maven.yml` für GitHub
2. **Deployment nach Tests**: CD-Stage hinzufügen (Docker Push, K8s Deployment)
3. **Notify**: Slack/Email Notifications bei fehlgeschlagenen Tests
4. **Performance**: Test-Caching optimieren, Parallelisierung
