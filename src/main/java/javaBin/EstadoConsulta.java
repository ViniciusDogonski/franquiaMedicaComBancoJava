/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaBin;

/**
 *
 * @author Vinicius Augusto
 */
public class EstadoConsulta {

    private int id;
    private String nome;

    public EstadoConsulta(int id, String nome) {

        this.id = id;
        this.nome = nome;

    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return id + "- " + nome;
    }

}
