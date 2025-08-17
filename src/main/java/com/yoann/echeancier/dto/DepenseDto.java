package com.yoann.echeancier.dto;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DepenseDto {
    private Long id;

    @NotBlank(message = "La description est obligatoire")
    private String description;

    @NotNull(message = "Le montant est obligatoire")
    @DecimalMin(value = "0.01", message = "Le montant doit être supérieur à 0")
    private BigDecimal montant;

    @NotBlank(message = "La catégorie est obligatoire")
    private String categorie;

    @NotNull(message = "La date de dépense est obligatoire")
    private LocalDate dateDepense;

    private String commentaires;

    private Long utilisateurId;

    // Constructeurs
    public DepenseDto() {}

    public DepenseDto(String description, BigDecimal montant, String categorie, LocalDate dateDepense) {
        this.description = description;
        this.montant = montant;
        this.categorie = categorie;
        this.dateDepense = dateDepense;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getMontant() { return montant; }
    public void setMontant(BigDecimal montant) { this.montant = montant; }

    public String getCategorie() { return categorie; }
    public void setCategorie(String categorie) { this.categorie = categorie; }

    public LocalDate getDateDepense() { return dateDepense; }
    public void setDateDepense(LocalDate dateDepense) { this.dateDepense = dateDepense; }

    public String getCommentaires() { return commentaires; }
    public void setCommentaires(String commentaires) { this.commentaires = commentaires; }

    public Long getUtilisateurId() { return utilisateurId; }
    public void setUtilisateurId(Long utilisateurId) { this.utilisateurId = utilisateurId; }
}
