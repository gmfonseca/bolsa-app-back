package br.com.gmfonseca.bolsaapp.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transacao")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int quantidade;
    private double valor;
    private Date data;

    @ManyToOne
    @JoinColumn(name = "ativo_id")
    private Ativo ativo;

    @ManyToOne
    @JoinColumn(name = "venda_id")
    private Ordem venda;

    @ManyToOne
    @JoinColumn(name = "compra_id")
    private Ordem compra;

    public Transacao(Ativo ativo, int quantidade, double valor, Ordem venda, Ordem compra) {
        this.quantidade = quantidade;
        this.valor = valor;
        this.ativo = ativo;
        this.venda = venda;
        this.compra = compra;

        this.data = new Date();
    }

    public Transacao(){}

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

    public Ativo getAtivo() {
        return ativo;
    }

    public void setAtivo(Ativo ativo) {
        this.ativo = ativo;
    }

    public Ordem getVenda() {
        return venda;
    }

    public void setVenda(Ordem venda) {
        this.venda = venda;
    }

    public Ordem getCompra() {
        return compra;
    }

    public void setCompra(Ordem compra) {
        this.compra = compra;
    }
}
