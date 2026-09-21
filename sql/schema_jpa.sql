-- Tables générées par JPA à partir des entités (référence PDF p.51)
-- En pratique : créées au démarrage Tomcat via eclipselink.ddl-generation

CREATE TABLE IF NOT EXISTS Serie (
  id INT NOT NULL AUTO_INCREMENT,
  nom VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS Saison (
  id INT NOT NULL AUTO_INCREMENT,
  numero INT NOT NULL,
  nom VARCHAR(255) NOT NULL,
  fk_serie INT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FK_Saison_fk_serie FOREIGN KEY (fk_serie) REFERENCES Serie (id)
);

CREATE TABLE IF NOT EXISTS Episode (
  id INT NOT NULL AUTO_INCREMENT,
  titre VARCHAR(255) NOT NULL,
  numero INT NOT NULL,
  vu TINYINT(1) NOT NULL DEFAULT 0,
  fk_saison INT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FK_Episode_fk_saison FOREIGN KEY (fk_saison) REFERENCES Saison (id)
);
