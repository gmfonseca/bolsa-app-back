package br.com.gmfonseca.bolsaapp.controllers;

import br.com.gmfonseca.bolsaapp.exceptions.*;
import br.com.gmfonseca.bolsaapp.models.Ativo;
import br.com.gmfonseca.bolsaapp.models.Corretora;
import br.com.gmfonseca.bolsaapp.models.Ordem;
import br.com.gmfonseca.bolsaapp.models.Transacao;
import br.com.gmfonseca.bolsaapp.util.OrdemType;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.*;

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
     * Método para recuperar todas as ordens cadastradas no banco de dados
     * com um tipo específico de operação
     *
     * @param type Tipo de operacao da ordem
     *
     * @return Lista de ordens encontradas
     */
    public List<Ordem> getOrdens(OrdemType type) throws OrderNotFoundException{

        if(!hasOrder(type)) throw new OrderNotFoundException();

        return entityManager.createQuery("from Ordem o where o.operacao = :ordemType", Ordem.class)
        .setParameter("ordemType", type).getResultList();
    }
    private boolean hasOrder(OrdemType type){
        try{
            List<Ordem> ordens = entityManager.createQuery("from Ordem o where o.operacao = :ordemType", Ordem.class)
                    .setParameter("ordemType", type).getResultList();

            return ordens != null;
        }catch (NoResultException e){
            return false;
        }
    }

    /**
     * Método para recuperar todas as ordens cadastradas no banco de dados
     * com um tipo específico de operação
     *
     * @param date Data de realização da ordem (dd:MM:yyyy-hh:mm)
     *
     *
     * @return Lista de ordens encontradas
     */
    public List<Ordem> getOrdens(String date) {
        List<Ordem> ordens = new ArrayList<>();

        if(date != null) {
            String[] dados = date.split("-");
            String data = dados[0].replaceAll(":", "/") + " - " + dados[1];

            List<Ordem> getted = getOrdens();

            if (getted != null && getted.size()>0)
                for (Ordem o : getted) {
                    if (o.getData().equalsIgnoreCase(data)) ordens.add(o);
                }

        }
        return ordens;
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
     * Método para criar uma Ordem
     *
     * @param operacao Tipo de operacao da ordem
     * @param quantidade Quantidade de acoes do Ativo envolvido na ordem
     * @param valor Valor da ordem de ativos
     * @param ativo Ativo envolvido na ordem
     * @param corretora Corretora responsavel por disparar a ordem
     *
     * @throws AssetNotFoundException caso o ativo nao seja encontrado
     * @throws BrokerNotFoundException caso a corretora nao seja encontrada
     *
     * @return Ativo criado
     */
    public Ordem createOrdem(OrdemType operacao, int quantidade, double valor, Ativo ativo, Corretora corretora)
            throws BrokerNotFoundException, AssetNotFoundException, OrderNotFoundException, WrongSellOrderTypeException, WrongBuyOrderTypeException {
        if (ativo == null) throw new AssetNotFoundException();
        if (corretora == null) throw new BrokerNotFoundException();
        Ordem ordem = new Ordem(operacao, quantidade, valor, ativo, corretora);
        OrdemType op = (operacao == OrdemType.COMPRA ? OrdemType.VENDA : OrdemType.COMPRA);

        List<Ordem> ordens = entityManager.createQuery("FROM Ordem o WHERE o.operacao = :operacao AND o.used = false " +
                "AND o.ativo = :ativo AND o.quantidade = :quantidade AND o.valor = :valor", Ordem.class)
                .setParameter("operacao", op).setParameter("ativo", ativo).setParameter("quantidade", quantidade).setParameter("valor", valor).getResultList();

        if(ordens.size()>0){
            ordens.sort(Comparator.comparing(Ordem::getId));
            Ordem o = ordens.get(0);
            o.setUsed(true);
            ordem.setUsed(true);

            entityManager.getTransaction().begin();
            entityManager.persist(ordem);
            entityManager.persist(o);
            entityManager.getTransaction().commit();

            if(operacao == OrdemType.COMPRA) {
                new TransacoesController(entityManager).createTransacao(ativo, quantidade, valor, o, ordem);
            }else{
                new TransacoesController(entityManager).createTransacao(ativo, quantidade, valor, ordem, o);
            }
        }else {
            entityManager.getTransaction().begin();
            entityManager.persist(ordem);
            entityManager.getTransaction().commit();
        }

        return ordem;
    }
    public Ordem createOrdem(OrdemType operacaoId, int quantidade, double valor, String ativoId, String corretoraNome)
            throws NotFilledRequiredFieldsException, NotCorrectFieldLengthException, BrokerAlreadyExistsException, BrokerNotFoundException, AssetNotFoundException, OrderNotFoundException, WrongSellOrderTypeException, WrongBuyOrderTypeException {
        CorretorasController controllerCorretora = new CorretorasController(entityManager);

        Ativo ativo = new AtivosController(entityManager).getAtivo(ativoId);
        if(!existsCorretora(corretoraNome)) controllerCorretora.createCorretora(corretoraNome);

        Corretora corretora = new CorretorasController(entityManager).getCorretora(corretoraNome);

        return createOrdem(operacaoId, quantidade, valor, ativo, corretora);
    }

    private boolean existsCorretora(String nome){
        try{
            Corretora corretora = entityManager.createQuery("FROM Corretora c WHERE c.nome = :nome", Corretora.class).setParameter("nome", nome).getSingleResult();

            return corretora != null;
        }catch (NoResultException e){
            return false;
        }
    }

    /**
     * Método para deletar uma ordem especifica do banco de dados
     *
     * @param ordem ordem que sera deletada
     *
     * @throws OrderNotFoundException caso a ordem nao seja encontrada
     *
     * @return Ordem deletada
     */
    public Ordem deleteOrdem(Ordem ordem)
            throws OrderNotFoundException, TransactionNotFoundException {
        if(ordem == null) throw new OrderNotFoundException();

        if(transationsUsesBroker(ordem)){
            List<Transacao> transacoes = entityManager.createQuery("FROM Transacao t WHERE t.venda = :ordem OR t.compra = :ordem", Transacao.class)
                                                      .setParameter("ordem", ordem).getResultList();

            TransacoesController transacoesController = new TransacoesController(entityManager);

            for (int i = transacoes.size() - 1; i >= 0; i--) {
                transacoesController.deleteTransacao(transacoes.get(i).getId());
            }
        }

        if(!entityManager.isOpen()) entityManager.getTransaction().begin();
        entityManager.remove(ordem);
        entityManager.getTransaction().commit();

        return ordem;
    }
    public Ordem deleteOrdem(int orderId)
            throws OrderNotFoundException, TransactionNotFoundException {

        Ordem ordem = getOrdem(orderId);

        return deleteOrdem(ordem);
    }

    private boolean transationsUsesBroker(Ordem ordem){
        try{
            List<Transacao> transacoes = entityManager.createQuery("from Transacao t where t.venda = :ordem OR t.compra = :ordem", Transacao.class)
                                                      .setParameter("ordem", ordem).getResultList();

            return transacoes != null;
        }catch (NoResultException e){
            return false;
        }
    }

}
