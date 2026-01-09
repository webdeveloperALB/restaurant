# Udhëzues i Shpejtë / Quick Start Guide

## Sistema e Menaxhimit të Studentëve dhe Notave

Filloni të punoni me aplikacionin në 5 minuta!

---

## Hapi 1: Instalo Parakushtet

### Visual Studio Code
Shkarkoni dhe instaloni: https://code.visualstudio.com/

### Java 17+
```bash
# Verifikoni nëse e keni
java -version

# Nëse jo, instaloni:
# Windows/Mac: https://adoptium.net/
# Linux:
sudo apt install openjdk-17-jdk
```

### Maven
```bash
# Verifikoni nëse e keni
mvn -version

# Nëse jo, instaloni:
# Linux:
sudo apt install maven
# Windows/Mac: https://maven.apache.org/download.cgi
```

---

## Hapi 2: Hap Projektin në VS Code

```bash
code /path/to/project
```

Ose:
1. Hap Visual Studio Code
2. File → Open Folder
3. Zgjidh dosjen e projektit

---

## Hapi 3: Instalo Extensions

Kur hapet projekti, VS Code do të sugjerojë:
```
"This workspace has extension recommendations"
```

Kliko **"Install All"**

Ose instalo manualisht:
1. `Ctrl+Shift+X` (Extensions)
2. Kërko dhe instalo:
   - **Extension Pack for Java**
   - **Spring Boot Extension Pack**

---

## Hapi 4: Konfiguro Bazën e Dhënave

1. Hap: `src/main/resources/application.properties`

2. Gjej fjalëkalimin e Supabase:
   - Shko në: https://app.supabase.com
   - Settings → Database
   - Kopjo fjalëkalimin

3. Zëvendëso `[YOUR_PASSWORD]`:
```properties
spring.datasource.password=FJALKALIMI_JUAJ_KETU
```

---

## Hapi 5: Ekzekuto Aplikacionin

### Mënyra 1: Shtyp F5 (Më e Shpejta!)
Thjesht shtyp `F5` në tastierë dhe aplikacioni do të fillojë!

### Mënyra 2: Përdor Spring Boot Dashboard
1. Shiko në sidebar të majtë
2. Gjej "Spring Boot Dashboard"
3. Kliko ▶️ pranë "student-management-system"

### Mënyra 3: Përdor Terminal
```bash
mvn spring-boot:run
```

---

## Hapi 6: Hap në Shfletues

Shkoni në:
```
http://localhost:8080
```

Duhet të shihni Panelin Kryesor! 🎉

---

## 4 Mënyrat për të Ekzekutuar

| Mënyra | Si të Ekzekutosh | Kur ta Përdorësh |
|--------|------------------|------------------|
| **F5** | Shtyp `F5` | Quick testing |
| **Spring Boot Dashboard** | Kliko ▶️ në sidebar | Visual control |
| **Command Palette** | `Ctrl+Shift+P` → "Spring Boot: Run" | Multiple projects |
| **Terminal** | `mvn spring-boot:run` | Command line fans |

---

## Funksionalitetet Kryesore

### 1. Menaxhimi i Studentëve
- Shto, modifiko, fshij studentë
- Kërko sipas emrit, mbiemrit ose email
- Shiko historinë akademike

### 2. Menaxhimi i Lëndëve
- Menaxho lëndët me kode dhe kredite
- Filtro sipas semestrit
- Kërkimi i avancuar

### 3. Regjistrimet
- Regjistro studentët në lëndë
- Menaxho vitet akademike
- Shiko statistikat

### 4. Notat
- Shto dhe menaxho notat
- Llogarit mesataret automatikisht
- Lloje të ndryshme provimesh

### 5. Statistikat
- Top studentët
- Shpërndarja e notave
- Statistika për lëndë

---

## Shortcuts në VS Code

| Shortcut | Çfarë Bën |
|----------|-----------|
| `F5` | Ekzekuto/Debug |
| `Shift+F5` | Ndalo aplikacionin |
| `Ctrl+Shift+P` | Command Palette |
| `Ctrl+` ` | Hap Terminal |
| `Ctrl+Shift+B` | Build projektin |
| `Alt+Shift+F` | Format code |
| `F12` | Go to definition |
| `Ctrl+.` | Quick fix |

---

## Test API me curl

```bash
# Merr statistikat
curl http://localhost:8080/api/statistika/pergjithshme

