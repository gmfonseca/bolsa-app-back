package br.com.gmfonseca.bolsaapp.util;

import br.com.gmfonseca.bolsaapp.exceptions.InvalidOrderTypeValueException;

public enum OrdemType {

    VENDA((byte) 0), COMPRA((byte) 1);

    private byte id;

    OrdemType(byte id) {
        this.id = id;
    }

    public byte getId() {
        return id;
    }

    public static OrdemType valueOf(int id) throws InvalidOrderTypeValueException {

        if(id != 0 && id != 1) throw new InvalidOrderTypeValueException();

        return id == 0 ? VENDA : COMPRA;
    }
}
