package br.com.gmfonseca.bolsaapp.exceptions;

public class AssetAlreadyExistsException extends Exception{

    public AssetAlreadyExistsException(String codigo) {
        super("Ja existe um Ativo cadastrado com o codigo '" + codigo + "'");
    }
}

