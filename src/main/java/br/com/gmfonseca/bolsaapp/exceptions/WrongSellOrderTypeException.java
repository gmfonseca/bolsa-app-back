package br.com.gmfonseca.bolsaapp.exceptions;

public class WrongSellOrderTypeException extends Exception {

    public WrongSellOrderTypeException() {
        super("A ordem informada deve ser do tipo VENDA (0).");
    }

}
