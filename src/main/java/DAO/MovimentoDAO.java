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
import java.util.List;
import javaBin.Movimento;

/**
 *
 * @author Vinicius Augusto
 */
public class MovimentoDAO {

    public List<Movimento> listarMovimentos() {

        String sql = "SELECT * FROM movimento";

        List<Movimento> retornoDeMovimento = new ArrayList<>();

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                int id = rs.getInt("idMovimento");
                String tipo = rs.getString("nome");

                Movimento novoMovimento = new Movimento(id, tipo);

                retornoDeMovimento.add(novoMovimento);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return retornoDeMovimento;
    }

}
