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
import java.util.ArrayList;
import java.util.List;
import javaBin.FinanceiroADM;
import javaBin.Movimento;
import javaBin.TipoMovimentacao;
import javaBin.UnidadeFranquia;

/**
 *
 * @author Vinicius Augusto
 */
public class FinanceiroAdmDAO {

    private Connection connection;

    public FinanceiroAdmDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void inserir(FinanceiroADM financeiro) {
        String sql = "INSERT INTO financeiroadm (tipoMovimentoId, valor, unidadeId, descricao, movimentoId, dataCriacao, dataModificacao) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, financeiro.getTipoMovimentacao().getId());
            stmt.setDouble(2, financeiro.getValor());
            stmt.setInt(3, financeiro.getUnidade().getId());
            stmt.setString(4, financeiro.getDescriacao());
            stmt.setInt(5, financeiro.getMovimento().getId());
            stmt.setTimestamp(6, java.sql.Timestamp.valueOf(financeiro.getDataCriacao()));
            stmt.setTimestamp(7, java.sql.Timestamp.valueOf(financeiro.getDataModificacao()));

            stmt.execute();
            System.out.println("add");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir o registro financeiro: " + e.getMessage());
        }
    }

    public void gerarFinanceiroADMdoMes(LocalDate dataAtual) {
        int year = dataAtual.getYear();
        int month = dataAtual.getMonthValue();

        String sql = "SELECT u.idUnidade, f.idFranquia, 1000 + (0.05 * (SUM(c.valor) + IFNULL(SUM(p.valor), 0))) AS total_payment "
                + "FROM franquia_medica.franquia f "
                + "INNER JOIN franquia_medica.unidade u ON f.idFranquia = u.franquiaId "
                + "LEFT JOIN franquia_medica.consulta c ON u.idUnidade = c.unidadeId "
                + "LEFT JOIN franquia_medica.procedimento p ON c.idConsulta = p.consultaId "
                + "WHERE YEAR(c.diaHorario) = ? AND MONTH(c.diaHorario) = ? "
                + "GROUP BY u.idUnidade, f.idFranquia";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, year);
            statement.setInt(2, month - 1);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int idUnidade = resultSet.getInt("idUnidade");
                    int idFranquia = resultSet.getInt("idFranquia");
                    double totalPayment = resultSet.getDouble("total_payment");

                    FinanceiroADM financeiroADM = new FinanceiroADM();
                    TipoMovimentacao tipoMovimentacao = new TipoMovimentacao(2, null);
                    financeiroADM.setTipoMovimentacao(tipoMovimentacao);
                    financeiroADM.setValor(totalPayment);
                    UnidadeFranquia unidadeFranquia = new UnidadeFranquia();
                    unidadeFranquia.setId(idUnidade);
                    financeiroADM.setUnidade(unidadeFranquia);
                    financeiroADM.setDescriacao("pagamento administradora");
                    Movimento mov = new Movimento(3, null);
                    financeiroADM.setMovimento(mov);
                    financeiroADM.setDataCriacao(dataAtual.atStartOfDay());
                    financeiroADM.setDataModificacao(dataAtual.atStartOfDay());

                    this.inserir(financeiroADM);
                    
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao gerar o financeiro ADM do mês", e);
        }
    }

    public void inserirConsulta(FinanceiroADM financeiro) {
        String sql = "INSERT INTO financeiroadm (tipoMovimentoId, valor, unidadeId, descricao, movimentoId, dataCriacao, dataModificacao) "
                + "VALUES (1, ?, ?, ?, 1, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setDouble(1, financeiro.getValor());
            stmt.setInt(2, financeiro.getUnidade().getId());
            stmt.setString(3, financeiro.getDescriacao());

            stmt.setTimestamp(4, java.sql.Timestamp.valueOf(financeiro.getDataCriacao()));
            stmt.setTimestamp(5, java.sql.Timestamp.valueOf(financeiro.getDataModificacao()));

            stmt.execute();
            System.out.println("add consulta");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir o registro financeiro: " + e.getMessage());
        }
    }

    public void inserirProcedimento(FinanceiroADM financeiro) {
        String sql = "INSERT INTO financeiroadm (tipoMovimentoId, valor, unidadeId, descricao, movimentoId, dataCriacao, dataModificacao) "
                + "VALUES (1, ?, ?, ?, 2, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setDouble(1, financeiro.getValor());
            stmt.setInt(2, financeiro.getUnidade().getId());
            stmt.setString(3, financeiro.getDescriacao());

            stmt.setTimestamp(4, java.sql.Timestamp.valueOf(financeiro.getDataCriacao()));
            stmt.setTimestamp(5, java.sql.Timestamp.valueOf(financeiro.getDataModificacao()));

            stmt.execute();
            System.out.println("add procedimento");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir o registro financeiro: " + e.getMessage());
        }
    }

    public void editar(FinanceiroADM financeiro) {
        String sql = "UPDATE financeiroadm SET tipoMovimentoId = ?, valor = ?, unidadeId = ?, descricao = ?, "
                + "movimentoId = ?, dataCriacao = ?, dataModificacao = ? WHERE idFinanceiroADM = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, financeiro.getTipoMovimentacao().getId());
            stmt.setDouble(2, financeiro.getValor());
            stmt.setInt(3, financeiro.getUnidade().getId());
            stmt.setString(4, financeiro.getDescriacao());
            stmt.setInt(5, financeiro.getMovimento().getId());
            stmt.setTimestamp(6, java.sql.Timestamp.valueOf(financeiro.getDataCriacao()));
            stmt.setTimestamp(7, java.sql.Timestamp.valueOf(financeiro.getDataModificacao()));
            stmt.setInt(8, financeiro.getId());

            stmt.execute();
            System.out.println("edit");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao editar o registro financeiro: " + e.getMessage());
        }
    }

    public List<FinanceiroADM> listar() {
        String sql = "SELECT * FROM financeiroadm";
        List<FinanceiroADM> lista = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                FinanceiroADM financeiro = new FinanceiroADM();
                financeiro.setId(rs.getInt("idFinanceiroADM"));
                TipoMovimentacao tipoMovimentacao = new TipoMovimentacao(rs.getInt("tipoMovimentoId"), null);
                financeiro.setTipoMovimentacao(tipoMovimentacao);
                financeiro.setValor(rs.getDouble("valor"));
                UnidadeFranquia unidadeFranquia = new UnidadeFranquia();
                unidadeFranquia.setId(rs.getInt("unidadeId"));
                financeiro.setUnidade(unidadeFranquia);
                financeiro.setDescriacao(rs.getString("descricao"));
                Movimento movimento = new Movimento(rs.getInt("movimentoId"), null);
                financeiro.setMovimento(movimento);
                financeiro.setDataCriacao(rs.getTimestamp("dataCriacao").toLocalDateTime());
                financeiro.setDataModificacao(rs.getTimestamp("dataModificacao").toLocalDateTime());

                lista.add(financeiro);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar os registros financeiros: " + e.getMessage());
        }

        return lista;
    }

    public void excluir(int id) {
        String sql = "DELETE FROM financeiroadm WHERE idFinanceiroADM = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("delete");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir o registro financeiro: " + e.getMessage());
        }
    }

}
