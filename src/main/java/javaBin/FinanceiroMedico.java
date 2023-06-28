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
public class FinanceiroMedico {

    private int id;
    private double valor;
    private Medico medico;
    private EstadoFinanceiro estado;
    private Franquia franquia;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataModificacao;

    public FinanceiroMedico() {
        this.dataCriacao = LocalDateTime.now();
        this.dataModificacao = LocalDateTime.now();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public void setEstado(EstadoFinanceiro estado) {
        this.estado = estado;
    }

    public void setFranquia(Franquia franquia) {
        this.franquia = franquia;
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

    public double getValor() {
        return valor;
    }

    public Medico getMedico() {
        return medico;
    }

    public EstadoFinanceiro getEstado() {
        return estado;
    }

    public Franquia getFranquia() {
        return franquia;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataModificacao() {
        return dataModificacao;
    }

    @Override
    public String toString() {
        return "FinanceiroMedico{" + "id=" + id + ", valor=" + valor + ", medico=" + medico + ", estado=" + estado + ", franquia=" + franquia + ", dataCriacao=" + dataCriacao + ", dataModificacao=" + dataModificacao + '}';
    }

}
