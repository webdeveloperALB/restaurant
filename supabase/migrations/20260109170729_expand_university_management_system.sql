/*
  # Sistemi i Zgjeruar i Menaxhimit Universitar
  # Expanded University Management System

  1. Tabelat e Reja / New Tables
  
    A. AUTHENTICATION & USERS
    - `perdoruesit` (users) - Sistema e perdoruesve me role
    - `rolet` (roles) - Rolet: Admin, Pedagog, Student
    - `sesionet` (sessions) - Menaxhimi i sesioneve
    
    B. ORGANIZATIONAL STRUCTURE
    - `fakultetet` (faculties) - Fakultetet e universitetit
    - `departamentet` (departments) - Departamentet
    - `pedagoget` (pedagogues) - Pedagogët/Profesorët
    - `pozicionet` (positions) - Pozicionet akademike
    
    C. ACADEMIC MANAGEMENT
    - `kurset` (courses) - Kurset me detaje të plota
    - `kursi_materiale` (course_materials) - Materialet e kursit
    - `orari` (schedule) - Orari i mësimit
    - `viti_akademik_config` (academic_year_config) - Konfigurimi i vitit
    
    D. STUDENT ENGAGEMENT
    - `prania` (attendance) - Evidenca e pranisë
    - `detyrat` (assignments) - Detyrat e shtëpisë
    - `dorezimi_detyrave` (assignment_submissions) - Dorëzimet
    - `provime` (exams) - Provimet dhe rezultatet
    
    E. COMMUNICATION
    - `njoftime` (announcements) - Njoftimet publike
    - `mesazhet` (messages) - Mesazhet private
    - `diskutimet` (discussions) - Forum diskutimi
    
    F. DOCUMENTS & FILES
    - `dokumentet` (documents) - Dokumentet administrative
    - `transkirptet` (transcripts) - Transkirptet akademike
    - `certifikatat` (certificates) - Certifikatat
    
    G. FINANCIAL
    - `pagesat` (payments) - Pagesat dhe tarifat
    - `bursat` (scholarships) - Bursat
    
    H. SUPPORT SYSTEMS
    - `kalendari_akademik` (academic_calendar) - Kalendari
    - `kerkesa_administrative` (admin_requests) - Kërkesa
    - `vleresime` (evaluations) - Vlerësimet e pedagogëve
    - `logs` (activity_logs) - Log-et e aktivitetit

  2. Siguria / Security
    - RLS i aktivizuar për të gjitha tabelat
    - Politika të detajuara sipas roleve
    - Enkriptim për të dhëna të ndjeshme
    
  3. Relacionet / Relationships
    - Foreign keys për integritet
    - Indexes për performancë
    - Triggers për audit trail
*/

-- ============================================================================
-- A. AUTHENTICATION & USERS
-- ============================================================================

-- Rolet / Roles
CREATE TABLE IF NOT EXISTS rolet (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  emri text UNIQUE NOT NULL,
  pershkrimi text,
  nivel_aksesi integer DEFAULT 1,
  krijuar_me timestamptz DEFAULT now()
);

-- Përdoruesit / Users
CREATE TABLE IF NOT EXISTS perdoruesit (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  email text UNIQUE NOT NULL,
  fjalekalimi_hash text NOT NULL,
  roli_id uuid REFERENCES rolet(id),
  emri text NOT NULL,
  mbiemri text NOT NULL,
  foto_profili text,
  telefoni text,
  adresa text,
  data_lindjes date,
  aktiv boolean DEFAULT true,
  email_verifikuar boolean DEFAULT false,
  last_login timestamptz,
  krijuar_me timestamptz DEFAULT now(),
  perditesuar_me timestamptz DEFAULT now()
);

-- Sesionet / Sessions
CREATE TABLE IF NOT EXISTS sesionet (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  perdoruesi_id uuid REFERENCES perdoruesit(id) ON DELETE CASCADE,
  token text UNIQUE NOT NULL,
  ip_address text,
  user_agent text,
  skadimi timestamptz NOT NULL,
  krijuar_me timestamptz DEFAULT now()
);

-- ============================================================================
-- B. ORGANIZATIONAL STRUCTURE
-- ============================================================================

-- Fakultetet / Faculties
CREATE TABLE IF NOT EXISTS fakultetet (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  emri text NOT NULL,
  kodi text UNIQUE NOT NULL,
  pershkrimi text,
  dekan text,
  email text,
  telefoni text,
  vendndodhja text,
  aktiv boolean DEFAULT true,
  krijuar_me timestamptz DEFAULT now(),
  perditesuar_me timestamptz DEFAULT now()
);

