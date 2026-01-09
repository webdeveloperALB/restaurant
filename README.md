# Sistema e Menaxhimit Universitar (UMIS)
## University Management Information System

Një sistem komplet dhe profesional për menaxhimin e universiteteve, i shkruar në Java Spring Boot me ndërfaqe moderne në gjuhën shqipe. Sistemi përfshin autentifikim të plotë, CRM për pedagogë, portal për studentë, dhe shumë karakteristika të avancuara.

---

## 🎯 Karakteristikat Kryesore / Key Features

### 🔐 **Autentifikimi dhe Siguria**
- ✅ Regjistrim dhe Kyçje (Login/Register) e sigurt
- ✅ Autentifikim me JWT (JSON Web Tokens)
- ✅ Menaxhim rolesh (Admin, Pedagog, Student)
- ✅ Password hashing me BCrypt
- ✅ Session management
- ✅ Row Level Security në databazë

### 👨‍🏫 **CRM për Pedagogë**
- ✅ Menaxhimi i kurseve dhe lëndëve
- ✅ Regjistrimi i pranisë së studentëve
- ✅ Krijimi dhe vlerësimi i detyrave
- ✅ Menaxhimi i provimeve dhe notave
- ✅ Komunikim me studentë (mesazhe)
- ✅ Publikimi i njoftimeve
- ✅ Dashboard me statistika

### 🎓 **Portal për Studentë**
- ✅ Shikimi i kurseve të regjistruara
- ✅ Shikimi i detyrave dhe afateve
- ✅ Dorëzimi i detyrave online
- ✅ Shikimi i notave dhe mesatares
- ✅ Evidenca e pranisë
- ✅ Mesazhe me pedagogët
- ✅ Njoftimet dhe kalendari akademik

### 📊 **Sistemi Akademik**
- ✅ Menaxhimi i fakulteteve dhe departamenteve
- ✅ Organizimi i kurseve dhe orareve
- ✅ Materialet e kursit (syllabus, dokumente, video)
- ✅ Forum diskutimi për kurse
- ✅ Transkirptet akademike
- ✅ Certifikatat dhe diplomat
- ✅ Bursat dhe pagesat

### 💼 **Karakteristika Administrative**
- ✅ Menaxhimi i dokumenteve
- ✅ Sistem kërkesash (tickets)
- ✅ Kalendari akademik
- ✅ Vlerësimet e pedagogëve
- ✅ Activity logs dhe audit trail
- ✅ Raportim dhe analiza

---

## 🏗️ Arkitektura e Sistemit

### **Databaza (PostgreSQL/Supabase)**
Sistemi përdor **30+ tabela** të organizuara në 8 module kryesore:

1. **Authentication & Users** - Përdoruesit, rolet, sesionet
2. **Organizational Structure** - Fakultetet, departamentet, pedagogët
3. **Academic Management** - Kurset, materialet, oraret
4. **Student Engagement** - Prania, detyrat, provimet
5. **Communication** - Mesazhet, njoftimet, diskutimet
6. **Documents** - Dokumentet, transkirptet, certifikatat
7. **Financial** - Pagesat, bursat
8. **Support Systems** - Kalendari, kërkesat, vlerësimet

### **Backend (Java Spring Boot)**
- **Controllers**: REST API endpoints për të gjitha modulet
- **Services**: Business logic dhe operacionet
- **Repositories**: Data access layer me JDBC
- **Security**: JWT authentication dhe Spring Security
- **DTOs**: Data transfer objects për API

### **Frontend (HTML/CSS/JavaScript)**
- **Ndërfaqe moderne**: Professional UI me design universiteti
- **Responsive**: Funksionon në desktop dhe mobile
- **Autentifikim**: Login/Register forms
- **Dashboard**: Panel për çdo rol (Admin, Pedagog, Student)
- **Navigim intuitiv**: Sidebar navigation dhe page routing

---

## 🛠️ Teknologjitë e Përdorura

### Backend
- **Java 17** - Programming language
- **Spring Boot 3.2.0** - Framework
- **Spring Security** - Authentication & authorization
- **JWT (JJWT 0.11.5)** - Token-based auth
- **Spring JDBC** - Database access
- **PostgreSQL** - Database
- **Supabase** - Database hosting
- **BCrypt** - Password hashing
- **Maven** - Build tool

### Frontend
- **HTML5** - Markup
- **CSS3** - Styling with CSS Variables
- **JavaScript (Vanilla)** - Client-side logic
- **Fetch API** - HTTP requests
- **LocalStorage** - Token storage

