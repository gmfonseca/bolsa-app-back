package br.com.gmfonseca.bolsaapp.models;

import javax.persistence.*;

@Entity
@Table(name = "ativo")
public class Ativo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    public Ativo() {}

}