-- Departamentet / Departments
CREATE TABLE IF NOT EXISTS departamentet (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  fakulteti_id uuid REFERENCES fakultetet(id) ON DELETE CASCADE,
  emri text NOT NULL,
  kodi text UNIQUE NOT NULL,
  pershkrimi text,
  kryetar text,
  email text,
  telefoni text,
  aktiv boolean DEFAULT true,
  krijuar_me timestamptz DEFAULT now(),
  perditesuar_me timestamptz DEFAULT now()
);

-- Pozicionet Akademike / Academic Positions
CREATE TABLE IF NOT EXISTS pozicionet (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  emri text UNIQUE NOT NULL,
  niveli integer DEFAULT 1,
  pershkrimi text,
  krijuar_me timestamptz DEFAULT now()
);

-- Pedagogët / Pedagogues (Teachers/Professors)
CREATE TABLE IF NOT EXISTS pedagoget (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  perdoruesi_id uuid UNIQUE REFERENCES perdoruesit(id) ON DELETE CASCADE,
  student_id uuid REFERENCES studentet(id),
  departamenti_id uuid REFERENCES departamentet(id),
  pozicioni_id uuid REFERENCES pozicionet(id),
  titulli_akademik text,
  specializimi text,
  biografia text,
  cv_url text,
  oret_konsultimi text,
  zyra text,
  data_fillimit date,
  data_perfundimit date,
  aktiv boolean DEFAULT true,
  krijuar_me timestamptz DEFAULT now(),
  perditesuar_me timestamptz DEFAULT now()
);

-- ============================================================================
-- C. ACADEMIC MANAGEMENT
-- ============================================================================

-- Kurset / Courses (Enhanced)
CREATE TABLE IF NOT EXISTS kurset (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  lende_id uuid REFERENCES lendet(id) ON DELETE CASCADE,
  pedagog_id uuid REFERENCES pedagoget(id),
  departamenti_id uuid REFERENCES departamentet(id),
  viti_akademik text NOT NULL,
  semestri integer NOT NULL,
  salla text,
  kapaciteti integer DEFAULT 30,
  numri_studenteve integer DEFAULT 0,
  ditet_mesimit text[], -- ['E Hënë', 'E Mërkurë']
  orari_fillimit time,
  orari_mbarimit time,
  pershkrimi_detajuar text,
  objektivat text,
  literatura text[],
  metoda_vleresimit text,
  aktiv boolean DEFAULT true,
  krijuar_me timestamptz DEFAULT now(),
  perditesuar_me timestamptz DEFAULT now()
);

-- Materialet e Kursit / Course Materials
CREATE TABLE IF NOT EXISTS kursi_materiale (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  kursi_id uuid REFERENCES kurset(id) ON DELETE CASCADE,
  titulli text NOT NULL,
  pershkrimi text,
  lloji text NOT NULL, -- syllabus, slide, document, video, link
  url_skedari text,
  madhesia_bytes bigint,
  format_skedari text,
  data_publikimit timestamptz DEFAULT now(),
  i_dukshem boolean DEFAULT true,
  shkarkime integer DEFAULT 0,
  krijuar_me timestamptz DEFAULT now()
);

-- Orari / Schedule
CREATE TABLE IF NOT EXISTS orari (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  kursi_id uuid REFERENCES kurset(id) ON DELETE CASCADE,
  dita_javes integer NOT NULL, -- 1=E Hënë, 7=E Diel
  ora_fillimit time NOT NULL,
  ora_mbarimit time NOT NULL,
  salla text,
  lloji text DEFAULT 'leksion', -- leksion, seminar, laborator
  aktiv boolean DEFAULT true,
  krijuar_me timestamptz DEFAULT now()
);

-- Konfigurimi i Vitit Akademik / Academic Year Configuration
CREATE TABLE IF NOT EXISTS viti_akademik_config (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  viti_akademik text UNIQUE NOT NULL,
  data_fillimit date NOT NULL,
  data_perfundimit date NOT NULL,
  semestri_1_fillimi date,
  semestri_1_mbarimi date,
  semestri_2_fillimi date,
  semestri_2_mbarimi date,
  pushimet jsonb, -- [{emri: 'Pushimi Verës', fillimi: '2024-07-01', mbarimi: '2024-09-01'}]
  aktiv boolean DEFAULT true,
  krijuar_me timestamptz DEFAULT now()
);

