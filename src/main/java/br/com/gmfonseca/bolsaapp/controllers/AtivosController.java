package br.com.gmfonseca.bolsaapp.controllers;

import br.com.gmfonseca.bolsaapp.exceptions.*;
import br.com.gmfonseca.bolsaapp.models.Ativo;
import br.com.gmfonseca.bolsaapp.util.Util;

import javax.persistence.EntityManager;
import java.util.List;

public class AtivosController {

    private EntityManager entityManager;

    public AtivosController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Método para recuperar todos os ativos cadastrados no banco de dados
     *
     * @return Lista de ativos cadastrados
     */
    public List<Ativo> getAtivos(){
        return entityManager.createQuery("from Ativo", Ativo.class).getResultList();
    }

    /**
     * Método para recuperar um ativo especifico cadastrado no banco de dados
     *
     * @return Ativo cadastrado
     */
    public Ativo getAtivo(String codigo) throws NotFilledRequiredFieldsException, NotCorrectFieldLengthException, AssetNotFoundException {

        if(Util.fieldIsEmpty(codigo)) throw new NotFilledRequiredFieldsException();
        if(codigo.length() != 5) throw new NotCorrectFieldLengthException("codigo", 5);

        Ativo ativo = entityManager.find(Ativo.class, codigo);

        if(ativo == null) throw new AssetNotFoundException(codigo);

        return ativo;
    }

    /**
     *
     *
     */
    public Ativo createAtivo(String nome, String codigo, String descricao) throws NotFilledRequiredFieldsException, NotCorrectFieldLengthException, AssetAlreadyExistsException {

        if(Util.fieldIsEmpty(nome) || Util.fieldIsEmpty(codigo) || Util.fieldIsEmpty(descricao)) throw new NotFilledRequiredFieldsException();
        if(codigo.length() != 5) throw new NotCorrectFieldLengthException("codigo", 5);


        Ativo ativo = entityManager.find(Ativo.class, codigo);
        if(ativo != null) throw new AssetAlreadyExistsException(codigo);

        ativo = new Ativo(nome, codigo, descricao);

        entityManager.getTransaction().begin();
        entityManager.persist(ativo);
        entityManager.getTransaction().commit();

        return ativo;
    }

    /**
     *
     *
     */
    public Ativo updateAtivo(String nome, String codigo, String descricao) throws NotFilledRequiredFieldsException, NotCorrectFieldLengthException, AssetNotFoundException, AssetNotUpdatedException {

        if(Util.fieldIsEmpty(nome) && Util.fieldIsEmpty(descricao)) throw new NotFilledRequiredFieldsException();

        Ativo ativo = getAtivo(codigo);

        if(descricao.equals(ativo.getDescricao()) && nome.equals(ativo.getNome())) throw new AssetNotUpdatedException();

        if(!Util.fieldIsEmpty(nome)) ativo.setNome(nome);
        if(!Util.fieldIsEmpty(descricao)) ativo.setDescricao(descricao);

        entityManager.getTransaction().begin();
        entityManager.persist(ativo);
        entityManager.getTransaction().commit();

        return ativo;
    }

    /**
     *
     *
     */
    public Ativo deleteAtivo(String codigo) throws NotFilledRequiredFieldsException, NotCorrectFieldLengthException, AssetNotFoundException {
        Ativo ativo = getAtivo(codigo);

        entityManager.getTransaction().begin();
        entityManager.remove(ativo);
        entityManager.getTransaction().commit();

        return ativo;
    }



}
