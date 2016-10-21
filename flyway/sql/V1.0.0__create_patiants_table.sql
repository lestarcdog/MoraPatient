CREATE TABLE patients (
  "id"           INT AUTO_INCREMENT PRIMARY KEY,
  "name"         VARCHAR(255),
  "ismale"       BOOLEAN,
  "birthdate"    DATE,
  "phone"        VARCHAR(20),
  "email"        VARCHAR(50),
  "city"         VARCHAR(50),
  "street"       VARCHAR(150),
  "lastmodified" TIMESTAMP
);

CREATE INDEX IDX_PATIENT_NAME
  ON patients ("name");