# Merr studentët
curl http://localhost:8080/api/studentet

# Shto student
curl -X POST http://localhost:8080/api/studentet \
  -H "Content-Type: application/json" \
  -d '{
    "emri": "Gjon",
    "mbiemri": "Buzuku",
    "email": "gjon@example.com",
    "dataLindjes": "2000-01-01",
    "aktiv": true
  }'
```

---

## Zgjidhja e Shpejtë e Problemeve

### ❌ "Java runtime could not be located"
```bash
# Instalo Java 17
# Windows/Mac: https://adoptium.net/
# Linux:
sudo apt install openjdk-17-jdk
```

### ❌ Nuk ekzekutohet aplikacioni
1. Kontrollo që extensions janë instaluar
2. `Ctrl+Shift+P` → "Java: Clean Java Language Server Workspace"
3. Rifillo VS Code

### ❌ Porta 8080 është e zënë
Ndryshoni në `application.properties`:
```properties
server.port=8090
```

### ❌ Gabim lidhje me bazën e të dhënave
- Kontrollo fjalëkalimin në `application.properties`
- Verifikoni lidhjen me internet

---

## Hot Reload

Spring Boot DevTools është i aktivizuar automatikisht!

**Si funksionon:**
1. Bëj ndryshime në code
2. Ruaj skedarin (`Ctrl+S`)
3. Aplikacioni do të reload automatikisht
4. Rifresko shfletuesin

---

## Debugging

1. Vendos **breakpoint** duke klikuar pranë numrit të linjës
2. Shtyp `F5` për debug mode
3. Aplikacioni do të ndalet në breakpoint
4. Përdor **Debug Console** për të testuar expressions
5. Shiko **Variables** panel për të parë vlerat

---

## Maven Commands

```bash
# Clean projektin
mvn clean

# Compile
mvn compile

# Build (krijo JAR)
mvn package

# Run aplikacionin
mvn spring-boot:run

# Clean dhe build
mvn clean package
```

---

## Struktura e Kodit

```
src/main/java/al/student/
├── controller/    → REST API endpoints
├── service/       → Business logic
├── repository/    → Database access
├── model/         → Entity classes
└── StudentManagementApplication.java → Main class
```

---

## Next Steps

Pasi të keni ekzekutuar me sukses:

1. ✅ Eksplorojeni kodin në VS Code
2. ✅ Testoni API endpoints
3. ✅ Shtoni të dhëna për test
4. ✅ Shikoni logët në Output panel
5. ✅ Bëni ndryshime dhe shikoni hot reload
6. ✅ Praktikoni debugging

---

## Resources

- **README.md** - Dokumentacion i plotë
- **VS Code Java Docs** - https://code.visualstudio.com/docs/java
- **Spring Boot Docs** - https://spring.io/projects/spring-boot
- **Supabase Docs** - https://supabase.com/docs

---

## Ndalo Aplikacionin

| Mënyra | Si të Ndalosh |
|--------|---------------|
| **VS Code** | `Shift+F5` ose kliko ⬛ (Stop) |
| **Terminal** | `Ctrl+C` |
| **Spring Boot Dashboard** | Kliko ⬛ pranë aplikacionit |

---

## Tips & Tricks

### Auto Import
Kur shkruani kod, VS Code do të sugjerojë imports automatikisht. Shtyp `Enter` për të pranuar.

### Code Snippets
- Shkruaj `psvm` dhe shtyp `Tab` → `public static void main`
- Shkruaj `sout` dhe shtyp `Tab` → `System.out.println()`

### Organize Imports
`Shift+Alt+O` → Pastron dhe organizon imports automatikisht

### Refactor
`F2` → Riemërto variabla/klasa kudo që përdoren

---

**Aplikacioni është gati! Thjesht shtyp F5 dhe fillo të kodosh!** 🚀

Për pyetje ose probleme, shiko README.md ose dokumentacionin e VS Code.
