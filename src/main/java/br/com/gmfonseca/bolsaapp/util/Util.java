package br.com.gmfonseca.bolsaapp.util;

public class Util {

    public static boolean fieldIsEmpty(String fieldData){

        return (fieldData == null || fieldData.isEmpty() || fieldData.equals("\"\""));
    }

}
