/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaBin;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author Vinicius Augusto
 */
public class Consulta {

    private int id;
    private LocalDateTime dataHora; //dia e horario

    private EstadoConsulta estado;

    private Medico medico;
    private Pessoa paciente;
    private double valor;

    private UnidadeFranquia unidade;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataModificacao;

    public Consulta() {
        this.dataCriacao = LocalDateTime.now();
        this.dataModificacao = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public EstadoConsulta getEstado() {
        return estado;
    }

    public Medico getMedico() {
        return medico;
    }

    public Pessoa getPaciente() {
        return paciente;
    }

    public double getValor() {
        return valor;
    }

    public UnidadeFranquia getUnidade() {
        return unidade;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataModificacao() {
        return dataModificacao;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public void setEstado(EstadoConsulta estado) {
        this.estado = estado;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public void setPaciente(Pessoa paciente) {
        this.paciente = paciente;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public void setUnidade(UnidadeFranquia unidade) {
        this.unidade = unidade;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public void setDataModificacao(LocalDateTime dataModificacao) {
        this.dataModificacao = dataModificacao;
    }

    @Override
    public String toString() {
        return "Consulta{" + "id=" + id + ", dataHora=" + dataHora + ", estado=" + estado.getId() + ", medico=" + medico.getId() + ", paciente=" + paciente.getId() + ", valor=" + valor + ", unidade=" + unidade.getId() + ", dataCriacao=" + dataCriacao + ", dataModificacao=" + dataModificacao + '}';
    }

}