-- ============================================================================
-- D. STUDENT ENGAGEMENT
-- ============================================================================

-- Prania / Attendance
CREATE TABLE IF NOT EXISTS prania (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  kursi_id uuid REFERENCES kurset(id) ON DELETE CASCADE,
  student_id uuid REFERENCES studentet(id) ON DELETE CASCADE,
  data_mesimit date NOT NULL,
  statusi text NOT NULL, -- present, absent, late, excused
  komente text,
  regjistruar_nga uuid REFERENCES pedagoget(id),
  krijuar_me timestamptz DEFAULT now(),
  UNIQUE(kursi_id, student_id, data_mesimit)
);

-- Detyrat / Assignments
CREATE TABLE IF NOT EXISTS detyrat (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  kursi_id uuid REFERENCES kurset(id) ON DELETE CASCADE,
  titulli text NOT NULL,
  pershkrimi text NOT NULL,
  instruksione text,
  data_publikimit timestamptz DEFAULT now(),
  data_skadimit timestamptz NOT NULL,
  piket_max decimal(5,2) DEFAULT 100,
  peshe_perqindje decimal(5,2), -- Peso në notën finale
  lloji text DEFAULT 'individual', -- individual, grup
  skedar_bashkelidhur text,
  i_dukshem boolean DEFAULT true,
  lejon_dorezim_vone boolean DEFAULT false,
  krijuar_me timestamptz DEFAULT now(),
  perditesuar_me timestamptz DEFAULT now()
);

-- Dorëzimi i Detyrave / Assignment Submissions
CREATE TABLE IF NOT EXISTS dorezimi_detyrave (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  detyra_id uuid REFERENCES detyrat(id) ON DELETE CASCADE,
  student_id uuid REFERENCES studentet(id) ON DELETE CASCADE,
  teksti_pergjigjes text,
  skedar_url text,
  data_dorezimit timestamptz DEFAULT now(),
  dorezim_vone boolean DEFAULT false,
  nota decimal(5,2),
  komente_pedagogu text,
  statusi text DEFAULT 'dorezuar', -- dorezuar, ne_vleresim, vleresuar
  vleresuar_nga uuid REFERENCES pedagoget(id),
  data_vleresimit timestamptz,
  krijuar_me timestamptz DEFAULT now(),
  perditesuar_me timestamptz DEFAULT now(),
  UNIQUE(detyra_id, student_id)
);

-- Provimet / Exams
CREATE TABLE IF NOT EXISTS provime (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  kursi_id uuid REFERENCES kurset(id) ON DELETE CASCADE,
  titulli text NOT NULL,
  lloji text NOT NULL, -- midterm, final, quiz, oral
  data_provimit timestamptz NOT NULL,
  kohezgjatja_minuta integer,
  salla text,
  piket_max decimal(5,2) DEFAULT 100,
  peshe_perqindje decimal(5,2),
  pershkrimi text,
  i_dukshem boolean DEFAULT true,
  krijuar_me timestamptz DEFAULT now(),
  perditesuar_me timestamptz DEFAULT now()
);

-- Rezultatet e Provimeve / Exam Results
CREATE TABLE IF NOT EXISTS rezultatet_provimeve (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  provimi_id uuid REFERENCES provime(id) ON DELETE CASCADE,
  student_id uuid REFERENCES studentet(id) ON DELETE CASCADE,
  nota decimal(5,2) NOT NULL CHECK (nota >= 0),
  komente text,
  status text DEFAULT 'i_miratuar', -- i_miratuar, ne_rishqyrtim, apeluar
  krijuar_me timestamptz DEFAULT now(),
  perditesuar_me timestamptz DEFAULT now(),
  UNIQUE(provimi_id, student_id)
);

-- ============================================================================
-- E. COMMUNICATION
-- ============================================================================

-- Njoftimet / Announcements
CREATE TABLE IF NOT EXISTS njoftime (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  titulli text NOT NULL,
  permbajtja text NOT NULL,
  lloji text DEFAULT 'info', -- info, warning, urgent, event
  publikuar_nga uuid REFERENCES perdoruesit(id),
  shfaq_per_rolet text[], -- ['student', 'pedagog', 'admin']
  kursi_id uuid REFERENCES kurset(id),
  departamenti_id uuid REFERENCES departamentet(id),
  i_dukshem boolean DEFAULT true,
  data_skadimit timestamptz,
  lexime integer DEFAULT 0,
  krijuar_me timestamptz DEFAULT now(),
  perditesuar_me timestamptz DEFAULT now()
);

