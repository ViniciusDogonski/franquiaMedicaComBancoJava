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
import javaBin.EstadoConsulta;

/**
 *
 * @author Vinicius Augusto
 */
public class EstadoConsultaDAO {

    public List<EstadoConsulta> listarEstadoConsulta() {

        String sql = "SELECT * FROM estado";

        List<EstadoConsulta> retornoEstadoConsulta = new ArrayList<>();

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                int id = rs.getInt("idEstado");
                String nome = rs.getString("nome");

                EstadoConsulta novoEstado = new EstadoConsulta(id, nome);

                //  System.out.println(id);
                // System.out.println(tipo);
                retornoEstadoConsulta.add(novoEstado);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return retornoEstadoConsulta;
    }

}
