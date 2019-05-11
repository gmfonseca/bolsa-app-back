package br.com.gmfonseca.bolsaapp.controllers;

import br.com.gmfonseca.bolsaapp.exceptions.*;
import br.com.gmfonseca.bolsaapp.models.Ativo;
import br.com.gmfonseca.bolsaapp.models.Ordem;
import br.com.gmfonseca.bolsaapp.models.Transacao;
import br.com.gmfonseca.bolsaapp.util.OrdemType;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.*;

public class TransacoesController {

    private EntityManager entityManager;

    public TransacoesController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Método para recuperar todas as transacoes cadastradas no banco de dados
     *
     * @return Lista de transacoes encontradas
     */
    public List<Transacao> getTransacoes(){
        List<Transacao> transacoes = entityManager.createQuery("from Transacao", Transacao.class).getResultList();
        transacoes.sort(Comparator.comparing(Transacao::getId));
        return transacoes;
    }

    /**
     * Método para recuperar todas as ordens cadastradas no banco de dados
     * com um tipo específico de operação
     *
     * @param date Data de realização da transacao (dd:MM:yyyy-hh:mm)
     *
     * @return Lista de ordens encontradas
     */
    public List<Transacao> getTransacoes(String date) {
        List<Transacao> transacoes = new ArrayList<>();

        if(date != null) {
            String[] dados = date.split("-");
            String data = dados[0].replaceAll(":", "/") + " - " + dados[1];

            List<Transacao> getted = getTransacoes();

            if (getted != null)
                for (Transacao t : getted) {
                    if (t.getData().equalsIgnoreCase(data)) transacoes.add(t);
                }

        }
        return transacoes;
    }

    /**
     * Método para recuperar uma transacao especifica cadastrada no banco de dados
     *
     * @param transacaoId id da transacao que se deseja buscar
     *
     * @throws TransactionNotFoundException caso a transacao nao seja encontrada,
     *
     * @return Transacao encontrada
     */
    public Transacao getTransacao(int transacaoId) throws TransactionNotFoundException{
        Transacao transacao = entityManager.find(Transacao.class, transacaoId);

        if(transacao == null) throw new TransactionNotFoundException();

        return transacao;
    }

    /**
     * Método para criar uma Transacao
     *
     * @param ativo Ativo envolvido na transacao
     * @param quantidade Quantidade de acoes do ativo envolvido na transacao
     * @param valor Valor da ordem de ativos
     * @param venda Ordem de venda responsavel pela transacao
     * @param compra Ordem de compra responsavel pela transacao
     *
     * @throws AssetNotFoundException caso o ativo nao seja encontrado
     * @throws OrderNotFoundException caso a ordem (de venda ou de compra) nao seja encontrada
     * @throws WrongSellOrderTypeException caso o tipo de operacao de venda nao seja correto
     * @throws WrongBuyOrderTypeException caso o tipo de operacao de compra nao esteja correto
     *
     * @return Transacao criada
     */
    protected Transacao createTransacao(Ativo ativo, int quantidade, double valor, Ordem venda, Ordem compra)
            throws AssetNotFoundException, OrderNotFoundException, WrongSellOrderTypeException, WrongBuyOrderTypeException {
        if (ativo == null) throw new AssetNotFoundException();
        if (venda == null || compra == null) throw new OrderNotFoundException();
        if(venda.getOperacao() != OrdemType.VENDA) throw new WrongSellOrderTypeException();
        if(compra.getOperacao() != OrdemType.COMPRA) throw new WrongBuyOrderTypeException();

        Transacao transacao = new Transacao(ativo, quantidade, valor, venda, compra);

        entityManager.getTransaction().begin();
        entityManager.persist(transacao);
        entityManager.getTransaction().commit();

        return transacao;
    }

    /**
     * Método para criar uma Transacao
     *
     * @param ativoId id do Ativo envolvido na transacao
     * @param quantidade Quantidade de acoes do ativo envolvido na transacao
     * @param valor Valor da ordem de ativos
     * @param vendaId id da Ordem de venda responsavel pela transacao
     * @param compraId id da Ordem de compra responsavel pela transacao
     *
     * @throws AssetNotFoundException caso o ativo nao seja encontrado
     * @throws OrderNotFoundException caso a ordem (de venda ou de compra) nao seja encontrada
     * @throws WrongSellOrderTypeException caso o tipo de operacao de venda nao seja correto
     * @throws WrongBuyOrderTypeException caso o tipo de operacao de compra nao esteja correto
     *
     * @return Transacao criada
     */
    public Transacao createTransacao(String ativoId, int quantidade, double valor, int vendaId, int compraId)
            throws NotFilledRequiredFieldsException, NotCorrectFieldLengthException, AssetNotFoundException, OrderNotFoundException, WrongSellOrderTypeException, WrongBuyOrderTypeException {
        OrdensController ordensController = new OrdensController(entityManager);

        Ativo ativo = new AtivosController(entityManager).getAtivo(ativoId);
        Ordem venda = ordensController.getOrdem(vendaId);
        Ordem compra = ordensController.getOrdem(compraId);

        return createTransacao(ativo, quantidade, valor, venda, compra);
    }

    /**
     * Método para deletar uma transacao especifica do banco de dados
     *
     * @param transacao transacao que sera deletada
     *
     * @throws TransactionNotFoundException caso a transacao nao seja encontrada
     *
     * @return Transacao deletada
     */
    public Transacao deleteTransacao(Transacao transacao)
            throws TransactionNotFoundException {

        if(transacao == null) throw new TransactionNotFoundException();

        if(!entityManager.isOpen()) entityManager.getTransaction().begin();
        entityManager.remove(transacao);
        entityManager.getTransaction().commit();

        return transacao;
    }

    /**
     * Método para deletar uma transacao especifica do banco de dados
     *
     * @param transactionId id da transacao que sera deletada
     *
     * @throws TransactionNotFoundException caso a transacao nao seja encontrada
     *
     * @return Transacao deletada
     */
    public Transacao deleteTransacao(int transactionId)
            throws TransactionNotFoundException {

        Transacao transacao = getTransacao(transactionId);

        return deleteTransacao(transacao);
    }
}
