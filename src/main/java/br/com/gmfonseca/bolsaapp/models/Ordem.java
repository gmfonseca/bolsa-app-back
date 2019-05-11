package br.com.gmfonseca.bolsaapp.models;

import br.com.gmfonseca.bolsaapp.util.OrdemType;
import org.hibernate.type.CalendarDateType;
import sun.util.calendar.CalendarDate;

import javax.persistence.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "ordem")
public class Ordem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int quantidade;
    private double valor;
    private String data;
    private boolean used;

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
        this.used = false;

        NumberFormat nb = new DecimalFormat("00");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, cal.get(Calendar.HOUR)-3);
        this.data = nb.format(cal.get(Calendar.DAY_OF_MONTH)) + "/" + nb.format((cal.get(Calendar.MONTH)+1)) + "/" + nb.format(cal.get(Calendar.YEAR)) + " - " + nb.format(cal.get(Calendar.HOUR_OF_DAY)) + ":" + nb.format(cal.get(Calendar.MINUTE));
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
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

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
