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
import java.time.LocalDate;
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

    public void gerarFinanceiroMedicoMes(LocalDate dataAtual) {
        LocalDate dataInicio = dataAtual.minusMonths(1);

        String sql = "SELECT f.idFranquia, m.idMedico, SUM(c.valor * 0.7) AS total_payment "
                + "FROM franquia_medica.franquia f "
                + "INNER JOIN franquia_medica.unidade u ON f.idFranquia = u.franquiaId "
                + "INNER JOIN franquia_medica.consulta c ON u.idUnidade = c.unidadeId "
                + "INNER JOIN franquia_medica.medico m ON c.medicoId = m.idMedico "
                + "WHERE c.diaHorario >= ? AND c.diaHorario < ? "
                + "GROUP BY f.idFranquia, m.idMedico "
                + "UNION "
                + "SELECT f.idFranquia, m.idMedico, SUM(p.valor * 0.5) AS total_payment "
                + "FROM franquia_medica.franquia f "
                + "INNER JOIN franquia_medica.unidade u ON f.idFranquia = u.franquiaId "
                + "INNER JOIN franquia_medica.consulta c ON u.idUnidade = c.unidadeId "
                + "INNER JOIN franquia_medica.medico m ON c.medicoId = m.idMedico "
                + "INNER JOIN franquia_medica.procedimento p ON c.idConsulta = p.consultaId "
                + "WHERE p.diaHorario >= ? AND p.diaHorario < ? "
                + "GROUP BY f.idFranquia, m.idMedico";

        try (Connection connection = connectionFactory.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setObject(1, dataInicio);
            statement.setObject(2, dataAtual);
            statement.setObject(3, dataInicio);
            statement.setObject(4, dataAtual);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int idFranquia = resultSet.getInt("idFranquia");
                    int idMedico = resultSet.getInt("idMedico");
                    double totalPayment = resultSet.getDouble("total_payment");

                    FinanceiroMedico financeiroMedico = new FinanceiroMedico();
                    financeiroMedico.setValor(totalPayment);
                    Medico medico = new Medico();
                    medico.setId(idMedico);
                    financeiroMedico.setMedico(medico);
                    Franquia franquia = new Franquia();
                    franquia.setId(idFranquia);
                    financeiroMedico.setFranquia(franquia);
                    financeiroMedico.setDataCriacao(dataAtual.atStartOfDay());
                    financeiroMedico.setDataModificacao(dataAtual.atStartOfDay());

                    this.inserir(financeiroMedico);
               
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao gerar o financeiro médico do mês", e);
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