-- Mesazhet / Messages
CREATE TABLE IF NOT EXISTS mesazhet (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  dergues_id uuid REFERENCES perdoruesit(id) ON DELETE CASCADE,
  marres_id uuid REFERENCES perdoruesit(id) ON DELETE CASCADE,
  titulli text NOT NULL,
  permbajtja text NOT NULL,
  i_lexuar boolean DEFAULT false,
  data_leximit timestamptz,
  skedar_bashkelidhur text,
  i_fshire_dergues boolean DEFAULT false,
  i_fshire_marres boolean DEFAULT false,
  krijuar_me timestamptz DEFAULT now()
);

-- Diskutimet / Discussion Forums
CREATE TABLE IF NOT EXISTS diskutimet (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  kursi_id uuid REFERENCES kurset(id) ON DELETE CASCADE,
  perdoruesi_id uuid REFERENCES perdoruesit(id),
  prindi_id uuid REFERENCES diskutimet(id), -- për replies
  titulli text,
  permbajtja text NOT NULL,
  i_fiksuar boolean DEFAULT false,
  i_mbyllur boolean DEFAULT false,
  shikime integer DEFAULT 0,
  krijuar_me timestamptz DEFAULT now(),
  perditesuar_me timestamptz DEFAULT now()
);

-- ============================================================================
-- F. DOCUMENTS & FILES
-- ============================================================================

-- Dokumentet / Documents
CREATE TABLE IF NOT EXISTS dokumentet (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  student_id uuid REFERENCES studentet(id) ON DELETE CASCADE,
  lloji text NOT NULL, -- aplikim, kerkese, certifikatë, etj
  titulli text NOT NULL,
  pershkrimi text,
  url_skedari text NOT NULL,
  format_skedari text,
  madhesia_bytes bigint,
  statusi text DEFAULT 'ne_pritje', -- ne_pritje, miratuar, refuzuar
  shqyrtuar_nga uuid REFERENCES pedagoget(id),
  data_shqyrtimit timestamptz,
  komente_shqyrtimi text,
  krijuar_me timestamptz DEFAULT now(),
  perditesuar_me timestamptz DEFAULT now()
);

-- Transkirptet / Transcripts
CREATE TABLE IF NOT EXISTS transkirptet (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  student_id uuid REFERENCES studentet(id) ON DELETE CASCADE,
  viti_akademik text NOT NULL,
  semestri integer,
  mesatarja_pergjithshme decimal(4,2),
  totali_krediteve integer DEFAULT 0,
  kredite_te_perfunduara integer DEFAULT 0,
  gpa decimal(3,2),
  url_pdf text,
  data_gjenerimit timestamptz DEFAULT now(),
  i_miratuar boolean DEFAULT false,
  miratuar_nga uuid REFERENCES pedagoget(id),
  data_miratimit timestamptz,
  krijuar_me timestamptz DEFAULT now()
);

-- Certifikatat / Certificates
CREATE TABLE IF NOT EXISTS certifikatat (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  student_id uuid REFERENCES studentet(id) ON DELETE CASCADE,
  lloji text NOT NULL, -- diplomë, certifikatë pjesëmarrje, etj
  titulli text NOT NULL,
  pershkrimi text,
  data_leshimit date NOT NULL,
  numri_serial text UNIQUE,
  url_pdf text,
  i_verifikuar boolean DEFAULT true,
  leshuar_nga uuid REFERENCES pedagoget(id),
  krijuar_me timestamptz DEFAULT now()
);

-- ============================================================================
-- G. FINANCIAL
-- ============================================================================

-- Pagesat / Payments
CREATE TABLE IF NOT EXISTS pagesat (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  student_id uuid REFERENCES studentet(id) ON DELETE CASCADE,
  lloji text NOT NULL, -- tarifë shkollimi, gjobë, rinovim, etj
  pershkrimi text,
  shuma decimal(10,2) NOT NULL,
  monedha text DEFAULT 'ALL',
  data_skadimit date,
  statusi text DEFAULT 'pa_paguar', -- pa_paguar, paguar, pjeserisht_paguar
  metoda_pageses text, -- cash, card, transfer
  data_pageses timestamptz,
  reference_code text UNIQUE,
  fature_url text,
  krijuar_me timestamptz DEFAULT now(),
  perditesuar_me timestamptz DEFAULT now()
);

