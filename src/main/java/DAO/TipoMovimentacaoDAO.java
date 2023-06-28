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
import javaBin.TipoMovimentacao;

/**
 *
 * @author Vinicius Augusto
 */
public class TipoMovimentacaoDAO {

    public List<TipoMovimentacao> listarTipos() {

        String sql = "SELECT * FROM tipomovimento";

        List<TipoMovimentacao> retornoDeTipos = new ArrayList<>();

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                int id = rs.getInt("idTipoMovimento");
                String tipo = rs.getString("nome");

                TipoMovimentacao novotipo = new TipoMovimentacao(id, tipo);

           
                retornoDeTipos.add(novotipo);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return retornoDeTipos;
    }

}
