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
import javaBin.EstadoConsulta;
import javaBin.Medico;
import javaBin.Pessoa;
import javaBin.UnidadeFranquia;

/**
 *
 * @author Vinicius Augusto
 */
public class ConsultaDAO {

    /*INSERT INTO `consulta` (`diaHorario`, `estadoId`, `medicoId`, `pacienteId`, `valor`, `unidadeId`, `dataCriacao`, `dataModificacao`)
VALUES ('2023-06-27 10:00:00', 1, 2, 3, 50.00, 4, '2023-06-27 09:00:00', '2023-06-27 09:30:00');*/
    public Consulta addConsulta(Consulta ConsultaAdicionada) {

        String sql = "INSERT INTO consulta (diaHorario, estadoId, medicoId, pacienteId, valor, unidadeId, dataCriacao, dataModificacao)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            // seta os valores

            Timestamp timestampDiaHorarioConsulta = Timestamp.valueOf(ConsultaAdicionada.getDataHora());
            java.sql.Date sqlDateDiaHorarioConsulta = new java.sql.Date(timestampDiaHorarioConsulta.getTime());
            java.sql.Time sqlTimeDiaHorarioConsulta = new java.sql.Time(timestampDiaHorarioConsulta.getTime());
            stmt.setString(1, sqlDateDiaHorarioConsulta + " " + sqlTimeDiaHorarioConsulta);
            stmt.setInt(2, ConsultaAdicionada.getEstado().getId());
            stmt.setInt(3, ConsultaAdicionada.getMedico().getId());
            stmt.setInt(4, ConsultaAdicionada.getPaciente().getId());
            stmt.setDouble(5, ConsultaAdicionada.getValor());
            stmt.setInt(6, ConsultaAdicionada.getUnidade().getId());

            Timestamp timestamp = Timestamp.valueOf(ConsultaAdicionada.getDataCriacao());
            java.sql.Date sqlDate = new java.sql.Date(timestamp.getTime());
            java.sql.Time sqlTime = new java.sql.Time(timestamp.getTime());
            stmt.setString(7, sqlDate + " " + sqlTime);
            stmt.setString(8, sqlDate + " " + sqlTime);

            /*stmt.setString(1, pessoaAdicionada.getNome());
            stmt.setString(2, pessoaAdicionada.getEndereco());
            stmt.setString(3, pessoaAdicionada.getCpf());
            stmt.setString(4, pessoaAdicionada.getTelefone());
            stmt.setString(5, pessoaAdicionada.getLogin());
            stmt.setString(6, pessoaAdicionada.getSenha());

            Timestamp timestamp = Timestamp.valueOf(pessoaAdicionada.getDataCriacao());
            java.sql.Date sqlDate = new java.sql.Date(timestamp.getTime());
            java.sql.Time sqlTime = new java.sql.Time(timestamp.getTime());
            stmt.setString(7, sqlDate + " " + sqlTime);
            stmt.setString(8, sqlDate + " " + sqlTime);*/
            stmt.execute();

            System.out.println("consulta inserido com sucesso.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ConsultaAdicionada;

    }

    public List<Consulta> listaDeConsultas(List<EstadoConsulta> estadoConsultas) {

        List<Consulta> listaRetorno = new ArrayList<>();

        String sql = "select * from consulta";

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
//nome, endereco,CPF,telefone,login,senha,tipoUsuarioId,dataCriacao,dataModificacao

                int idConsulta = rs.getInt("idConsulta");
                Timestamp diaHorarioTime = Timestamp.valueOf(rs.getString("diaHorario"));
                LocalDateTime diaHorario = diaHorarioTime.toLocalDateTime();
                int estadoId = rs.getInt("estadoId");
                int medicoId = rs.getInt("medicoId");
                int pacienteId = rs.getInt("pacienteId");
                double valor = rs.getDouble("valor");
                int unidadeId = rs.getInt("unidadeId");

                Timestamp timestampDataCriacao = Timestamp.valueOf(rs.getString("dataCriacao"));
                LocalDateTime dataCriacao = timestampDataCriacao.toLocalDateTime();
                Timestamp timestampDataModificacao = Timestamp.valueOf(rs.getString("dataModificacao"));
                LocalDateTime dataModificacao = timestampDataModificacao.toLocalDateTime();

                Consulta novaConsulta = new Consulta();

                for (EstadoConsulta estadoConsulta : estadoConsultas) {

                    if (estadoConsulta.getId() == estadoId) {
                        novaConsulta.setEstado(estadoConsulta);
                    }
                }

                novaConsulta.setId(idConsulta);
                novaConsulta.setDataHora(diaHorario);
                Medico medico = new Medico();
                medico.setId(medicoId);
                novaConsulta.setMedico(medico);

                Pessoa pessoa = new Pessoa();
                pessoa.setId(pacienteId);
                novaConsulta.setPaciente(pessoa);

                novaConsulta.setValor(valor);

                UnidadeFranquia unidade = new UnidadeFranquia();
                unidade.setId(unidadeId);
                novaConsulta.setUnidade(unidade);

                novaConsulta.setDataModificacao(dataModificacao);
                novaConsulta.setDataCriacao(dataCriacao);

                listaRetorno.add(novaConsulta);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listaRetorno;

    }

    public void excluirConsulta(int id) {

        String sql = "DELETE FROM consulta WHERE idConsulta = ?";

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);

            stmt.execute();

            System.out.println("consulta excluído com sucesso.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Consulta buscaPorID(int code) {
        Consulta ConsultaBuscada = null;
        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement ps = createPreparedStatement(connection, code); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ConsultaBuscada = new Consulta();

                int idConsulta = rs.getInt("idConsulta");
                Timestamp diaHorarioTime = Timestamp.valueOf(rs.getString("diaHorario"));
                LocalDateTime diaHorario = diaHorarioTime.toLocalDateTime();
                int estadoId = rs.getInt("estadoId");
                int medicoId = rs.getInt("medicoId");
                int pacienteId = rs.getInt("pacienteId");
                double valor = rs.getDouble("valor");
                int unidadeId = rs.getInt("unidadeId");

                Timestamp timestampDataCriacao = Timestamp.valueOf(rs.getString("dataCriacao"));
                LocalDateTime dataCriacao = timestampDataCriacao.toLocalDateTime();
                Timestamp timestampDataModificacao = Timestamp.valueOf(rs.getString("dataModificacao"));
                LocalDateTime dataModificacao = timestampDataModificacao.toLocalDateTime();

                ConsultaBuscada.setId(idConsulta);
                ConsultaBuscada.setDataHora(diaHorario);
                Medico medico = new Medico();
                medico.setId(medicoId);
                ConsultaBuscada.setMedico(medico);

                Pessoa pessoa = new Pessoa();
                pessoa.setId(pacienteId);
                ConsultaBuscada.setPaciente(pessoa);

                ConsultaBuscada.setValor(valor);

                UnidadeFranquia unidade = new UnidadeFranquia();
                unidade.setId(unidadeId);
                ConsultaBuscada.setUnidade(unidade);

                ConsultaBuscada.setDataModificacao(dataModificacao);
                ConsultaBuscada.setDataCriacao(dataCriacao);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ConsultaBuscada;
    }

    private PreparedStatement createPreparedStatement(Connection con, long id) throws SQLException {
        String sql = "select * from consulta where idConsulta = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setLong(1, id);
        return ps;
    }

    public Consulta alterarConsulta(Consulta consultaAlterada) {

        String sql = "UPDATE consulta SET"
                + "  diaHorario = ?,"
                + "  estadoId = ?,"
                + "  medicoId = ?,"
                + "  pacienteId = ?,"
                + "  valor = ?,"
                + "  unidadeId = ?,"
                + "  dataModificacao = ?"
                + "WHERE"
                + "  idConsulta = ?";

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            Timestamp timestampDiaHorarioConsulta = Timestamp.valueOf(consultaAlterada.getDataHora());
            java.sql.Date sqlDateDiaHorarioConsulta = new java.sql.Date(timestampDiaHorarioConsulta.getTime());
            java.sql.Time sqlTimeDiaHorarioConsulta = new java.sql.Time(timestampDiaHorarioConsulta.getTime());
            stmt.setString(1, sqlDateDiaHorarioConsulta + " " + sqlTimeDiaHorarioConsulta);
            stmt.setInt(2, consultaAlterada.getEstado().getId());
            stmt.setInt(3, consultaAlterada.getMedico().getId());
            stmt.setInt(4, consultaAlterada.getPaciente().getId());
            stmt.setDouble(5, consultaAlterada.getValor());
            stmt.setInt(6, consultaAlterada.getUnidade().getId());

            Timestamp timestamp = Timestamp.valueOf(consultaAlterada.getDataModificacao());
            java.sql.Date sqlDate = new java.sql.Date(timestamp.getTime());
            java.sql.Time sqlTime = new java.sql.Time(timestamp.getTime());
            stmt.setString(7, sqlDate + " " + sqlTime);

            stmt.setInt(8, consultaAlterada.getId());

            stmt.execute();

            System.out.println("Consulta alterado com sucesso.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    private PreparedStatement createPreparedStatement1(Connection con, long id) throws SQLException {
        String sql = "select medicoId from consulta where idConsulta = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setLong(1, id);
        return ps;
    }

    public Medico buscarMedicoPorConsulta(int idConsulta, List<Medico> medicos) {
       int medicoId=0;
        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement ps = createPreparedStatement1(connection, idConsulta); ResultSet rs = ps.executeQuery()) {
            while(rs.next()){
                medicoId = rs.getInt("medicoId");
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        Medico medico = new Medico();
        for(Medico m : medicos){
            if(m != null){
                if(m.getId() == medicoId){
                    medico = m;
                }
            }
        }
        return medico;
        }

    }
