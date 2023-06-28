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
import javaBin.EstadoFinanceiro;

/**
 *
 * @author Vinicius Augusto
 */
public class EstadoFinanceiroDAO {

    public List<EstadoFinanceiro> listarEstados() {

        String sql = "SELECT * FROM estadofinanceiro";

        List<EstadoFinanceiro> retornoDeTipos = new ArrayList<>();

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                int id = rs.getInt("idestadofinanceiro");
                String tipo = rs.getString("nome");

                EstadoFinanceiro novotipo = new EstadoFinanceiro(id, tipo);

                retornoDeTipos.add(novotipo);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return retornoDeTipos;
    }

}
