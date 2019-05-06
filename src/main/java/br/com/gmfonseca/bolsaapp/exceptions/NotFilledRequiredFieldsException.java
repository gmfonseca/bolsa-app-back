package br.com.gmfonseca.bolsaapp.exceptions;

public class NotFilledRequiredFieldsException extends Exception {

    public NotFilledRequiredFieldsException() {
        super("Todos os campos obrigatorios precisam ser preenchidos.");
    }
}
