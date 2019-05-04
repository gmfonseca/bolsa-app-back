package br.com.gmfonseca.bolsaapp.controllers;

import javax.persistence.EntityManager;

public class BolsasController {

    private EntityManager entityManager;

    public BolsasController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
