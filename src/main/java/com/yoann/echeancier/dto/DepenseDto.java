package com.yoann.echeancier.dto;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class DepenseDto {
    // Getters et Setters
    private Long id;

    @NotBlank(message = "La description est obligatoire")
    private String description;

    @NotNull(message = "Le montant est obligatoire")
    @DecimalMin(value = "0.01", message = "Le montant doit être supérieur à 0")
    private BigDecimal montant;

    @NotNull(message = "La date de dépense est obligatoire")
    private LocalDate dateDepense;

    private String commentaires;

    // Ajout de l'utilisateurId
    private Long utilisateurId;

    private LocalDateTime dateCreation;

    // Constructeurs
    public DepenseDto() {}

    public DepenseDto(String description, BigDecimal montant, String categorie, LocalDate dateDepense) {
        this.description = description;
        this.montant = montant;
        this.dateDepense = dateDepense;
    }

}
