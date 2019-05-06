package br.com.gmfonseca.bolsaapp.exceptions;

public class InvalidOrderTypeValueException extends Exception {

    public InvalidOrderTypeValueException() {
        super("Insira um valor valido de tipo de ordem. (0 ou 1)");
    }
}
