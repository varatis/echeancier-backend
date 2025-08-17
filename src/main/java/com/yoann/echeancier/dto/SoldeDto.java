package com.yoann.echeancier.dto;

import java.math.BigDecimal;
import java.util.Map;

public class SoldeDto {
    private BigDecimal totalDepenses;
    private Long nombreDepenses;
    private BigDecimal depenseMoyenne;
    private Map<String, BigDecimal> depensesParCategorie;

    // Constructeurs
    public SoldeDto() {}

    public SoldeDto(BigDecimal totalDepenses, Long nombreDepenses, BigDecimal depenseMoyenne) {
        this.totalDepenses = totalDepenses;
        this.nombreDepenses = nombreDepenses;
        this.depenseMoyenne = depenseMoyenne;
    }

    // Getters et Setters
    public BigDecimal getTotalDepenses() { return totalDepenses; }
    public void setTotalDepenses(BigDecimal totalDepenses) { this.totalDepenses = totalDepenses; }

    public Long getNombreDepenses() { return nombreDepenses; }
    public void setNombreDepenses(Long nombreDepenses) { this.nombreDepenses = nombreDepenses; }

    public BigDecimal getDepenseMoyenne() { return depenseMoyenne; }
    public void setDepenseMoyenne(BigDecimal depenseMoyenne) { this.depenseMoyenne = depenseMoyenne; }

    public Map<String, BigDecimal> getDepensesParCategorie() { return depensesParCategorie; }
    public void setDepensesParCategorie(Map<String, BigDecimal> depensesParCategorie) {
        this.depensesParCategorie = depensesParCategorie;
    }
}
