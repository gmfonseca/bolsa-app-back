package br.com.gmfonseca.bolsaapp.exceptions;

public class OrderNotFoundException extends Exception {

    public OrderNotFoundException() {
        super("Não foi encontrado nenhuma ordem correspondente.");
    }
}
