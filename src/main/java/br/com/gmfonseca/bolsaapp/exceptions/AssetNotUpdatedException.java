package br.com.gmfonseca.bolsaapp.exceptions;

public class AssetNotUpdatedException extends Exception {

    public AssetNotUpdatedException() {
        super("O ativo nao foi atualizado, pois os dados passados sao os mesmos.");
    }
}
