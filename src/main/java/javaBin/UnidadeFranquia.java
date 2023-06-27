/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaBin;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author bibis
 */
public class UnidadeFranquia {
    private int id;
    private Franquia franquia;
    private String cidade;
    private String endereco;
    private Pessoa responsavel;
    private LocalDateTime dataCriacao; 
    private LocalDateTime dataModificacao;
    public UnidadeFranquia(){
        this.dataCriacao = LocalDateTime.now();
        this.dataModificacao = LocalDateTime.now();
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Franquia getFranquia() {
        return franquia;
    }

    public void setFranquia(Franquia franquia) {
        this.franquia = franquia;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Pessoa getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Pessoa responsavel) {
        this.responsavel = responsavel;
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
        return "UnidadeFranquia{" + "id=" + id + "," + "\n "
                + ",franquia=" + franquia + "," + "\n "+
                ", cidade=" + cidade  + ","+ "\n "+ 
        " endereco=" + endereco + ","+ "\n "+
        "responsavel=" + responsavel + "," + "\n "
        + "dataCriacao=" + dataCriacao + "," + "\n "
        +"dataModificacao=" + dataModificacao + '}' + "\n";
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UnidadeFranquia other = (UnidadeFranquia) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.cidade, other.cidade)) {
            return false;
        }
        if (!Objects.equals(this.endereco, other.endereco)) {
            return false;
        }
        if (!Objects.equals(this.franquia, other.franquia)) {
            return false;
        }
        if (!Objects.equals(this.responsavel, other.responsavel)) {
            return false;
        }
        if (!Objects.equals(this.dataCriacao, other.dataCriacao)) {
            return false;
        }
        return Objects.equals(this.dataModificacao, other.dataModificacao);
    }
    
}
