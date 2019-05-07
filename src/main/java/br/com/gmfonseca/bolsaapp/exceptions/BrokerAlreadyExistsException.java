package br.com.gmfonseca.bolsaapp.exceptions;

public class BrokerAlreadyExistsException extends Exception{

    public BrokerAlreadyExistsException(String nome) {
        super("Ja existe uma Corretora cadastrada com o nome '" + nome + "'");
    }
}
