package br.com.gmfonseca.bolsaapp.exceptions;

public class WrongBuyOrderTypeException extends Exception {

    public WrongBuyOrderTypeException() {
        super("A ordem informada deve ser do tipo COMPRA (1).");
    }
}
