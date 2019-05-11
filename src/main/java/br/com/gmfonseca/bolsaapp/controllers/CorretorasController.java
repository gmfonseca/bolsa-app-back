package br.com.gmfonseca.bolsaapp.controllers;

import br.com.gmfonseca.bolsaapp.exceptions.*;
import br.com.gmfonseca.bolsaapp.models.Corretora;
import br.com.gmfonseca.bolsaapp.models.Ordem;
import br.com.gmfonseca.bolsaapp.util.Util;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

public class CorretorasController {

    private EntityManager entityManager;

    public CorretorasController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Método para recuperar todas as corretoras cadastradas no banco de dados
     *
     * @return Lista de corretoras encontradas
     */
    public List<Corretora> getCorretoras(){
        return entityManager.createQuery("from Corretora", Corretora.class).getResultList();
    }

    /**
     * Método para recuperar uma corretora especifica cadastrada no banco de dados
     *
     * @param corretoraId Codigo da corretora que se deseja buscar
     *
     * @throws BrokerNotFoundException caso a Corretora nao seja encontrada
     *
     * @return Corretora encontrado
     */
    public Corretora getCorretora(int corretoraId) throws BrokerNotFoundException {
        Corretora corretora = entityManager.find(Corretora.class, corretoraId);

        if(corretora == null) throw new BrokerNotFoundException();

        return corretora;
    }
    public Corretora getCorretora(String nome) throws BrokerNotFoundException {
        Corretora corretora = entityManager.createQuery("FROM Corretora c WHERE c.nome = :nome", Corretora.class).setParameter("nome", nome).getSingleResult();

        if(corretora == null) throw new BrokerNotFoundException();

        return corretora;
    }

    /**
     * Método para criar uma corretora
     *
     * @param nome Nome da corretora
     *
     * @throws NotFilledRequiredFieldsException caso os campos obrigatorios nao sejam preenchidos
     * @throws BrokerAlreadyExistsException caso exista uma Corretora cadastrada com o nome informado
     *
     * @return Corretora criada
     */
    public Corretora createCorretora(String nome) throws BrokerAlreadyExistsException, NotFilledRequiredFieldsException {

        if(Util.fieldIsEmpty(nome)) throw new NotFilledRequiredFieldsException();
        if(nameIsInUse(nome)) throw new BrokerAlreadyExistsException(nome);

        Corretora corretora = new Corretora(nome);

        entityManager.getTransaction().begin();
        entityManager.persist(corretora);
        entityManager.getTransaction().commit();

        return corretora;
    }
    private boolean nameIsInUse(String nome){

        try{
            Corretora corretora = entityManager.createQuery("from Corretora c where c.nome = :nome", Corretora.class).setParameter("nome", nome).getSingleResult();

            return corretora != null;
        }catch (NoResultException e){
            return false;
        }

    }

    /**
     * Método para renomear uma correota especifica cadastrada no banco de dados
     *
     * @param nome Novo nome da empresa do ativo
     * @param corretoraId id de referencia da corretora (inalteravel)
     *
     * @throws NotFilledRequiredFieldsException caso os campos obrigatorios nao sejam preenchidos
     * @throws BrokerNotFoundException caso a Corretora nao seja encontrada
     * @throws BrokerNotUpdatedException caso o Ativo nao tenha sido atualizado
     *
     * @return Corretora renomeada
     */
    public Corretora renameCorretora(int corretoraId, String nome) throws NotFilledRequiredFieldsException, BrokerNotFoundException , BrokerNotUpdatedException {

        if(Util.fieldIsEmpty(nome)) throw new NotFilledRequiredFieldsException();

        Corretora corretora = getCorretora(corretoraId);

        if(nome.equals(corretora.getNome())) throw new BrokerNotUpdatedException();

        corretora.setNome(nome);

        entityManager.getTransaction().begin();
        entityManager.persist(corretora);
        entityManager.getTransaction().commit();

        return corretora;
    }

    /**
     * Método para deletar uma corretora especifica do banco de dados
     *
     * @param corretoraId Id da corretora que se deseja deletar
     *
     * @throws BrokerNotFoundException caso a Corretora nao seja encontrada
     *
     * @return Corretora deletada
     */
    public Corretora deleteCorretora(int corretoraId) throws BrokerNotFoundException, OrderNotFoundException, TransactionNotFoundException {
        Corretora corretora = getCorretora(corretoraId);

        if(ordersUsesBroker(corretora)) {
            List<Ordem> ordens = entityManager.createQuery("FROM Ordem c WHERE c.corretora = :corretora", Ordem.class).setParameter("corretora", corretora).getResultList();

            OrdensController ordensController = new OrdensController(entityManager);

            for (int i = ordens.size() - 1; i >= 0; i--) {
                ordensController.deleteOrdem(ordens.get(i));
            }
        }

        entityManager.getTransaction().begin();
        entityManager.remove(corretora);
        entityManager.getTransaction().commit();

        return corretora;
    }
    private boolean ordersUsesBroker(Corretora corretora){
        try{
            List<Ordem> ordem = entityManager.createQuery("from Ordem o where o.corretora = :corretora", Ordem.class)
                                       .setParameter("corretora", corretora).getResultList();

            return ordem  != null;
        }catch (NoResultException e){
            return false;
        }
    }

}
