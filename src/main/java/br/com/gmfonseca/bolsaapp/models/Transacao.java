package br.com.gmfonseca.bolsaapp.models;

import br.com.gmfonseca.bolsaapp.util.OrdemType;

import javax.persistence.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;

@Entity
@Table(name = "transacao")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int quantidade;
    private double valor;
    private String data;

    @ManyToOne
    @JoinColumn(name = "ativo_id")
    private Ativo ativo;

    @ManyToOne
    @JoinColumn(name = "venda_id")
    private Ordem venda;

    @ManyToOne
    @JoinColumn(name = "compra_id")
    private Ordem compra;

    public Transacao(Ativo ativo, int quantidade, double valor, Ordem ordem1, Ordem ordem2) {
        this.quantidade = quantidade;
        this.valor = valor;
        this.ativo = ativo;

        if(ordem1.getOperacao() == OrdemType.VENDA) {
            this.venda = ordem1;
            this.compra = ordem2;
        }else{
            this.compra = ordem1;
            this.venda = ordem2;
        }

        NumberFormat nb = new DecimalFormat("00");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, cal.get(Calendar.HOUR)-3);
        this.data = nb.format(cal.get(Calendar.DAY_OF_MONTH)) + "/" + nb.format((cal.get(Calendar.MONTH)+1)) + "/" + nb.format(cal.get(Calendar.YEAR)) + " - " + nb.format(cal.get(Calendar.HOUR_OF_DAY)) + ":" + nb.format(cal.get(Calendar.MINUTE));
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
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
