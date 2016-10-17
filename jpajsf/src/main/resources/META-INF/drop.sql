-- Entity constraints
ALTER TABLE IF EXISTS contact_info DROP CONSTRAINT IF EXISTS contact_info_home_address_id_fkey;
ALTER TABLE IF EXISTS contact_info DROP CONSTRAINT IF EXISTS contact_info_work_address_id_fkey;
ALTER TABLE IF EXISTS contact_info DROP CONSTRAINT IF EXISTS contact_info_person_id_fkey;
ALTER TABLE IF EXISTS email_record DROP CONSTRAINT IF EXISTS email_record_contact_info_id_fkey;
ALTER TABLE IF EXISTS phone_record DROP CONSTRAINT IF EXISTS phone_record_contact_info_id_fkey;
ALTER TABLE IF EXISTS inquisitor DROP CONSTRAINT IF EXISTS inquisitor_person_id_fkey;
ALTER TABLE IF EXISTS investigation DROP CONSTRAINT IF EXISTS investigation_assignee_inquisitor_id_fkey;
ALTER TABLE IF EXISTS investigation DROP CONSTRAINT IF EXISTS investigation_suspect_person_id_fkey;
ALTER TABLE IF EXISTS investigation_record DROP CONSTRAINT IF EXISTS investigation_record_operator_id_fkey;
ALTER TABLE IF EXISTS investigation_record DROP CONSTRAINT IF EXISTS investigation_record_investigation_id_fkey;
ALTER TABLE IF EXISTS investigation_note DROP CONSTRAINT IF EXISTS investigation_note_author_inquisitor_id_fkey;
ALTER TABLE IF EXISTS investigation_note DROP CONSTRAINT IF EXISTS investigation_note_investigation_id_fkey;

-- m2m constraints
ALTER TABLE IF EXISTS m2m_person_interest DROP CONSTRAINT IF EXISTS m2m_person_interest_person_id_fkey;
ALTER TABLE IF EXISTS m2m_person_interest DROP CONSTRAINT IF EXISTS m2m_person_interest_interest_id_fkey;
ALTER TABLE IF EXISTS m2m_contact_info_address DROP CONSTRAINT IF EXISTS m2m_contact_info_address_contact_info_id_fkey;
ALTER TABLE IF EXISTS m2m_contact_info_address DROP CONSTRAINT IF EXISTS m2m_contact_info_address_address_id_fkey;
ALTER TABLE IF EXISTS m2m_investigation_crime DROP CONSTRAINT IF EXISTS m2m_investigation_crime_investigation_id_fkey;
ALTER TABLE IF EXISTS m2m_investigation_crime DROP CONSTRAINT IF EXISTS m2m_investigation_crime_crime_id_fkey;

-- entity tables
DROP TABLE IF EXISTS person;
DROP TABLE IF EXISTS person_note;
DROP TABLE IF EXISTS contact_info;
DROP TABLE IF EXISTS address;
DROP TABLE IF EXISTS crime;
DROP TABLE IF EXISTS email_record;
DROP TABLE IF EXISTS phone_record;
DROP TABLE IF EXISTS inquisitor;
DROP TABLE IF EXISTS interest;
DROP TABLE IF EXISTS investigation;
DROP TABLE IF EXISTS investigation_record;
DROP TABLE IF EXISTS investigation_note;

-- m2m tables
DROP TABLE IF EXISTS m2m_contact_info_address;
DROP TABLE IF EXISTS m2m_investigation_crime;
DROP TABLE IF EXISTS m2m_person_interest;
