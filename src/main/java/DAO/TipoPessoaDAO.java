/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javaBin.TipoPessoa;

/**
 *
 * @author Vinicius Augusto
 */
public class TipoPessoaDAO {

    public List<TipoPessoa> listarTipos() {

        String sql = "SELECT * FROM tipousuario";

        List<TipoPessoa> retornoDeTipos = new ArrayList<>();

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                int id = rs.getInt("id");
                String tipo = rs.getString("tipo");

                TipoPessoa novotipo = new TipoPessoa(id, tipo);

                //  System.out.println(id);
                // System.out.println(tipo);
                retornoDeTipos.add(novotipo);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return retornoDeTipos;
    }

}