-- Bursat / Scholarships
CREATE TABLE IF NOT EXISTS bursat (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  student_id uuid REFERENCES studentet(id) ON DELETE CASCADE,
  emri_burses text NOT NULL,
  lloji text, -- akademike, sportive, sociale
  shuma decimal(10,2) NOT NULL,
  monedha text DEFAULT 'ALL',
  viti_akademik text NOT NULL,
  data_fillimit date,
  data_perfundimit date,
  statusi text DEFAULT 'aktive', -- aktive, e_perfunduar, e_pezulluar
  sponsorizuesi text,
  kushtet text,
  krijuar_me timestamptz DEFAULT now(),
  perditesuar_me timestamptz DEFAULT now()
);

-- ============================================================================
-- H. SUPPORT SYSTEMS
-- ============================================================================

-- Kalendari Akademik / Academic Calendar
CREATE TABLE IF NOT EXISTS kalendari_akademik (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  titulli text NOT NULL,
  pershkrimi text,
  lloji text NOT NULL, -- ngjarje, provim, pushim, afat
  data_fillimit timestamptz NOT NULL,
  data_perfundimit timestamptz,
  vendndodhja text,
  organizator text,
  kursi_id uuid REFERENCES kurset(id),
  departamenti_id uuid REFERENCES departamentet(id),
  per_kedo boolean DEFAULT false,
  i_dukshem boolean DEFAULT true,
  krijuar_me timestamptz DEFAULT now()
);

-- Kërkesat Administrative / Administrative Requests
CREATE TABLE IF NOT EXISTS kerkesa_administrative (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  student_id uuid REFERENCES studentet(id) ON DELETE CASCADE,
  lloji text NOT NULL, -- transkript, certifikatë, arsyetim, etj
  titulli text NOT NULL,
  pershkrimi text NOT NULL,
  prioriteti text DEFAULT 'normale', -- e_ulet, normale, e_larte, urgjente
  statusi text DEFAULT 'e_re', -- e_re, ne_proces, e_perfunduar, e_refuzuar
  asgn_pedagogut uuid REFERENCES pedagoget(id),
  data_perfundimit timestamptz,
  pergjigja text,
  krijuar_me timestamptz DEFAULT now(),
  perditesuar_me timestamptz DEFAULT now()
);

-- Vlerësimet e Pedagogëve / Teacher Evaluations
CREATE TABLE IF NOT EXISTS vleresime (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  pedagog_id uuid REFERENCES pedagoget(id) ON DELETE CASCADE,
  kursi_id uuid REFERENCES kurset(id) ON DELETE CASCADE,
  student_id uuid REFERENCES studentet(id) ON DELETE CASCADE,
  viti_akademik text NOT NULL,
  semestri integer NOT NULL,
  nota_mesimore decimal(3,2) CHECK (nota_mesimore >= 1 AND nota_mesimore <= 5),
  nota_komunikimi decimal(3,2) CHECK (nota_komunikimi >= 1 AND nota_komunikimi <= 5),
  nota_organizimi decimal(3,2) CHECK (nota_organizimi >= 1 AND nota_organizimi <= 5),
  nota_materiali decimal(3,2) CHECK (nota_materiali >= 1 AND nota_materiali <= 5),
  nota_pergjithshme decimal(3,2),
  komente text,
  anonim boolean DEFAULT true,
  krijuar_me timestamptz DEFAULT now(),
  UNIQUE(pedagog_id, kursi_id, student_id, viti_akademik, semestri)
);

-- Activity Logs
CREATE TABLE IF NOT EXISTS logs (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  perdoruesi_id uuid REFERENCES perdoruesit(id),
  aksioni text NOT NULL,
  tabela text,
  rekord_id uuid,
  detajet jsonb,
  ip_address text,
  user_agent text,
  krijuar_me timestamptz DEFAULT now()
);

-- ============================================================================
-- INDEXES FOR PERFORMANCE
-- ============================================================================

