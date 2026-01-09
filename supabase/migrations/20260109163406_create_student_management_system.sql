/*
  # Sistema e Menaxhimit të Studentëve dhe Notave
  # Student and Grade Management System

  1. Tabelat e Reja / New Tables
    - `studentet` (students)
      - `id` (uuid, primary key) - ID unike për studentin
      - `emri` (text) - Emri i studentit
      - `mbiemri` (text) - Mbiemri i studentit
      - `email` (text, unique) - Email unik
      - `data_lindjes` (date) - Data e lindjes
      - `adresa` (text) - Adresa
      - `telefoni` (text) - Numri i telefonit
      - `data_regjistrimit` (timestamptz) - Data e regjistrimit
      - `aktiv` (boolean) - Statusi aktiv/jo-aktiv
      - `krijuar_me` (timestamptz) - Data e krijimit
      - `perditesuar_me` (timestamptz) - Data e përditësimit
    
    - `lendet` (subjects)
      - `id` (uuid, primary key) - ID unike për lëndën
      - `emri` (text) - Emri i lëndës
      - `kodi` (text, unique) - Kodi unik i lëndës
      - `pershkrimi` (text) - Përshkrimi i lëndës
      - `kredite` (integer) - Numri i krediteve
      - `semestri` (integer) - Semestri
      - `aktiv` (boolean) - Statusi aktiv/jo-aktiv
      - `krijuar_me` (timestamptz) - Data e krijimit
      - `perditesuar_me` (timestamptz) - Data e përditësimit
    
    - `regjistrime` (enrollments)
      - `id` (uuid, primary key) - ID unike për regjistrimin
      - `student_id` (uuid, foreign key) - Referenca në studentin
      - `lende_id` (uuid, foreign key) - Referenca në lëndën
      - `viti_akademik` (text) - Viti akademik (p.sh. "2023-2024")
      - `semestri` (integer) - Semestri
      - `data_regjistrimit` (timestamptz) - Data e regjistrimit
      - `aktiv` (boolean) - Statusi aktiv/jo-aktiv
      - `krijuar_me` (timestamptz) - Data e krijimit
    
    - `notat` (grades)
      - `id` (uuid, primary key) - ID unike për notën
      - `regjistrim_id` (uuid, foreign key) - Referenca në regjistrimin
      - `nota` (decimal) - Nota (0-10)
      - `lloji_provimit` (text) - Lloji i provimit (provim, kuiz, projekt, etj)
      - `data_provimit` (date) - Data e provimit
      - `komente` (text) - Komente shtesë
      - `krijuar_me` (timestamptz) - Data e krijimit
      - `perditesuar_me` (timestamptz) - Data e përditësimit

  2. Siguria / Security
    - Enable RLS on all tables
    - Politikat për lexim dhe shkrim për përdoruesit e autentikuar
    - Politikat për përditësim dhe fshirje

  3. Indekset / Indexes
    - Index për kërkime më të shpejta sipas emrit, email, kodit
    - Index për marrëdhëniet foreign key

  4. Shënime të Rëndësishme / Important Notes
    - Të gjitha tabelat përdorin UUID për primary keys
    - RLS është i aktivizuar për siguri maksimale
    - Timestamptz përdoret për të gjitha datat për të menaxhuar fushat kohore
    - Constraints për të siguruar integritetin e të dhënave
*/

-- Tabela e Studentëve / Students Table
CREATE TABLE IF NOT EXISTS studentet (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  emri text NOT NULL,
  mbiemri text NOT NULL,
  email text UNIQUE NOT NULL,
  data_lindjes date NOT NULL,
  adresa text DEFAULT '',
  telefoni text DEFAULT '',
  data_regjistrimit date DEFAULT CURRENT_DATE,
  aktiv boolean DEFAULT true,
  krijuar_me timestamptz DEFAULT now(),
  perditesuar_me timestamptz DEFAULT now()
);

-- Tabela e Lëndëve / Subjects Table
CREATE TABLE IF NOT EXISTS lendet (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  emri text NOT NULL,
  kodi text UNIQUE NOT NULL,
  pershkrimi text DEFAULT '',
  kredite integer NOT NULL DEFAULT 0,
  semestri integer NOT NULL DEFAULT 1,
  aktiv boolean DEFAULT true,
  krijuar_me timestamptz DEFAULT now(),
  perditesuar_me timestamptz DEFAULT now()
);

-- Tabela e Regjistrimeve / Enrollments Table
CREATE TABLE IF NOT EXISTS regjistrime (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  student_id uuid NOT NULL REFERENCES studentet(id) ON DELETE CASCADE,
  lende_id uuid NOT NULL REFERENCES lendet(id) ON DELETE CASCADE,
  viti_akademik text NOT NULL,
  semestri integer NOT NULL DEFAULT 1,
  data_regjistrimit timestamptz DEFAULT now(),
  aktiv boolean DEFAULT true,
  krijuar_me timestamptz DEFAULT now(),
  UNIQUE(student_id, lende_id, viti_akademik)
);

