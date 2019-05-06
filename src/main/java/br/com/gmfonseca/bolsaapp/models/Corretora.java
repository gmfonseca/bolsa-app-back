package br.com.gmfonseca.bolsaapp.models;

import javax.persistence.*;

@Entity
@Table(name = "corretora")
public class Corretora {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int id;

    public Corretora() {}

}
