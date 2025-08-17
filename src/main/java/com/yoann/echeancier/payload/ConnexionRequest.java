package com.yoann.echeancier.payload;

import lombok.Data;

@Data
public class ConnexionRequest {
    private String email;
    private String motDePasse;
}
