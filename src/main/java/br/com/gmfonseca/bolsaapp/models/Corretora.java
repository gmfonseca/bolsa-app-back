package br.com.gmfonseca.bolsaapp.models;

import javax.persistence.*;

@Entity
@Table(name = "corretora")
public class Corretora {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int id;

    protected String nome;

    public Corretora(String nome) {
        this.nome = nome;
    }

    public Corretora() {}

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
