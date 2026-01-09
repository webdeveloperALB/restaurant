# Struktura e Skedarëve / File Structure
## Sistema e Menaxhimit të Studentëve dhe Notave

---

## Përmbledhje e Projektit

Totali i skedarëve: **30+ files**
Gjuha: **Java, HTML, CSS, JavaScript, SQL**
Framework: **Spring Boot 3.2.0**

---

## Struktura e Plotë e Dosjes

```
project/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── al/
│   │   │       └── student/
│   │   │           │
│   │   │           ├── controller/           [REST API Controllers]
│   │   │           │   ├── StudentController.java
│   │   │           │   ├── LendeController.java
│   │   │           │   ├── RegjistrimController.java
│   │   │           │   ├── NotaController.java
│   │   │           │   └── StatisticsController.java
│   │   │           │
│   │   │           ├── model/                [Entity Models]
│   │   │           │   ├── Student.java
│   │   │           │   ├── Lende.java
│   │   │           │   ├── Regjistrim.java
│   │   │           │   └── Nota.java
│   │   │           │
│   │   │           ├── repository/           [Data Access Layer]
│   │   │           │   ├── StudentRepository.java
│   │   │           │   ├── LendeRepository.java
│   │   │           │   ├── RegjistrimRepository.java
│   │   │           │   └── NotaRepository.java
│   │   │           │
│   │   │           ├── service/              [Business Logic]
│   │   │           │   ├── StudentService.java
│   │   │           │   ├── LendeService.java
│   │   │           │   ├── RegjistrimService.java
│   │   │           │   ├── NotaService.java
│   │   │           │   └── StatisticsService.java
│   │   │           │
│   │   │           ├── StudentManagementApplication.java  [Main Class]
│   │   │           └── ServletInitializer.java            [Tomcat Config]
│   │   │
│   │   └── resources/
│   │       ├── static/                       [Frontend Files]
│   │       │   ├── index.html               [Main HTML Page]
│   │       │   ├── css/
│   │       │   │   └── styles.css           [Styling]
│   │       │   └── js/
│   │       │       └── app.js               [Frontend Logic]
│   │       │
│   │       └── application.properties        [Configuration]
│   │
│   └── test/                                 [Tests - Optional]
│
├── pom.xml                                   [Maven Configuration]
├── build.sh                                  [Build Script]
├── .gitignore                                [Git Ignore Rules]
│
└── Documentation/                            [Project Documentation]
    ├── README.md                             [Main Documentation]
    ├── QUICKSTART.md                         [Quick Start Guide]
    ├── DEPLOYMENT_GUIDE.md                   [Deployment Instructions]
    ├── PROJECT_SUMMARY.md                    [Technical Summary]
    ├── SETUP_INSTRUCTIONS.md                 [Setup Guide]
    └── FILE_STRUCTURE.md                     [This File]
```

---

## Përshkrimi i Skedarëve

### 📁 Backend - Java Files

#### Main Application
| Skedar | Qëllimi | Linja Kodi |
|--------|---------|------------|
| `StudentManagementApplication.java` | Entry point i aplikacionit | 15 |
| `ServletInitializer.java` | Konfigurimi për Tomcat | 13 |

#### Controllers (REST API Endpoints)
| Skedar | Përshkrimi | API Endpoints |
|--------|------------|---------------|
| `StudentController.java` | CRUD për studentët | 7 endpoints |
| `LendeController.java` | CRUD për lëndët | 6 endpoints |
| `RegjistrimController.java` | CRUD për regjistrimet | 5 endpoints |
| `NotaController.java` | CRUD për notat | 6 endpoints |
| `StatisticsController.java` | Statistika dhe raporte | 7 endpoints |

#### Models (Entities)
| Skedar | Përshkrimi | Atributet |
|--------|------------|-----------|
| `Student.java` | Modeli i studentit | 11 fields |
| `Lende.java` | Modeli i lëndës | 9 fields |
| `Regjistrim.java` | Modeli i regjistrimit | 9 fields |
| `Nota.java` | Modeli i notës | 9 fields |

