package br.com.gmfonseca.bolsaapp.exceptions;

public class TransactionNotFoundException extends Exception {

    public TransactionNotFoundException() {
        super("Não foi encontrada nenhuma transação correspondente.");
    }
}
