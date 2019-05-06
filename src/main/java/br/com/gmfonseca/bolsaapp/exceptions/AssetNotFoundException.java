package br.com.gmfonseca.bolsaapp.exceptions;

public class AssetNotFoundException extends Exception {

    public AssetNotFoundException(){
        super("Não foi encontrado nenhum ativo correspondente.");
    }

}