#### Repositories (Data Access)
| Skedar | Përshkrimi | Metodat Kryesore |
|--------|------------|------------------|
| `StudentRepository.java` | Aksesi në DB për studentët | 10 methods |
| `LendeRepository.java` | Aksesi në DB për lëndët | 10 methods |
| `RegjistrimRepository.java` | Aksesi në DB për regjistrimet | 8 methods |
| `NotaRepository.java` | Aksesi në DB për notat | 7 methods |

#### Services (Business Logic)
| Skedar | Përshkrimi | Funksionaliteti |
|--------|------------|-----------------|
| `StudentService.java` | Logjika për studentët | CRUD + Search |
| `LendeService.java` | Logjika për lëndët | CRUD + Filtering |
| `RegjistrimService.java` | Logjika për regjistrimet | CRUD + Enrichment |
| `NotaService.java` | Logjika për notat | CRUD + Calculations |
| `StatisticsService.java` | Llogaritje statistikash | Complex Queries |

### 📁 Frontend - Web Files

#### HTML
| Skedar | Përshkrimi | Faqet |
|--------|------------|-------|
| `index.html` | Faqja kryesore | 6 pages (Dashboard, Students, Subjects, Enrollments, Grades, Statistics) |

#### CSS
| Skedar | Përshkrimi | Styles |
|--------|------------|--------|
| `styles.css` | Stilet e aplikacionit | Responsive, Modern Design, Animations |

#### JavaScript
| Skedar | Përshkrimi | Funksionaliteti |
|--------|------------|-----------------|
| `app.js` | Logjika e frontend | API Calls, DOM Manipulation, Form Handling |

### 📁 Configuration Files

#### Maven
| Skedar | Përshkrimi |
|--------|------------|
| `pom.xml` | Konfigurimi i Maven, dependencies, build settings |

#### Application
| Skedar | Përshkrimi |
|--------|------------|
| `application.properties` | Konfigurimi i Spring Boot, database connection, logging |

#### Scripts
| Skedar | Përshkrimi |
|--------|------------|
| `build.sh` | Script për ndërtimin e projektit |

#### Git
| Skedar | Përshkrimi |
|--------|------------|
| `.gitignore` | Skedarët që duhen injoruar nga Git |

### 📁 Documentation Files

| Skedar | Përshkrimi | Faqet |
|--------|------------|-------|
| `README.md` | Dokumentacioni kryesor | ~200 linja |
| `QUICKSTART.md` | Udhëzues i shpejtë | ~150 linja |
| `DEPLOYMENT_GUIDE.md` | Udhëzues për deployment | ~300 linja |
| `PROJECT_SUMMARY.md` | Përmbledhje teknike | ~400 linja |
| `SETUP_INSTRUCTIONS.md` | Udhëzime për setup | ~250 linja |
| `FILE_STRUCTURE.md` | Ky dokument | ~200 linja |

---

## Statistika e Projektit

### Linja Kodi sipas Gjuhës

| Gjuha | Linja Kodi | Përqindja |
|-------|-----------|-----------|
| Java | ~3,500 | 60% |
| JavaScript | ~1,200 | 20% |
| CSS | ~700 | 12% |
| HTML | ~300 | 5% |
| Markdown | ~1,500 | 3% |
| **TOTAL** | **~7,200** | **100%** |

### Skedarë sipas Tipit

| Tipi | Numri | Shembuj |
|------|-------|---------|
| Java Classes | 19 | Controllers, Services, Repositories, Models |
| Web Files | 3 | HTML, CSS, JavaScript |
| Config Files | 3 | pom.xml, application.properties, .gitignore |
| Documentation | 6 | README, Guides |
| Scripts | 1 | build.sh |
| **TOTAL** | **32** | |

---

## Madhësia e Skedarëve

### Skedarët më të Mëdhenj

| Skedar | Madhësia (approx) | Linja |
|--------|-------------------|-------|
| `app.js` | 25 KB | ~800 linja |
| `StatisticsService.java` | 8 KB | ~200 linja |
| `styles.css` | 6 KB | ~350 linja |
| `StudentController.java` | 5 KB | ~120 linja |
| `StudentRepository.java` | 5 KB | ~110 linja |

