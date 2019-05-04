package br.com.gmfonseca.bolsaapp.models;

import javax.persistence.*;

@Entity
@Table(name = "bolsa")
public class Bolsa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int id;

}