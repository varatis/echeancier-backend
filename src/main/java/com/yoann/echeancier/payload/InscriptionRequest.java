package com.yoann.echeancier.payload;

import lombok.Data;

@Data
public class InscriptionRequest {
    private String nom;
    private String email;
    private String motDePasse;
}
