package br.com.gmfonseca.bolsaapp.controllers;

import javax.persistence.EntityManager;

public class CorretorasController {

    private EntityManager entityManager;

    public CorretorasController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