### Development Tools
- **Visual Studio Code** - IDE
- **Git** - Version control
- **Embedded Tomcat** - Web server

---

## 📋 Parakushtet / Prerequisites

1. **Visual Studio Code**
   - Shkarkoni: https://code.visualstudio.com/

2. **Java Development Kit (JDK) 17+**
   - Windows/Mac: https://adoptium.net/
   - Linux: `sudo apt install openjdk-17-jdk`

3. **Apache Maven 3.6+**
   - Windows/Mac: https://maven.apache.org/download.cgi
   - Linux: `sudo apt install maven`

4. **VS Code Extensions** (të rekomanduara):
   - Extension Pack for Java
   - Spring Boot Extension Pack
   - Language Support for Java

---

## 🚀 Si të Filloni / Getting Started

### 1. Klononi projektin
```bash
git clone <repository-url>
cd student-management-system
```

### 2. Konfiguroni databazën
Skedari `.env` përmban kredencialet e Supabase:
```properties
SUPABASE_URL=your-supabase-url
SUPABASE_ANON_KEY=your-anon-key
DB_URL=your-database-connection-string
DB_USER=your-db-user
DB_PASSWORD=your-db-password
```

### 3. Hapni projektin në VS Code
```bash
code .
```

### 4. Ekzekutoni aplikacionin
**Opsioni 1 - Nga VS Code:**
- Hapni `src/main/java/al/student/StudentManagementApplication.java`
- Klikoni "Run" ose shtypni `F5`

**Opsioni 2 - Nga Terminali:**
```bash
mvn spring-boot:run
```

### 5. Hapni në browser
```
http://localhost:8080
```

---

## 👤 Përdorimi i Sistemit / System Usage

### **Hapi 1: Regjistrohu**
1. Hapni aplikacionin në browser
2. Klikoni "Regjistrohu këtu"
3. Plotësoni formularin me të dhënat tuaja
4. Zgjidhni rolin: **Student** ose **Pedagog**
5. Klikoni "Regjistrohu"

### **Hapi 2: Kyçu në sistem**
1. Vendosni email dhe fjalëkalimin
2. Klikoni "Kyçu"
3. Do të ridrejtoheni automatikisht në dashboard

### **Hapi 3: Eksploro sistemin**
- **Dashboard**: Shiko statistikat dhe aktivitetin
- **Navigimi**: Përdor sidebar-in për të naviguar
- **Profili**: Shiko dhe modifiko të dhënat tuaja
- **Dil**: Kliko butonin "Dil" për të dalë nga sistemi

---

## 📁 Struktura e Projektit

```
student-management-system/
├── src/
│   └── main/
│       ├── java/al/student/
│       │   ├── controller/          # REST Controllers
│       │   │   ├── AuthController.java
│       │   │   ├── StudentController.java
│       │   │   ├── LendeController.java
│       │   │   └── ...
│       │   ├── service/             # Business Logic
│       │   │   ├── AuthService.java
│       │   │   ├── StudentService.java
│       │   │   └── ...
│       │   ├── repository/          # Data Access
│       │   │   ├── PerdoruesiRepository.java
│       │   │   ├── StudentRepository.java
│       │   │   └── ...
│       │   ├── model/              # Domain Models
│       │   │   ├── Perdoruesi.java
│       │   │   ├── Student.java
│       │   │   └── ...
│       │   ├── dto/                # Data Transfer Objects
│       │   │   ├── LoginRequest.java
│       │   │   ├── RegisterRequest.java
│       │   │   └── AuthResponse.java
│       │   ├── security/           # Security Configuration
│       │   │   ├── SecurityConfig.java
│       │   │   ├── JwtUtil.java
│       │   │   └── JwtAuthenticationFilter.java
│       │   └── StudentManagementApplication.java
│       └── resources/
│           ├── static/
│           │   ├── index.html      # Main HTML
│           │   ├── css/
│           │   │   └── styles.css  # Styling
│           │   └── js/
│           │       └── app.js      # Frontend Logic
│           └── application.properties
├── supabase/
│   └── migrations/                 # Database Migrations
│       └── *.sql
├── pom.xml                         # Maven Configuration
└── README.md
```

---

## 🔌 API Endpoints

