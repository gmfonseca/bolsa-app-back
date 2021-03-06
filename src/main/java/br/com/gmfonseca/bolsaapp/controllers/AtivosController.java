package br.com.gmfonseca.bolsaapp.controllers;

import br.com.gmfonseca.bolsaapp.exceptions.*;
import br.com.gmfonseca.bolsaapp.models.Ativo;
import br.com.gmfonseca.bolsaapp.models.Corretora;
import br.com.gmfonseca.bolsaapp.models.Ordem;
import br.com.gmfonseca.bolsaapp.models.Transacao;
import br.com.gmfonseca.bolsaapp.util.Util;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

public class AtivosController {

    private EntityManager entityManager;

    public AtivosController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Método para recuperar todos os ativos cadastrados no banco de dados
     *
     * @return Lista de ativos encontrados
     */
    public List<Ativo> getAtivos(){
        return entityManager.createQuery("from Ativo", Ativo.class).getResultList();
    }

    /**
     * Método para recuperar um ativo especifico cadastrado no banco de dados
     *
     * @param codigo Codigo do ativo que se deseja buscar
     *
     * @throws NotFilledRequiredFieldsException caso os campos obrigatorios nao sejam preenchidos
     * @throws NotCorrectFieldLengthException caso o codigo do Ativo nao tenha comprimento 5
     * @throws AssetNotFoundException caso o Ativo nao seja encontrado
     *
     * @return Ativo encontrado
     */
    public Ativo getAtivo(String codigo) throws NotFilledRequiredFieldsException, NotCorrectFieldLengthException, AssetNotFoundException {

        if(Util.fieldIsEmpty(codigo)) throw new NotFilledRequiredFieldsException();
        if(codigo.length() != 5) throw new NotCorrectFieldLengthException("Ativo:codigo/id", 5);

        Ativo ativo = entityManager.find(Ativo.class, codigo.toUpperCase());

        if(ativo == null) throw new AssetNotFoundException();

        return ativo;
    }

    /**
     * Método para criar um ativo
     *
     * @param nome Nome da empresa do ativo
     * @param codigo Codigo de referencia do ativo
     * @param descricao Descricao da empresa do ativo
     *
     * @throws NotFilledRequiredFieldsException caso os campos obrigatorios nao sejam preenchidos
     * @throws NotCorrectFieldLengthException caso o codigo do Ativo nao tenha comprimento 5
     * @throws AssetAlreadyExistsException caso exista um Ativo cadastrado com o codigo informado
     *
     * @return Ativo criado
     */
    public Ativo createAtivo(String nome, String codigo, String descricao) throws NotFilledRequiredFieldsException, NotCorrectFieldLengthException, AssetAlreadyExistsException {

        if(Util.fieldIsEmpty(nome) || Util.fieldIsEmpty(codigo) || Util.fieldIsEmpty(descricao)) throw new NotFilledRequiredFieldsException();
        if(codigo.length() != 5) throw new NotCorrectFieldLengthException("codigo", 5);

        Ativo ativo = entityManager.find(Ativo.class, codigo);
        if(ativo != null) throw new AssetAlreadyExistsException(codigo);
        if(descricao.length()>100) descricao = descricao.substring(0, 97)+"...";

        ativo = new Ativo(nome, codigo, descricao);

        entityManager.getTransaction().begin();
        entityManager.persist(ativo);
        entityManager.getTransaction().commit();

        return ativo;
    }

    /**
     * Método para atualizar um ativo especifico cadastrado no banco de dados
     *
     * @param nome Novo nome da empresa do ativo
     * @param codigo Codigo de referencia do ativo (inalteravel)
     * @param descricao Nova descricao da empresa do ativo
     *
     * @throws NotFilledRequiredFieldsException caso os campos obrigatorios nao sejam preenchidos
     * @throws NotCorrectFieldLengthException caso o codigo do Ativo nao tenha comprimento 5
     * @throws AssetNotFoundException caso o Ativo nao seja encontrado
     * @throws AssetNotUpdatedException caso o Ativo nao tenha sido atualizado
     *
     * @return Ativo atualizado
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
     * Método para deletar um ativo especifico do banco de dados
     *
     * @param codigo Codigo do ativo que se deseja deletar
     *
     * @throws NotFilledRequiredFieldsException caso os campos obrigatorios nao sejam preenchidos
     * @throws NotCorrectFieldLengthException caso o codigo do Ativo nao tenha comprimento 5
     * @throws AssetNotFoundException caso o Ativo nao seja encontrado
     *
     * @return Ativo deletado
     */
    public Ativo deleteAtivo(String codigo) throws NotFilledRequiredFieldsException, NotCorrectFieldLengthException, AssetNotFoundException, OrderNotFoundException, TransactionNotFoundException {
        Ativo ativo = getAtivo(codigo);

        if(transationsUsesAsset(ativo)){
            List<Transacao> transacoes = entityManager.createQuery("from Transacao t where t.ativo = :ativo", Transacao.class).setParameter("ativo", ativo).getResultList();
            TransacoesController transacoesController = new TransacoesController(entityManager);

            for (int i = transacoes.size() - 1; i >= 0; i--) {
                transacoesController.deleteTransacao(transacoes.get(i).getId());
            }
        }
        if(ordersUsesAsset(ativo)) {
            List<Ordem> ordens = entityManager.createQuery("from Ordem o where o.ativo = :ativo", Ordem.class).setParameter("ativo", ativo).getResultList();
            OrdensController ordensController = new OrdensController(entityManager);

            for (int i = ordens.size() - 1; i >= 0; i--) {
                ordensController.deleteOrdem(ordens.get(i));
            }
        }

        entityManager.getTransaction().begin();
        entityManager.remove(ativo);
        entityManager.getTransaction().commit();

        return ativo;
    }
    private boolean ordersUsesAsset(Ativo ativo){
        try{
            List<Ordem>  ordem = entityManager.createQuery("from Ordem o where o.ativo = :ativo", Ordem.class)
                    .setParameter("ativo", ativo).getResultList();

            return ordem  != null;
        }catch (NoResultException e){
            return false;
        }
    }
    private boolean transationsUsesAsset(Ativo ativo){
        try{
            List<Transacao> transacaoes = entityManager.createQuery("from Transacao t where t.ativo = :ativo", Transacao.class)
                    .setParameter("ativo", ativo).getResultList();

            return transacaoes  != null;
        }catch (NoResultException e){
            return false;
        }
    }

}
