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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javaBin.Consulta;
import javaBin.InfoConsulta;

/**
 *
 * @author bibis
 */
public class InfoConsultaDAO {

    public List<InfoConsulta> ListaDeInfoConsultas() {
        String sql = "SELECT * FROM infoconsulta";
        InfoConsulta infoNova = new InfoConsulta();
        List<InfoConsulta> lista = new ArrayList<>();
        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            infoNova.setConsulta(null);
            infoNova.setDescricao(rs.getString("descricao"));
            infoNova.setId(rs.getInt("idInfoConsulta"));
            Timestamp timestampDataCriacao = Timestamp.valueOf(rs.getString("dataCriacao"));
            LocalDateTime dataCriacao = timestampDataCriacao.toLocalDateTime();
            Timestamp timestampDataModificacao = Timestamp.valueOf(rs.getString("dataModificacao"));
            LocalDateTime dataModificacao = timestampDataModificacao.toLocalDateTime();
            infoNova.setDataCriacao(dataCriacao);
            infoNova.setDataModificacao(dataModificacao);
            lista.add(infoNova);
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return lista;
    }

    public List<InfoConsulta> InfoConsultasPorMedico() { //todas as info consultas de um medico
        List<InfoConsulta> infos = new ArrayList<>();
        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement ps = createPreparedStatement(connection); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int consultaId = rs.getInt("consultaId");
                int idInfoConsulta = rs.getInt("idInfoConsulta");
                String descricao = rs.getString("descricao");
                Timestamp timestampDataCriacao = Timestamp.valueOf(rs.getString("dataCriacao"));
                LocalDateTime dataCriacao = timestampDataCriacao.toLocalDateTime();
                Timestamp timestampDataModificacao = Timestamp.valueOf(rs.getString("dataModificacao"));
                LocalDateTime dataModificacao = timestampDataModificacao.toLocalDateTime();
                InfoConsulta info = new InfoConsulta();
                info.setId(idInfoConsulta);
                info.setDescricao(descricao);
                info.setConsulta(null);
                info.setDataCriacao(dataCriacao);
                info.setDataModificacao(dataModificacao);
                infos.add(info);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return infos;
    }

    private PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        String sql = "select * from infoconsulta";

        PreparedStatement ps = con.prepareStatement(sql);
        return ps;
    }

    public void addInfoConsulta(InfoConsulta info) {
        String sql = "INSERT INTO infoconsulta (consultaId, descricao,dataCriacao, dataModificacao)"
                + "VALUES (?, ?, ?, ?)";

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            // seta os valores
            stmt.setInt(1, info.getConsulta().getId());
            stmt.setString(2, info.getDescricao());
            Timestamp timestamp = Timestamp.valueOf(info.getDataCriacao());
            java.sql.Date sqlDate = new java.sql.Date(timestamp.getTime());
            java.sql.Time sqlTime = new java.sql.Time(timestamp.getTime());
            stmt.setString(3, sqlDate + " " + sqlTime);
            stmt.setString(4, sqlDate + " " + sqlTime);

            stmt.execute();

            System.out.println("info inserida com sucesso.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private PreparedStatement createPreparedStatement1(Connection con, long id) throws SQLException {
        String sql = "select * from infoconsulta where idInfoConsulta = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setLong(1, id);
        return ps;
    }

    public InfoConsulta buscaPorId(int idInfo, List<Consulta> consultas) {
        InfoConsulta infoconsulta = new InfoConsulta();
        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement ps = createPreparedStatement1(connection, idInfo); ResultSet rs = ps.executeQuery()) {
            infoconsulta.setId(rs.getInt("idInfoConsulta"));
            int idConsulta = rs.getInt("consultaId");
            infoconsulta.setDescricao(rs.getString("descricao"));
            Timestamp timestampDataCriacao = Timestamp.valueOf(rs.getString("dataCriacao"));
            LocalDateTime dataCriacao = timestampDataCriacao.toLocalDateTime();
            Timestamp timestampDataModificacao = Timestamp.valueOf(rs.getString("dataModificacao"));
            LocalDateTime dataModificacao = timestampDataModificacao.toLocalDateTime();
            infoconsulta.setDataCriacao(dataCriacao);
            infoconsulta.setDataModificacao(dataModificacao);
            for (Consulta c : consultas) {
                if (c != null) {
                    if (c.getId() == idConsulta) {
                        infoconsulta.setConsulta(c);
                    }

                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return infoconsulta;
    }

    public void deleteInfoConsulta(int idInfo) {
        String sql = "DELETE FROM infoconsulta where idInfoConsulta = ?";

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            // seta os valores
            stmt.setInt(1, idInfo);
            stmt.execute();
            System.out.println("info apagada com sucesso.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void alteraInfo(InfoConsulta info) {
        String sql = "UPDATE infoconsulta SET"
                + "  consultaId = ?,"
                + "  descricao = ?,"
                + "  dataModificacao = ?"
                + "WHERE"
                + "  idInfoConsulta = ?";
        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, info.getConsulta().getId());
            stmt.setString(2, info.getDescricao());

            Timestamp timestamp = Timestamp.valueOf(info.getDataModificacao());
            java.sql.Date sqlDate = new java.sql.Date(timestamp.getTime());
            java.sql.Time sqlTime = new java.sql.Time(timestamp.getTime());
            stmt.setString(3, sqlDate + " " + sqlTime);

            stmt.setInt(4, info.getId());

            stmt.execute();

            System.out.println("Info consulta alterado com sucesso.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
