package com.yoann.echeancier.repository;

import com.yoann.echeancier.model.Depense;
import com.yoann.echeancier.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface DepenseRepository extends JpaRepository<Depense, Long> {

    List<Depense> findByUtilisateurOrderByDateDepenseDesc(Utilisateur utilisateur);

    List<Depense> findByUtilisateurAndDateDepenseBetween(
            Utilisateur utilisateur,
            LocalDate dateDebut,
            LocalDate dateFin
    );

    List<Depense> findByUtilisateurAndCategorie(Utilisateur utilisateur, String categorie);

    @Query("SELECT SUM(d.montant) FROM Depense d WHERE d.utilisateur = :utilisateur")
    BigDecimal getTotalDepensesByUtilisateur(@Param("utilisateur") Utilisateur utilisateur);

    @Query("SELECT SUM(d.montant) FROM Depense d WHERE d.utilisateur = :utilisateur AND d.dateDepense BETWEEN :dateDebut AND :dateFin")
    BigDecimal getTotalDepensesByUtilisateurEtPeriode(
            @Param("utilisateur") Utilisateur utilisateur,
            @Param("dateDebut") LocalDate dateDebut,
            @Param("dateFin") LocalDate dateFin
    );

    @Query("SELECT d.categorie, SUM(d.montant) FROM Depense d WHERE d.utilisateur = :utilisateur GROUP BY d.categorie")
    List<Object[]> getDepensesParCategorie(@Param("utilisateur") Utilisateur utilisateur);

    @Query("SELECT COUNT(d) FROM Depense d WHERE d.utilisateur = :utilisateur")
    Long countDepensesByUtilisateur(@Param("utilisateur") Utilisateur utilisateur);
}