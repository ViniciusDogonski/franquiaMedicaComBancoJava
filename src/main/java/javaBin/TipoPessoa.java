/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaBin;

/**
 *
 * @author Vinicius Augusto
 */
public class TipoPessoa {

    private int id;
    private String tipo;

    public TipoPessoa(int id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return "TipoPessoa{" + "id=" + id + ", tipo=" + tipo + '}';
    }

}
