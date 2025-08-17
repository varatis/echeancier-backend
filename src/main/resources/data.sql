-- Données de test pour l'application Échéancier

-- Insertion d'utilisateurs de test
INSERT INTO UTILISATEURS (nom_utilisateur, email, mot_de_passe, nom, prenom, date_creation)
VALUES
    ('yoann123', 'yoann@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Dupont', 'Yoann', CURRENT_TIMESTAMP),
    ('marie456', 'marie@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Martin', 'Marie', CURRENT_TIMESTAMP);

-- Insertion de dépenses de test
INSERT INTO DEPENSES (description, montant, categorie, date_depense, commentaires, utilisateur_id, date_creation)
VALUES
    ('Courses Carrefour', 85.50, 'Alimentation', '2024-01-15', 'Courses hebdomadaires', 1, CURRENT_TIMESTAMP),
    ('Essence', 65.20, 'Transport', '2024-01-14', 'Plein d''essence', 1, CURRENT_TIMESTAMP),
    ('Restaurant', 45.00, 'Loisirs', '2024-01-13', 'Déjeuner en ville', 1, CURRENT_TIMESTAMP),
    ('Électricité EDF', 120.30, 'Logement', '2024-01-12', 'Facture mensuelle', 1, CURRENT_TIMESTAMP),
    ('Cinéma', 24.00, 'Loisirs', '2024-01-11', 'Film en soirée', 1, CURRENT_TIMESTAMP),
    ('Pharmacie', 15.80, 'Santé', '2024-01-10', 'Médicaments', 1, CURRENT_TIMESTAMP),
    ('Coiffeur', 35.00, 'Soins personnels', '2024-01-09', 'Coupe + brushing', 2, CURRENT_TIMESTAMP),
    ('Livre Amazon', 18.90, 'Culture', '2024-01-08', 'Roman policier', 2, CURRENT_TIMESTAMP);