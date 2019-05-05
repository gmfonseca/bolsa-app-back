package br.com.gmfonseca.bolsaapp.controllers;

import javax.persistence.EntityManager;

public class AtivosController {

    private EntityManager entityManager;

    public AtivosController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