-- Tabela e Notave / Grades Table
CREATE TABLE IF NOT EXISTS notat (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  regjistrim_id uuid NOT NULL REFERENCES regjistrime(id) ON DELETE CASCADE,
  nota decimal(4,2) NOT NULL CHECK (nota >= 0 AND nota <= 10),
  lloji_provimit text NOT NULL DEFAULT 'provim',
  data_provimit date DEFAULT CURRENT_DATE,
  komente text DEFAULT '',
  krijuar_me timestamptz DEFAULT now(),
  perditesuar_me timestamptz DEFAULT now()
);

-- Indekset / Indexes
CREATE INDEX IF NOT EXISTS idx_studentet_emri ON studentet(emri);
CREATE INDEX IF NOT EXISTS idx_studentet_mbiemri ON studentet(mbiemri);
CREATE INDEX IF NOT EXISTS idx_studentet_email ON studentet(email);
CREATE INDEX IF NOT EXISTS idx_lendet_kodi ON lendet(kodi);
CREATE INDEX IF NOT EXISTS idx_lendet_emri ON lendet(emri);
CREATE INDEX IF NOT EXISTS idx_regjistrime_student ON regjistrime(student_id);
CREATE INDEX IF NOT EXISTS idx_regjistrime_lende ON regjistrime(lende_id);
CREATE INDEX IF NOT EXISTS idx_notat_regjistrim ON notat(regjistrim_id);

-- Aktivizo RLS / Enable RLS
ALTER TABLE studentet ENABLE ROW LEVEL SECURITY;
ALTER TABLE lendet ENABLE ROW LEVEL SECURITY;
ALTER TABLE regjistrime ENABLE ROW LEVEL SECURITY;
ALTER TABLE notat ENABLE ROW LEVEL SECURITY;

-- Politikat për Studentët / Policies for Students
CREATE POLICY "Authenticated users can view students"
  ON studentet FOR SELECT
  TO authenticated
  USING (true);

CREATE POLICY "Authenticated users can insert students"
  ON studentet FOR INSERT
  TO authenticated
  WITH CHECK (true);

CREATE POLICY "Authenticated users can update students"
  ON studentet FOR UPDATE
  TO authenticated
  USING (true)
  WITH CHECK (true);

CREATE POLICY "Authenticated users can delete students"
  ON studentet FOR DELETE
  TO authenticated
  USING (true);

-- Politikat për Lëndët / Policies for Subjects
CREATE POLICY "Authenticated users can view subjects"
  ON lendet FOR SELECT
  TO authenticated
  USING (true);

CREATE POLICY "Authenticated users can insert subjects"
  ON lendet FOR INSERT
  TO authenticated
  WITH CHECK (true);

CREATE POLICY "Authenticated users can update subjects"
  ON lendet FOR UPDATE
  TO authenticated
  USING (true)
  WITH CHECK (true);

CREATE POLICY "Authenticated users can delete subjects"
  ON lendet FOR DELETE
  TO authenticated
  USING (true);

-- Politikat për Regjistrime / Policies for Enrollments
CREATE POLICY "Authenticated users can view enrollments"
  ON regjistrime FOR SELECT
  TO authenticated
  USING (true);

CREATE POLICY "Authenticated users can insert enrollments"
  ON regjistrime FOR INSERT
  TO authenticated
  WITH CHECK (true);

CREATE POLICY "Authenticated users can update enrollments"
  ON regjistrime FOR UPDATE
  TO authenticated
  USING (true)
  WITH CHECK (true);

CREATE POLICY "Authenticated users can delete enrollments"
  ON regjistrime FOR DELETE
  TO authenticated
  USING (true);

-- Politikat për Notat / Policies for Grades
CREATE POLICY "Authenticated users can view grades"
  ON notat FOR SELECT
  TO authenticated
  USING (true);

CREATE POLICY "Authenticated users can insert grades"
  ON notat FOR INSERT
  TO authenticated
  WITH CHECK (true);

CREATE POLICY "Authenticated users can update grades"
  ON notat FOR UPDATE
  TO authenticated
  USING (true)
  WITH CHECK (true);

CREATE POLICY "Authenticated users can delete grades"
  ON notat FOR DELETE
  TO authenticated
  USING (true);

-- Funksion për të përditësuar timestamptz / Function to update timestamp
CREATE OR REPLACE FUNCTION update_perditesuar_me()
RETURNS TRIGGER AS $$
BEGIN
  NEW.perditesuar_me = now();
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Triggers për përditësimin automatik të timestamptz / Triggers for automatic timestamp update
CREATE TRIGGER update_studentet_perditesuar_me
  BEFORE UPDATE ON studentet
  FOR EACH ROW
  EXECUTE FUNCTION update_perditesuar_me();

CREATE TRIGGER update_lendet_perditesuar_me
  BEFORE UPDATE ON lendet
  FOR EACH ROW
  EXECUTE FUNCTION update_perditesuar_me();

CREATE TRIGGER update_notat_perditesuar_me
  BEFORE UPDATE ON notat
  FOR EACH ROW
  EXECUTE FUNCTION update_perditesuar_me();