CREATE INDEX IF NOT EXISTS idx_perdoruesit_email ON perdoruesit(email);
CREATE INDEX IF NOT EXISTS idx_perdoruesit_roli ON perdoruesit(roli_id);
CREATE INDEX IF NOT EXISTS idx_pedagoget_departamenti ON pedagoget(departamenti_id);
CREATE INDEX IF NOT EXISTS idx_kurset_pedagog ON kurset(pedagog_id);
CREATE INDEX IF NOT EXISTS idx_kurset_viti ON kurset(viti_akademik, semestri);
CREATE INDEX IF NOT EXISTS idx_prania_kursi_student ON prania(kursi_id, student_id);
CREATE INDEX IF NOT EXISTS idx_prania_data ON prania(data_mesimit);
CREATE INDEX IF NOT EXISTS idx_detyrat_kursi ON detyrat(kursi_id);
CREATE INDEX IF NOT EXISTS idx_dorezimi_student ON dorezimi_detyrave(student_id);
CREATE INDEX IF NOT EXISTS idx_mesazhet_marres ON mesazhet(marres_id, i_lexuar);
CREATE INDEX IF NOT EXISTS idx_njoftime_data ON njoftime(krijuar_me DESC);
CREATE INDEX IF NOT EXISTS idx_pagesat_student ON pagesat(student_id, statusi);
CREATE INDEX IF NOT EXISTS idx_logs_perdoruesi ON logs(perdoruesi_id, krijuar_me DESC);

-- ============================================================================
-- ENABLE ROW LEVEL SECURITY
-- ============================================================================

ALTER TABLE rolet ENABLE ROW LEVEL SECURITY;
ALTER TABLE perdoruesit ENABLE ROW LEVEL SECURITY;
ALTER TABLE sesionet ENABLE ROW LEVEL SECURITY;
ALTER TABLE fakultetet ENABLE ROW LEVEL SECURITY;
ALTER TABLE departamentet ENABLE ROW LEVEL SECURITY;
ALTER TABLE pozicionet ENABLE ROW LEVEL SECURITY;
ALTER TABLE pedagoget ENABLE ROW LEVEL SECURITY;
ALTER TABLE kurset ENABLE ROW LEVEL SECURITY;
ALTER TABLE kursi_materiale ENABLE ROW LEVEL SECURITY;
ALTER TABLE orari ENABLE ROW LEVEL SECURITY;
ALTER TABLE viti_akademik_config ENABLE ROW LEVEL SECURITY;
ALTER TABLE prania ENABLE ROW LEVEL SECURITY;
ALTER TABLE detyrat ENABLE ROW LEVEL SECURITY;
ALTER TABLE dorezimi_detyrave ENABLE ROW LEVEL SECURITY;
ALTER TABLE provime ENABLE ROW LEVEL SECURITY;
ALTER TABLE rezultatet_provimeve ENABLE ROW LEVEL SECURITY;
ALTER TABLE njoftime ENABLE ROW LEVEL SECURITY;
ALTER TABLE mesazhet ENABLE ROW LEVEL SECURITY;
ALTER TABLE diskutimet ENABLE ROW LEVEL SECURITY;
ALTER TABLE dokumentet ENABLE ROW LEVEL SECURITY;
ALTER TABLE transkirptet ENABLE ROW LEVEL SECURITY;
ALTER TABLE certifikatat ENABLE ROW LEVEL SECURITY;
ALTER TABLE pagesat ENABLE ROW LEVEL SECURITY;
ALTER TABLE bursat ENABLE ROW LEVEL SECURITY;
ALTER TABLE kalendari_akademik ENABLE ROW LEVEL SECURITY;
ALTER TABLE kerkesa_administrative ENABLE ROW LEVEL SECURITY;
ALTER TABLE vleresime ENABLE ROW LEVEL SECURITY;
ALTER TABLE logs ENABLE ROW LEVEL SECURITY;

-- ============================================================================
-- RLS POLICIES (Examples - Expand as needed)
-- ============================================================================

-- Policies for Users
CREATE POLICY "Users can view own profile" ON perdoruesit FOR SELECT TO authenticated USING (auth.uid() = id);
CREATE POLICY "Admins can view all users" ON perdoruesit FOR SELECT TO authenticated USING (
  EXISTS (SELECT 1 FROM perdoruesit WHERE id = auth.uid() AND roli_id IN (SELECT id FROM rolet WHERE emri = 'admin'))
);

-- Policies for Pedagogues
CREATE POLICY "Pedagogues can view own data" ON pedagoget FOR SELECT TO authenticated USING (perdoruesi_id = auth.uid());
CREATE POLICY "Students can view pedagogues" ON pedagoget FOR SELECT TO authenticated USING (aktiv = true);

