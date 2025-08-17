package com.yoann.echeancier.model;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "utilisateurs")
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom_utilisateur", unique = true, nullable = false)
    private String nomUtilisateur;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String motDePasse;
}