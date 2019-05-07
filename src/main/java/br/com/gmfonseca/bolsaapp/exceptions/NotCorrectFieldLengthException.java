package br.com.gmfonseca.bolsaapp.exceptions;

public class NotCorrectFieldLengthException extends Exception {

    public NotCorrectFieldLengthException(String campo, int size) {
        super("O campo '" + campo + "' deve ter " + size + " caractere(s).");
    }
}