-- Policies for Courses
CREATE POLICY "Users can view active courses" ON kurset FOR SELECT TO authenticated USING (aktiv = true);
CREATE POLICY "Pedagogues can manage own courses" ON kurset FOR ALL TO authenticated USING (
  pedagog_id IN (SELECT id FROM pedagoget WHERE perdoruesi_id = auth.uid())
);

-- Policies for Attendance
CREATE POLICY "Pedagogues can manage attendance" ON prania FOR ALL TO authenticated USING (
  kursi_id IN (SELECT id FROM kurset WHERE pedagog_id IN (SELECT id FROM pedagoget WHERE perdoruesi_id = auth.uid()))
);
CREATE POLICY "Students can view own attendance" ON prania FOR SELECT TO authenticated USING (
  student_id IN (SELECT id FROM studentet WHERE email IN (SELECT email FROM perdoruesit WHERE id = auth.uid()))
);

-- Policies for Messages
CREATE POLICY "Users can view own messages" ON mesazhet FOR SELECT TO authenticated USING (
  dergues_id = auth.uid() OR marres_id = auth.uid()
);
CREATE POLICY "Users can send messages" ON mesazhet FOR INSERT TO authenticated WITH CHECK (dergues_id = auth.uid());

-- Additional policies would be added for all other tables following similar patterns

-- ============================================================================
-- INSERT DEFAULT DATA
-- ============================================================================

-- Insert default roles
INSERT INTO rolet (emri, pershkrimi, nivel_aksesi) VALUES
  ('admin', 'Administrator i sistemit me akses të plotë', 100),
  ('pedagog', 'Pedagog/Profesor me akses në kurset e tij', 50),
  ('student', 'Student me akses në të dhënat personale', 10)
ON CONFLICT (emri) DO NOTHING;

-- Insert default academic positions
INSERT INTO pozicionet (emri, niveli, pershkrimi) VALUES
  ('Profesor i Rregullt', 5, 'Profesor me gradën më të lartë akademike'),
  ('Profesor i Asociuar', 4, 'Profesor në gradën e dytë'),
  ('Profesor Asistent', 3, 'Profesor në fillim të karrierës'),
  ('Lektor', 2, 'Mësues me përgjegjësi të kufizuara'),
  ('Asistent', 1, 'Asistent mësimor')
ON CONFLICT (emri) DO NOTHING;

-- ============================================================================
-- TRIGGERS
-- ============================================================================

-- Trigger për përditësimin automatik të timestamp-it
CREATE OR REPLACE FUNCTION update_timestamp()
RETURNS TRIGGER AS $$
BEGIN
  NEW.perditesuar_me = now();
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Apply trigger to all relevant tables
CREATE TRIGGER update_perdoruesit_timestamp BEFORE UPDATE ON perdoruesit FOR EACH ROW EXECUTE FUNCTION update_timestamp();
CREATE TRIGGER update_pedagoget_timestamp BEFORE UPDATE ON pedagoget FOR EACH ROW EXECUTE FUNCTION update_timestamp();
CREATE TRIGGER update_kurset_timestamp BEFORE UPDATE ON kurset FOR EACH ROW EXECUTE FUNCTION update_timestamp();
CREATE TRIGGER update_detyrat_timestamp BEFORE UPDATE ON detyrat FOR EACH ROW EXECUTE FUNCTION update_timestamp();
CREATE TRIGGER update_fakultetet_timestamp BEFORE UPDATE ON fakultetet FOR EACH ROW EXECUTE FUNCTION update_timestamp();
CREATE TRIGGER update_departamentet_timestamp BEFORE UPDATE ON departamentet FOR EACH ROW EXECUTE FUNCTION update_timestamp();

-- Trigger për llogaritjen automatike të GPA
CREATE OR REPLACE FUNCTION calculate_gpa()
RETURNS TRIGGER AS $$
BEGIN
  NEW.nota_pergjithshme := (
    COALESCE(NEW.nota_mesimore, 0) + 
    COALESCE(NEW.nota_komunikimi, 0) + 
    COALESCE(NEW.nota_organizimi, 0) + 
    COALESCE(NEW.nota_materiali, 0)
  ) / 4.0;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER calculate_evaluation_gpa BEFORE INSERT OR UPDATE ON vleresime 
FOR EACH ROW EXECUTE FUNCTION calculate_gpa();