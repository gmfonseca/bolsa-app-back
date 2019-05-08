package br.com.gmfonseca.bolsaapp.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ativo")
public class Ativo {

    @Id
    @Column(length = 5)
    private String codigo;

    private String nome;
    private String descricao;

    public Ativo() {}

    public Ativo(String nome, String codigo, String descricao) {
        this.nome = nome;
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ativo ativo = (Ativo) o;
        return codigo.equals(ativo.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
}
