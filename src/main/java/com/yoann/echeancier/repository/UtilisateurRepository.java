package com.yoann.echeancier.repository;

import com.yoann.echeancier.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    Optional<Utilisateur> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<Utilisateur> findByNomUtilisateur(String nomUtilisateur);

    boolean existsByNomUtilisateur(String nomUtilisateur);
}