-- Sorry guys, it seems, JPA is so stupid so it cannot handle even simple MULTILINE SQL =(

-- entity tables
CREATE TABLE IF NOT EXISTS person (id SERIAL PRIMARY KEY, first_name TEXT NOT NULL, middle_name TEXT, last_name TEXT NOT NULL, document TEXT, birth_date TIMESTAMP WITH TIME ZONE NOT NULL, death_date TIMESTAMP WITH TIME ZONE, contact_info_id INTEGER NOT NULL);
CREATE TABLE IF NOT EXISTS person_note (id SERIAL PRIMARY KEY, author_inquisitor_id INTEGER NOT NULL, person_id INTEGER NOT NULL, text TEXT NOT NULL);
CREATE TABLE IF NOT EXISTS contact_info (id SERIAL PRIMARY KEY, home_address_id INTEGER, work_address_id INTEGER, person_id INTEGER NOT NULL);
CREATE TABLE IF NOT EXISTS address (id SERIAL PRIMARY KEY, country TEXT, region TEXT, city TEXT, street TEXT, building INTEGER, apartment INTEGER, postal_code TEXT);
CREATE TABLE IF NOT EXISTS crime (id SERIAL PRIMARY KEY, title TEXT NOT NULL, description TEXT, exists_since TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(), exists_until TIMESTAMP WITH TIME ZONE);
CREATE TABLE IF NOT EXISTS email_record (id SERIAL PRIMARY KEY, email TEXT NOT NULL, type TEXT NOT NULL, contact_info_id INTEGER NOT NULL);
CREATE TABLE IF NOT EXISTS phone_record (id SERIAL PRIMARY KEY, phone_number TEXT NOT NULL, type TEXT NOT NULL, contact_info_id INTEGER NOT NULL);
CREATE TABLE IF NOT EXISTS inquisitor (id SERIAL PRIMARY KEY, person_id INTEGER NOT NULL, codename TEXT);
CREATE TABLE IF NOT EXISTS interest (id SERIAL PRIMARY KEY, title TEXT NOT NULL, description TEXT, sin_rate INTEGER NOT NULL);
CREATE TABLE IF NOT EXISTS investigation (id SERIAL PRIMARY KEY, assignee_inquisitor_id INTEGER NOT NULL, date_opened TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(), date_closed TIMESTAMP WITH TIME ZONE, suspect_person_id INTEGER NOT NULL, verdict TEXT, sentence TEXT);
CREATE TABLE IF NOT EXISTS investigation_record (id SERIAL PRIMARY KEY, operator_id INTEGER NOT NULL, date_created TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(), action TEXT NOT NULL, investigation_id INTEGER NOT NULL);
CREATE TABLE IF NOT EXISTS investigation_note (id SERIAL PRIMARY KEY, author_inquisitor_id INTEGER NOT NULL, title TEXT NOT NULL, text TEXT NOT NULL, investigation_id INTEGER NOT NULL);

-- m2m tables
CREATE TABLE IF NOT EXISTS m2m_contact_info_address (contact_info_id INTEGER NOT NULL, address_id INTEGER NOT NULL);
CREATE TABLE IF NOT EXISTS m2m_investigation_crime (investigation_id INTEGER NOT NULL, crime_id INTEGER NOT NULL);
CREATE TABLE IF NOT EXISTS m2m_person_interest (person_id INTEGER NOT NULL, interest_id INTEGER NOT NULL);

-- Entity constraints
ALTER TABLE IF EXISTS contact_info DROP CONSTRAINT IF EXISTS contact_info_home_address_id_fkey;
ALTER TABLE IF EXISTS contact_info ADD CONSTRAINT contact_info_home_address_id_fkey FOREIGN KEY (home_address_id) REFERENCES address (id);

ALTER TABLE IF EXISTS contact_info DROP CONSTRAINT IF EXISTS contact_info_work_address_id_fkey;
ALTER TABLE IF EXISTS contact_info ADD CONSTRAINT contact_info_work_address_id_fkey FOREIGN KEY (work_address_id) REFERENCES address (id);

ALTER TABLE IF EXISTS contact_info DROP CONSTRAINT IF EXISTS contact_info_person_id_fkey;
ALTER TABLE IF EXISTS contact_info ADD CONSTRAINT contact_info_person_id_fkey FOREIGN KEY (person_id) REFERENCES person (id);

ALTER TABLE IF EXISTS email_record DROP CONSTRAINT IF EXISTS email_record_contact_info_id_fkey;
ALTER TABLE IF EXISTS email_record ADD CONSTRAINT email_record_contact_info_id_fkey FOREIGN KEY (contact_info_id) REFERENCES contact_info (id);

ALTER TABLE IF EXISTS phone_record DROP CONSTRAINT IF EXISTS phone_record_contact_info_id_fkey;
ALTER TABLE IF EXISTS phone_record ADD CONSTRAINT phone_record_contact_info_id_fkey FOREIGN KEY (contact_info_id) REFERENCES contact_info (id);

ALTER TABLE IF EXISTS inquisitor DROP CONSTRAINT IF EXISTS inquisitor_person_id_fkey;
ALTER TABLE IF EXISTS inquisitor ADD CONSTRAINT inquisitor_person_id_fkey FOREIGN KEY (person_id) REFERENCES person (id);

ALTER TABLE IF EXISTS investigation DROP CONSTRAINT IF EXISTS investigation_assignee_inquisitor_id_fkey;
ALTER TABLE IF EXISTS investigation ADD CONSTRAINT investigation_assignee_inquisitor_id_fkey FOREIGN KEY (assignee_inquisitor_id) REFERENCES inquisitor (id);

ALTER TABLE IF EXISTS investigation DROP CONSTRAINT IF EXISTS investigation_suspect_person_id_fkey;
ALTER TABLE IF EXISTS investigation ADD CONSTRAINT investigation_suspect_person_id_fkey FOREIGN KEY (suspect_person_id) REFERENCES person (id);

ALTER TABLE IF EXISTS investigation_record DROP CONSTRAINT IF EXISTS investigation_record_operator_id_fkey;
ALTER TABLE IF EXISTS investigation_record ADD CONSTRAINT investigation_record_operator_id_fkey FOREIGN KEY (operator_id) REFERENCES inquisitor (id);

ALTER TABLE IF EXISTS investigation_record DROP CONSTRAINT IF EXISTS investigation_record_investigation_id_fkey;
ALTER TABLE IF EXISTS investigation_record ADD CONSTRAINT investigation_record_investigation_id_fkey FOREIGN KEY (investigation_id) REFERENCES investigation (id);

ALTER TABLE IF EXISTS investigation_note DROP CONSTRAINT IF EXISTS investigation_note_author_inquisitor_id_fkey;
ALTER TABLE IF EXISTS investigation_note ADD CONSTRAINT investigation_note_author_inquisitor_id_fkey FOREIGN KEY (author_inquisitor_id) REFERENCES person (id);

ALTER TABLE IF EXISTS investigation_note DROP CONSTRAINT IF EXISTS investigation_note_investigation_id_fkey;
ALTER TABLE IF EXISTS investigation_note ADD CONSTRAINT investigation_note_investigation_id_fkey FOREIGN KEY (investigation_id) REFERENCES investigation (id);

-- m2m constraints
ALTER TABLE IF EXISTS m2m_person_interest DROP CONSTRAINT IF EXISTS m2m_person_interest_person_id_fkey;
ALTER TABLE IF EXISTS m2m_person_interest ADD CONSTRAINT m2m_person_interest_person_id_fkey FOREIGN KEY (person_id) REFERENCES person (id);

ALTER TABLE IF EXISTS m2m_person_interest DROP CONSTRAINT IF EXISTS m2m_person_interest_interest_id_fkey;
ALTER TABLE IF EXISTS m2m_person_interest ADD CONSTRAINT m2m_person_interest_interest_id_fkey FOREIGN KEY (interest_id) REFERENCES interest (id);

ALTER TABLE IF EXISTS m2m_contact_info_address DROP CONSTRAINT IF EXISTS m2m_contact_info_address_contact_info_id_fkey;
ALTER TABLE IF EXISTS m2m_contact_info_address ADD CONSTRAINT m2m_contact_info_address_contact_info_id_fkey FOREIGN KEY (contact_info_id) REFERENCES contact_info (id);

ALTER TABLE IF EXISTS m2m_contact_info_address DROP CONSTRAINT IF EXISTS m2m_contact_info_address_address_id_fkey;
ALTER TABLE IF EXISTS m2m_contact_info_address ADD CONSTRAINT m2m_contact_info_address_address_id_fkey FOREIGN KEY (address_id) REFERENCES address (id);

ALTER TABLE IF EXISTS m2m_investigation_crime DROP CONSTRAINT IF EXISTS m2m_investigation_crime_investigation_id_fkey;
ALTER TABLE IF EXISTS m2m_investigation_crime ADD CONSTRAINT m2m_investigation_crime_investigation_id_fkey FOREIGN KEY (investigation_id) REFERENCES investigation (id);

ALTER TABLE IF EXISTS m2m_investigation_crime DROP CONSTRAINT IF EXISTS m2m_investigation_crime_crime_id_fkey;
ALTER TABLE IF EXISTS m2m_investigation_crime ADD CONSTRAINT m2m_investigation_crime_crime_id_fkey FOREIGN KEY (crime_id) REFERENCES crime (id);
