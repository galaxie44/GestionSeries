-- Table utilisateurs API (évite l'erreur MySQL « clé trop longue » sur UNIQUE VARCHAR(255))
CREATE TABLE IF NOT EXISTS AppUser (
  id INT NOT NULL AUTO_INCREMENT,
  login VARCHAR(100) NOT NULL,
  password VARCHAR(255) NOT NULL,
  role VARCHAR(32) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uk_appuser_login (login)
);