### **Authentication**
```
POST   /api/auth/register    - Regjistrim i përdoruesit të ri
POST   /api/auth/login       - Kyçje në sistem
POST   /api/auth/logout      - Dalje nga sistemi
GET    /api/auth/validate    - Validim i token
```

### **Students**
```
GET    /api/studentet              - Lista e studentëve
GET    /api/studentet/{id}         - Detajet e një studenti
POST   /api/studentet              - Krijo student të ri
PUT    /api/studentet/{id}         - Përditëso student
DELETE /api/studentet/{id}         - Fshij student
GET    /api/studentet/kerko        - Kërko studentë
```

### **Courses (Kurset)**
```
GET    /api/kurset                 - Lista e kurseve
GET    /api/kurset/{id}            - Detajet e një kursi
POST   /api/kurset                 - Krijo kurs të ri
PUT    /api/kurset/{id}            - Përditëso kurs
DELETE /api/kurset/{id}            - Fshij kurs
```

---

## 🔒 Siguria

- **Password Hashing**: Të gjitha fjalëkalimet ruhen me BCrypt
- **JWT Tokens**: Token expires në 24 orë
- **Row Level Security**: Të gjitha tabelat kanë RLS të aktivizuar
- **Role-Based Access**: Qasje e kufizuar sipas rolit
- **HTTPS**: Rekomandohet për production
- **CORS**: Konfiguruar për frontend-backend communication

---

## 🗄️ Databaza

### **Schema Overview**
Sistemi përdor një arkitekturë të avancuar me:
- 30+ tabela
- Foreign key constraints
- Indexes për performancë
- Triggers për timestamp updates
- Row Level Security policies
- Default data për roles dhe positions

### **Migration Files**
Të gjitha migracionet janë në `supabase/migrations/`:
- `20260109163406_create_student_management_system.sql`
- `20260109170729_expand_university_management_system.sql`

---

## 📱 Responsive Design

Sistemi është i optimizuar për:
- **Desktop** (1920px+)
- **Laptop** (1366px - 1920px)
- **Tablet** (768px - 1366px)
- **Mobile** (320px - 768px)

---

## 🎨 Design Principles

- **Professional**: University-grade appearance
- **Clean**: Minimalist and organized
- **Intuitive**: Easy navigation
- **Accessible**: WCAG compliant
- **Consistent**: Unified color scheme and typography
- **Modern**: Contemporary UI patterns

---

## 🛡️ Best Practices

1. **Security First**: Gjithmonë validoni input
2. **Error Handling**: Të gjitha operacionet kanë error handling
3. **Logging**: Activity logs për audit trail
4. **Code Organization**: Separation of concerns
5. **Documentation**: Komente dhe dokumentim i plotë
6. **Testing**: Testoni para deployment

---

## 📖 Dokumentacione të Tjera

- **QUICKSTART.md** - Guide i shpejtë
- **HOW_TO_RUN.md** - Udhëzime detajuara ekzekutimi
- **PROJECT_SUMMARY.md** - Përmbledhje e projektit
- **FILE_STRUCTURE.md** - Struktura e skedarëve

---

## 🤝 Kontributi

Për të kontribuar në projekt:
1. Fork the repository
2. Krijo branch të ri (`git checkout -b feature/AmazingFeature`)
3. Commit ndryshimet (`git commit -m 'Add some AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Hap Pull Request

---

## 📄 Licensa

Ky projekt është i licensuar nën MIT License.

---

## 👨‍💻 Autori

Sistema e Menaxhimit Universitar (UMIS)
Zhvilluar me ❤️ për institucionet arsimore në Shqipëri

---

## 🆘 Mbështetje / Support

Për çështje, pyetje ose sugjerime:
- Hapni një issue në GitHub
- Dërgoni email në support@umis.edu.al
- Kontaktoni ekipin teknik

---

## 🚀 Versioni Aktual: v2.0.0

### Changelog
**v2.0.0** (2024-01-09)
- ✅ Autentifikim i plotë me JWT
- ✅ Sistem rolesh (Admin, Pedagog, Student)
- ✅ 30+ tabela të reja në databazë
- ✅ CRM për pedagogë
- ✅ Portal për studentë
- ✅ UI/UX i riprojektuar
- ✅ Security enhancements
- ✅ Professional design

**v1.0.0** (2024-01-08)
- ✅ Release fillestare
- ✅ CRUD operations për studentë dhe lëndë
- ✅ Statistika bazike

---

**UMIS - University Management Information System**
*Transformojmë menaxhimin akademik në Shqipëri* 🇦🇱
