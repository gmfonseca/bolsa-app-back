package br.com.gmfonseca.bolsaapp.exceptions;

public class AssetNotFoundException extends Exception {

    public AssetNotFoundException(String codigo){
        super("Nao foi encontrado um ativo com o codigo '" + codigo + "'");
    }

}
