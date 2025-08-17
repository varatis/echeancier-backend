-- Structure des tables pour l'application Échéancier

CREATE TABLE utilisateurs (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              nom_utilisateur VARCHAR(50) NOT NULL UNIQUE,
                              email VARCHAR(100) UNIQUE NOT NULL,
                              mot_de_passe VARCHAR(255) NOT NULL
);

CREATE TABLE depenses (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          description VARCHAR(255) NOT NULL,
                          montant DECIMAL(10,2) NOT NULL,
                          categorie VARCHAR(50) NOT NULL,
                          date_depense DATE NOT NULL,
                          commentaires TEXT,
                          utilisateur_id BIGINT NOT NULL,
                          date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          FOREIGN KEY (utilisateur_id) REFERENCES utilisateurs(id) ON DELETE CASCADE
);