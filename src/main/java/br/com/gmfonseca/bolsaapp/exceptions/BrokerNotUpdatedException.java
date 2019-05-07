package br.com.gmfonseca.bolsaapp.exceptions;

public class BrokerNotUpdatedException extends Exception {

    public BrokerNotUpdatedException() {
        super("A corretora nao foi atualizada, pois o nome passado é o mesmo.");
    }
}
