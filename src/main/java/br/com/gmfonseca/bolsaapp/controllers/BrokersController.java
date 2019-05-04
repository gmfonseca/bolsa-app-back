package br.com.gmfonseca.bolsaapp.controllers;

import javax.persistence.EntityManager;

public class BrokersController {

    private EntityManager entityManager;

    public BrokersController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
