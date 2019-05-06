package br.com.gmfonseca.bolsaapp.controllers;

import br.com.gmfonseca.bolsaapp.exceptions.*;
import br.com.gmfonseca.bolsaapp.models.Ativo;
import br.com.gmfonseca.bolsaapp.models.Corretora;
import br.com.gmfonseca.bolsaapp.models.Ordem;
import br.com.gmfonseca.bolsaapp.util.OrdemType;

import javax.persistence.EntityManager;
import java.util.List;

public class OrdensController {

    private EntityManager entityManager;

    public OrdensController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Método para recuperar todas as ordens cadastradas no banco de dados
     *
     * @return Lista de ordens encontradas
     */
    public List<Ordem> getOrdens(){
        return entityManager.createQuery("from Ordem", Ordem.class).getResultList();
    }

    /**
     * Método para recuperar uma ordem especifica cadastrada no banco de dados
     *
     * @param ordemId id da ordem que se deseja buscar
     *
     * @throws OrderNotFoundException caso a ordem nao seja encontrada,
     *
     * @return Ordem encontrada
     */
    public Ordem getOrdem(int ordemId) throws OrderNotFoundException{
        Ordem ordem = entityManager.find(Ordem.class, ordemId);

        if(ordem == null) throw new OrderNotFoundException();

        return ordem;
    }

    /**
     * Método para criar um ativo
     *
     * @param operacao Tipo de operacao da ordem
     * @param quantidade Quantidade de acoes do Ativo envolvido na Ordem
     * @param valor Valor da ordem de ativos
     * @param ativo Ativo envolvido na ordem
     * @param corretora Corretora responsavel por disparar a Ordem
     *
     * @throws AssetNotFoundException caso o ativo nao seja encontrado
     * @throws BrokerNotFoundException caso a corretora nao seja encontrada
     *
     * @return Ativo criado
     */
    public Ordem createOrdem(OrdemType operacao, int quantidade, double valor, Ativo ativo, Corretora corretora) throws AssetNotFoundException, BrokerNotFoundException {
        if (ativo == null) throw new AssetNotFoundException();
        if (corretora == null) throw new BrokerNotFoundException();

        Ordem ordem = new Ordem(operacao, quantidade, valor, ativo, corretora);

        entityManager.getTransaction().begin();
        entityManager.persist(ordem);
        entityManager.getTransaction().commit();

        return ordem;
    }
    public Ordem createOrdem(int operacaoId, int quantidade, double valor, String ativoId, int corretoraId)
            throws NotFilledRequiredFieldsException, NotCorrectFieldLengthException, AssetNotFoundException, InvalidOrderTypeValueException, BrokerNotFoundException {

        AtivosController ativosController = new AtivosController(entityManager);

        Ativo ativo = ativosController.getAtivo(ativoId);
        Corretora corretora = entityManager.find(Corretora.class, corretoraId);

        return createOrdem(OrdemType.valueOf(operacaoId), quantidade, valor, ativo, corretora);
    }

    /**
     * Método para deletar uma ordem especifica do banco de dados
     *
     * @param orderId id da ordem que sera deletada
     *
     * @throws OrderNotFoundException caso a ordem nao seja encontrada
     *
     * @return Ordem deletada
     */
    public Ordem deleteOrdem(int orderId)
            throws OrderNotFoundException {

        Ordem ordem = getOrdem(orderId);

        entityManager.getTransaction().begin();
        entityManager.remove(ordem);
        entityManager.getTransaction().commit();

        return ordem;
    }

}
