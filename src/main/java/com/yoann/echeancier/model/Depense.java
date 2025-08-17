package com.yoann.echeancier.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entité représentant une dépense
 */
@Entity
@Table(name = "depenses")
public class Depense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;

    @Column(name = "montant", nullable = false, precision = 10, scale = 2)
    @NotNull(message = "Le montant ne peut pas être null")
    @DecimalMin(value = "0.01", message = "Le montant doit être supérieur à 0")
    @Digits(integer = 8, fraction = 2, message = "Le montant ne peut avoir plus de 2 décimales")
    private BigDecimal montant;

    @Column(name = "description", nullable = false, length = 255)
    @NotBlank(message = "La description ne peut pas être vide")
    @Size(max = 255, message = "La description ne peut pas dépasser 255 caractères")
    private String description;

    @Column(name = "date_depense", nullable = false)
    @NotNull(message = "La date de dépense ne peut pas être null")
    private LocalDate dateDepense;

    @Column(name = "date_creation", nullable = false)
    private LocalDateTime dateCreation;

    @Column(name = "date_modification")
    private LocalDateTime dateModification;

    // Constructeurs
    public Depense() {
        this.dateCreation = LocalDateTime.now();
    }

    public Depense(Utilisateur utilisateur, BigDecimal montant, String description, LocalDate dateDepense) {
        this();
        this.utilisateur = utilisateur;
        this.montant = montant;
        this.description = description;
        this.dateDepense = dateDepense;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
        this.dateModification = LocalDateTime.now();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.dateModification = LocalDateTime.now();
    }

    public LocalDate getDateDepense() {
        return dateDepense;
    }

    public void setDateDepense(LocalDate dateDepense) {
        this.dateDepense = dateDepense;
        this.dateModification = LocalDateTime.now();
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDateTime getDateModification() {
        return dateModification;
    }

    public void setDateModification(LocalDateTime dateModification) {
        this.dateModification = dateModification;
    }

    @Override
    public String toString() {
        return "Depense{" +
                "id=" + id +
                ", montant=" + montant +
                ", description='" + description + '\'' +
                ", dateDepense=" + dateDepense +
                ", dateCreation=" + dateCreation +
                '}';
    }
}