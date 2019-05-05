package br.com.gmfonseca.bolsaapp.models;

import javax.persistence.*;

@Entity
@Table(name = "broker")
public class Broker {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int id;

    public Broker() {}

}
