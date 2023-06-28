/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaBin;

import java.time.LocalDateTime;

/**
 *
 * @author bibis
 */
public class Procedimento {
     private int id;
    private String nome;
    private Consulta consulta;
    private LocalDateTime dataHora; //dia e horario
    private EstadoConsulta estado;
    private double valor;
    private String laudo;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataModificacao;

    public Procedimento() {
        this.dataCriacao = LocalDateTime.now();
        this.dataModificacao = LocalDateTime.now();
    }

    
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public EstadoConsulta getEstado() {
        return estado;
    }

    public void setEstado(EstadoConsulta estado) {
        this.estado = estado;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getLaudo() {
        return laudo;
    }

    public void setLaudo(String laudo) {
        this.laudo = laudo;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataModificacao() {
        return dataModificacao;
    }

    public void setDataModificacao(LocalDateTime dataModificacao) {
        this.dataModificacao = dataModificacao;
    }

    @Override
    public String toString() {
        return "Procedimento{" + "id=" + id + ", nome=" + nome + "\n"+ ", consulta=" + consulta + "\n"+ ", dataHora=" + dataHora + "\n"+ ", estado=" + estado + ", valor=" + valor +  "\n" + ", laudo=" + laudo +  "\n" + ", dataCriacao=" + dataCriacao +  "\n" + ", dataModificacao=" + dataModificacao + '}';
    }
    
    
}
