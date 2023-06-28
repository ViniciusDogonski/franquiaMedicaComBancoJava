/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaBin;

import java.time.LocalDateTime;

/**
 *
 * @author Vinicius Augusto
 */
public class FinanceiroADM {

    private int id;
    private TipoMovimentacao tipoMovimentacao;
    private double valor;
    private UnidadeFranquia unidade;
    private String descriacao;
    private Movimento movimento;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataModificacao;

    public FinanceiroADM() {
        this.dataCriacao = LocalDateTime.now();
        this.dataModificacao = LocalDateTime.now();
    }

    public Movimento getMovimento() {
        return movimento;
    }

    public void setMovimento(Movimento movimento) {
        this.movimento = movimento;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTipoMovimentacao(TipoMovimentacao tipoMovimentacao) {
        this.tipoMovimentacao = tipoMovimentacao;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public void setUnidade(UnidadeFranquia unidade) {
        this.unidade = unidade;
    }

    public void setDescriacao(String descriacao) {
        this.descriacao = descriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public void setDataModificacao(LocalDateTime dataModificacao) {
        this.dataModificacao = dataModificacao;
    }

    public int getId() {
        return id;
    }

    public TipoMovimentacao getTipoMovimentacao() {
        return tipoMovimentacao;
    }

    public double getValor() {
        return valor;
    }

    public UnidadeFranquia getUnidade() {
        return unidade;
    }

    public String getDescriacao() {
        return descriacao;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataModificacao() {
        return dataModificacao;
    }

}
