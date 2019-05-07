package br.com.gmfonseca.bolsaapp.exceptions;

public class BrokerNotFoundException extends Exception {

    public BrokerNotFoundException() {
        super("Não foi encontrada nenhuma corretora correspondente.");
    }
}
