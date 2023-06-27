/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaBin;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author bibis
 */
public class Franquia {
    private int id;
    private String nome;
    private String cnpj;
    private String cidade;
    private String endereco;
    private Pessoa responsavel;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataModificacao;
    public Franquia(){
         this.setDataCriacao(LocalDateTime.now());
        this.setDataModificacao(LocalDateTime.now());
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
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

    public void setEndereco(String endereço) {
        this.endereco = endereço;
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
        this.dataModificacao = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Franquia{"+"id:" + id  + ","+ "\n "
        + "nome=" + nome + "," + "\n "
        + ", cnpj=" + cnpj  + "," + "\n "+
        ", cidade=" + cidade + "," + "\n "+
        ", endereco=" + endereco + "," + "\n "
        + ", responsavel=" + responsavel  + "," + "\n "
        +", dataCriacao=" + dataCriacao + "," + "\n "
        +", dataModificacao=" + dataModificacao  + "," + '}' + "\n";
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
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
        final Franquia other = (Franquia) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.cnpj, other.cnpj)) {
            return false;
        }
        return Objects.equals(this.responsavel, other.responsavel);
    }
    

}
