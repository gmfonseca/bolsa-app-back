package br.com.gmfonseca.bolsaapp.models;

import br.com.gmfonseca.bolsaapp.util.OrdemType;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ordem")
public class Ordem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int quantidade;
    private double valor;
    private Date data;

    @Enumerated(EnumType.ORDINAL)
    private OrdemType operacao;

    @ManyToOne
    @JoinColumn(name = "ativo_id")
    private Ativo ativo;

    @ManyToOne
    @JoinColumn(name="corretora_id")
    private Corretora corretora;

    public Ordem(OrdemType operacao, int quantidade, double valor, Ativo ativo, Corretora corretora) {
        this.operacao = operacao;
        this.quantidade = quantidade;
        this.valor = valor;
        this.ativo = ativo;
        this.corretora = corretora;

        this.data = new Date();
    }

    public Ordem(){}

    public int getId() {
        return id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public OrdemType getOperacao() {
        return operacao;
    }

    public void setOperacao(OrdemType operacao) {
        this.operacao = operacao;
    }

    public Ativo getAtivo() {
        return ativo;
    }

    public void setAtivo(Ativo ativo) {
        this.ativo = ativo;
    }

    public Corretora getCorretora() {
        return corretora;
    }

    public void setCorretora(Corretora corretora) {
        this.corretora = corretora;
    }
}
