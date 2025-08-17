package com.yoann.echeancier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EcheancierApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcheancierApplication.class, args);
        System.out.println("🚀 Application Échéancier démarrée avec succès !");
        System.out.println("📋 API disponible sur : http://localhost:8080/api");
        System.out.println("👤 Endpoints utilisateurs : /api/utilisateurs");
        System.out.println("💰 Endpoints dépenses : /api/depenses");
    }

}
