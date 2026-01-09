# Si të Ekzekutosh Aplikacionin / How to Run

## Hapa të Shpejtë / Quick Steps

### 1. Hap projektin në Visual Studio Code
```bash
code /path/to/project
```

### 2. Instalo extensions kur të kërkohet
Kliko "Install All" kur VS Code të sugjerojë.

### 3. Konfiguro bazën e të dhënave
Hap `src/main/resources/application.properties` dhe vendos fjalëkalimin e Supabase.

### 4. Shtyp F5
Aq! Aplikacioni do të fillojë.

### 5. Hap shfletuesin
```
http://localhost:8080
```

---

## 4 Mënyrat për të Ekzekutuar

### ⚡ Mënyra 1: F5 (Më e Shpejta)
Thjesht shtyp `F5` në tastierë!

### 📊 Mënyra 2: Spring Boot Dashboard
1. Shiko në sidebar të majtë
2. Gjej "Spring Boot Dashboard"
3. Kliko ▶️ (Run) ose 🐛 (Debug)

### 📋 Mënyra 3: Command Palette
1. `Ctrl+Shift+P` (Cmd+Shift+P në Mac)
2. Shkruaj "Spring Boot: Run"
3. Zgjidh "Spring Boot: Run"

### 💻 Mënyra 4: Terminal
```bash
mvn spring-boot:run
```

---

## Ndalo Aplikacionin

| Mënyra | Si |
|--------|-----|
| **Keyboard** | `Shift+F5` |
| **Button** | Kliko ⬛ (Stop) në debug toolbar |
| **Terminal** | `Ctrl+C` |
| **Dashboard** | Kliko ⬛ pranë app-it në Spring Boot Dashboard |

---

## Debugging

1. Vendos breakpoints (kliko pranë numrit të linjës)
2. Shtyp `F5`
3. Aplikacioni do të ndalet në breakpoint
4. Përdor Debug Console për të testuar expressions

---

## Shortcuts

| Shortcut | Action |
|----------|--------|
| `F5` | Run/Debug |
| `Shift+F5` | Stop |
| `Ctrl+Shift+P` | Command Palette |
| `Ctrl+` ` | Terminal |
| `Ctrl+Shift+B` | Build |

---

## Probleme?

### Nuk fillonApplicationi
1. Kontrollo që Java dhe Maven janë instaluar
2. Instalo extensions: "Extension Pack for Java" dhe "Spring Boot Extension Pack"
3. Rifillo VS Code

### Porta 8080 është e zënë
Ndryshoni në `application.properties`:
```properties
server.port=8090
```

### Gabim lidhje me bazën
Kontrollo fjalëkalimin në `application.properties`.

---

**Për më shumë detaje, shiko README.md ose QUICKSTART.md**
