package com.yoann.echeancier.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entité représentant un utilisateur du système
 */
@Entity
@Table(name = "utilisateurs")
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom", nullable = false, length = 100)
    @NotBlank(message = "Le nom ne peut pas être vide")
    @Size(max = 100, message = "Le nom ne peut pas dépasser 100 caractères")
    private String nom;

    @Column(name = "couleur", nullable = false, length = 50)
    @NotBlank(message = "La couleur ne peut pas être vide")
    private String couleur;

    @Column(name = "date_creation", nullable = false)
    private LocalDateTime dateCreation;

    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Depense> depenses = new ArrayList<>();

    // Constructeurs
    public Utilisateur() {
        this.dateCreation = LocalDateTime.now();
    }

    public Utilisateur(String nom, String couleur) {
        this();
        this.nom = nom;
        this.couleur = couleur;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public List<Depense> getDepenses() {
        return depenses;
    }

    public void setDepenses(List<Depense> depenses) {
        this.depenses = depenses;
    }

    // Méthodes utilitaires
    public void ajouterDepense(Depense depense) {
        depenses.add(depense);
        depense.setUtilisateur(this);
    }

    public void supprimerDepense(Depense depense) {
        depenses.remove(depense);
        depense.setUtilisateur(null);
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", couleur='" + couleur + '\'' +
                ", dateCreation=" + dateCreation +
                '}';
    }
}