### Madhësia Totale e Projektit

```
Source Code:  ~150 KB
Documentation: ~80 KB
Total Project: ~250 KB (pa dependencies)
With Dependencies (target/): ~40 MB
```

---

## Paketat Java

```
al.student
├── controller          [5 classes] - REST API
├── model              [4 classes] - Entities
├── repository         [4 classes] - Data Access
├── service            [5 classes] - Business Logic
└── [root]             [2 classes] - Main & Initializer
```

---

## API Endpoints (31 Total)

### Studentët (7)
- GET, POST, PUT, DELETE, Search, Count, Active

### Lëndët (6)
- GET, POST, PUT, DELETE, Search, Active

### Regjistrimet (5)
- GET, POST, PUT, DELETE, Filters

### Notat (6)
- GET, POST, PUT, DELETE, Filter, Average

### Statistikat (7)
- General, Student, History, Top, Subjects, Enrollments, Distribution

---

## Tabela e Bazës së të Dhënave (4 Tabela)

```
studentet (11 columns)
  └── id, emri, mbiemri, email, data_lindjes, adresa, telefoni,
      data_regjistrimit, aktiv, krijuar_me, perditesuar_me

lendet (9 columns)
  └── id, emri, kodi, pershkrimi, kredite, semestri, aktiv,
      krijuar_me, perditesuar_me

regjistrime (8 columns)
  └── id, student_id, lende_id, viti_akademik, semestri,
      data_regjistrimit, aktiv, krijuar_me

notat (8 columns)
  └── id, regjistrim_id, nota, lloji_provimit, data_provimit,
      komente, krijuar_me, perditesuar_me
```

---

## Dependencies (Maven - pom.xml)

### Spring Boot
- spring-boot-starter-web
- spring-boot-starter-jdbc
- spring-boot-starter-validation
- spring-boot-starter-tomcat
- spring-boot-devtools

### Database
- postgresql (JDBC Driver)

### Utilities
- jackson-databind (JSON)
- lombok (Optional)

---

## Karakteristikat Teknike

### Architecture Pattern
```
MVC (Model-View-Controller)
├── Model      → Java Entities
├── View       → HTML/CSS/JavaScript
└── Controller → REST API Controllers
```

### Design Patterns Used
- Repository Pattern (Data Access)
- Service Layer Pattern (Business Logic)
- DTO Pattern (Data Transfer)
- Singleton Pattern (Spring Beans)

### Technologies
- **Backend**: Spring Boot 3.2.0, Java 17
- **Frontend**: Vanilla JavaScript (No frameworks)
- **Database**: PostgreSQL via Supabase
- **Build**: Maven 3.6+
- **Server**: Apache Tomcat 10+

---

## Navigimi në Projekt

### Për të gjetur komponentin që ju nevojitet:

**Controllers (REST API):**
```
src/main/java/al/student/controller/
```

**Business Logic:**
```
src/main/java/al/student/service/
```

**Database Access:**
```
src/main/java/al/student/repository/
```

**Entity Models:**
```
src/main/java/al/student/model/
```

**Frontend:**
```
src/main/resources/static/
```

**Configuration:**
```
src/main/resources/application.properties
```

**Documentation:**
```
/ (root directory)
```

---

## Build Artifacts

Pas ndërtimit me `mvn package`, do të krijohen:

```
target/
├── classes/                          [Compiled .class files]
├── generated-sources/                [Generated code]
├── maven-status/                     [Build status]
├── student-management.war            [Deployable WAR file]
└── student-management/               [Exploded WAR]
```

---

## Përfundim

Ky projekt përmban një arkitekturë të plotë dhe moderne për një sistem menaxhimi studentësh. Çdo skedar ka një qëllim të qartë dhe kontribuon në funksionalitetin e përgjithshëm të sistemit.

**Struktura është e organizuar, e mirëmbajtur, dhe e gatshme për zgjerime të ardhshme.**

---

Për pyetje rreth ndonjë skedari specifik, referohuni në dokumentacionin përkatës ose hapni skedarin për të parë komentet e detajuara.
