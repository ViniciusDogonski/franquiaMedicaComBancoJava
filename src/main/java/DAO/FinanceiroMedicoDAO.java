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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javaBin.EstadoFinanceiro;
import javaBin.FinanceiroMedico;
import javaBin.Franquia;
import javaBin.Medico;

/**
 *
 * @author Vinicius Augusto
 */
public class FinanceiroMedicoDAO {

    private final ConnectionFactory connectionFactory;

    public FinanceiroMedicoDAO() {
        this.connectionFactory = new ConnectionFactory();
    }

    public void inserir(FinanceiroMedico financeiroMedico) {
        String sql = "INSERT INTO financeiromedico (valor, medicoId, estadofinanceiro, franquiaId, dataCriacao, dataModificacao) VALUES (?, ?, 1, ?, ?, ?)";

        try (Connection connection = connectionFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDouble(1, financeiroMedico.getValor());
            statement.setInt(2, financeiroMedico.getMedico().getId());
            //statement.setInt(3, financeiroMedico.getEstado().getId());
            statement.setInt(3, financeiroMedico.getFranquia().getId());
            statement.setObject(4, financeiroMedico.getDataCriacao());
            statement.setObject(5, financeiroMedico.getDataModificacao());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir o financeiro médico", e);
        }
    }

    public void atualizar(FinanceiroMedico financeiroMedico) {
        String sql = "UPDATE financeiromedico SET valor = ?, medicoId = ?, estadofinanceiro = ?, franquiaId = ?, dataModificacao = ? WHERE idfinanceiromedico = ?";

        try (Connection connection = connectionFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDouble(1, financeiroMedico.getValor());
            statement.setInt(2, financeiroMedico.getMedico().getId());
            statement.setInt(3, financeiroMedico.getEstado().getId());
            statement.setInt(4, financeiroMedico.getFranquia().getId());
            statement.setObject(5, financeiroMedico.getDataModificacao());
            statement.setInt(6, financeiroMedico.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar o financeiro médico", e);
        }
    }

    public void excluir(int idFinanceiroMedico) {
        String sql = "DELETE FROM financeiromedico WHERE idfinanceiromedico = ?";

        try (Connection connection = connectionFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, idFinanceiroMedico);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir o financeiro médico", e);
        }
    }

    public List<FinanceiroMedico> listarTodos() {
        String sql = "SELECT * FROM financeiromedico";

        try (Connection connection = connectionFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {

            List<FinanceiroMedico> financeirosMedicos = new ArrayList<>();

            while (resultSet.next()) {
                int idFinanceiroMedico = resultSet.getInt("idfinanceiromedico");
                double valor = resultSet.getDouble("valor");
                int medicoId = resultSet.getInt("medicoId");
                int estadoFinanceiroId = resultSet.getInt("estadofinanceiro");
                int franquiaId = resultSet.getInt("franquiaId");
                LocalDateTime dataCriacao = resultSet.getTimestamp("dataCriacao").toLocalDateTime();
                LocalDateTime dataModificacao = resultSet.getTimestamp("dataModificacao").toLocalDateTime();

                Medico medico = new Medico();
                medico.setId(medicoId);

                EstadoFinanceiro estadoFinanceiro = new EstadoFinanceiro(estadoFinanceiroId, null);

                Franquia franquia = new Franquia();
                franquia.setId(franquiaId);

                FinanceiroMedico financeiroMedico = new FinanceiroMedico();
                financeiroMedico.setId(idFinanceiroMedico);
                financeiroMedico.setValor(valor);
                financeiroMedico.setMedico(medico);
                financeiroMedico.setEstado(estadoFinanceiro);
                financeiroMedico.setFranquia(franquia);
                financeiroMedico.setDataCriacao(dataCriacao);
                financeiroMedico.setDataModificacao(dataModificacao);

                financeirosMedicos.add(financeiroMedico);
            }

            return financeirosMedicos;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar os financeiros médicos", e);
        }
    }

}
