# Deployment-Umgebungen und Automatisierung

## Aufgabe 1

In der Softwareentwicklung werden üblicherweise vier Deployment-Umgebungen eingesetzt:

- Development Environment  
- Testing Environment  
- Staging Environment  
- Production Environment  

Im Folgenden werden die Softwarelösungen **Docker Compose**, **Kubernetes**, **Vagrant** und **Terraform** kurz eingeordnet und den passenden Umgebungen zugewiesen.

### Development Environment
**Geeignete Softwarelösungen:** Docker Compose, Vagrant

**Begründung:**
- **Docker Compose** eignet sich sehr gut für lokale Entwicklungsumgebungen. Services wie Webserver oder Datenbanken lassen sich schnell und reproduzierbar starten.
- **Vagrant** ermöglicht das Bereitstellen kompletter virtueller Maschinen. Dies ist besonders sinnvoll, wenn ein vollständiges Betriebssystem oder Legacy-Setups benötigt werden.

### Testing Environment
**Geeignete Softwarelösungen:** Docker Compose, Kubernetes

**Begründung:**
- **Docker Compose** erlaubt das schnelle Aufsetzen reproduzierbarer Testumgebungen und eignet sich gut für automatisierte Tests.
- **Kubernetes** ist sinnvoll, wenn die spätere Produktionsumgebung ebenfalls auf Kubernetes basiert und realitätsnahe Tests durchgeführt werden sollen.

### Staging Environment
**Geeignete Softwarelösungen:** Kubernetes, Terraform

**Begründung:**
- Die Staging-Umgebung sollte der Produktionsumgebung möglichst stark entsprechen.
- **Kubernetes** bietet Orchestrierung, Skalierung und Service-Management.
- **Terraform** ermöglicht das deklarative und versionierte Bereitstellen der Infrastruktur.

### Production Environment
**Geeignete Softwarelösungen:** Kubernetes, Terraform

**Begründung:**
- Hohe Anforderungen an Stabilität, Sicherheit und Skalierbarkeit.
- **Kubernetes** ist Industriestandard für Container-Orchestrierung mit Self-Healing und Rolling Updates.
- **Terraform** erlaubt eine vollständig automatisierte und dokumentierte Infrastruktur.

### Übersichtstabelle

| Umgebung     | Empfohlene Softwarelösungen |
|--------------|-----------------------------|
| Development  | Docker Compose, Vagrant     |
| Testing      | Docker Compose, Kubernetes  |
| Staging      | Kubernetes, Terraform       |
| Production   | Kubernetes, Terraform       |

## Aufgabe 2

### Gewählte Softwarelösung
Docker Compose

### Gewählte Umgebung
Development Environment

### Ziel
Automatisiertes Setup einer lokalen Entwicklungsumgebung.

### Deployte Software
- Webapplikation (z. B. PHP- oder Node.js-Anwendung)
- Datenbank (z. B. MySQL oder PostgreSQL)

### Umsetzung innerhalb einer Lektion

**Erfolge:**
- Erstellung einer `docker-compose.yml`
- Start aller Services mit `docker compose up`
- Schnelles und reproduzierbares Setup
- Sofort lauffähige Umgebung

**Herausforderungen:**
- Verständnis von Volumes, Netzwerken und Port-Mappings
- Debugging bei Container-Startproblemen
- Abhängigkeiten zwischen Services (Startreihenfolge)

**Sinnvoller Einsatz:**
- Kleine bis mittlere Projekte
- Teamarbeit mit identischen Entwicklungsumgebungen
- Schnelles Testen und Prototyping

**Grenzen:**
- Nicht für hochskalierende Produktionsumgebungen geeignet
- Keine erweiterten Orchestrierungsfunktionen

## Aufgabe 3

### Weiterführende Ideen
- Separate Compose-Dateien für Development und Testing
- Integration in eine CI/CD-Pipeline
- Umstieg auf Kubernetes für Staging und Production
- Nutzung von Terraform für Cloud-Infrastruktur

### Beruflicher Mehrwert
- Vertieftes Verständnis von DevOps-Prinzipien
- Zeitersparnis durch Automatisierung
- Relevante und gefragte Kompetenzen im Berufsalltag