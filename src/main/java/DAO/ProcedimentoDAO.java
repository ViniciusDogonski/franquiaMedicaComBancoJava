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
import javaBin.Procedimento;
import javaBin.Consulta;
import javaBin.EstadoConsulta;

/**
 *
 * @author bibis
 */
public class ProcedimentoDAO {
    
    public void addProcedimento(Procedimento proc) {
        String sql = "insert into procedimento (nome, consultaId, diaHorario, estadoId, valor, laudo, dataCriacao, dataModificacao) values (?,?,?,?,?,?,?,?)";
        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, proc.getNome());
            stmt.setInt(2, proc.getConsulta().getId());
            Timestamp timestampDiaHorarioProc = Timestamp.valueOf(proc.getDataHora());
            java.sql.Date sqlDateDiaHorarioProc = new java.sql.Date(timestampDiaHorarioProc.getTime());
            java.sql.Time sqlTimeDiaHorarioProc = new java.sql.Time(timestampDiaHorarioProc.getTime());
            stmt.setString(3, sqlDateDiaHorarioProc + " " + sqlTimeDiaHorarioProc);
            stmt.setInt(4, proc.getEstado().getId());
            stmt.setDouble(5, proc.getValor());
            stmt.setString(6, proc.getLaudo());
            Timestamp timestampCriacaoProc = Timestamp.valueOf(proc.getDataCriacao());
            java.sql.Date sqlDateCriacaoProc = new java.sql.Date(timestampCriacaoProc.getTime());
            java.sql.Time sqlTimeCriacaoProc = new java.sql.Time(timestampCriacaoProc.getTime());
            stmt.setString(7, sqlDateCriacaoProc + " " + sqlTimeCriacaoProc);
            stmt.setString(8, sqlDateCriacaoProc + " " + sqlTimeCriacaoProc);
            
            stmt.execute();
            System.out.println("procedimento criado com sucesso");
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void deleteProcedimento(int idProc) {
        String sql = "DELETE FROM procedimento where idProcedimento = ?";
        try (Connection con = new ConnectionFactory().getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idProc);
            stmt.execute();
            System.out.println("procedimento deletado com sucesso");
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
    
    public void alteraProcedimento(Procedimento proc) {
        String sql = "UPDATE procedimento SET"
                + "  nome = ?,"
                + "  consultaId = ?,"
                + "  diaHorario = ?,"
                + "  estadoId = ?,"
                + "  valor = ?,"
                + "  laudo= ?,"
                + "  dataModificacao = ?"
                + "WHERE"
                + "  idProcedimento = ?";
        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, proc.getNome());
            stmt.setInt(2, proc.getConsulta().getId());
            Timestamp timestampDiaHorarioProc = Timestamp.valueOf(proc.getDataHora());
            java.sql.Date sqlDateDiaHorarioProc = new java.sql.Date(timestampDiaHorarioProc.getTime());
            java.sql.Time sqlTimeDiaHorarioProc = new java.sql.Time(timestampDiaHorarioProc.getTime());
            stmt.setString(3, sqlDateDiaHorarioProc + " " + sqlTimeDiaHorarioProc);
            stmt.setInt(4, proc.getEstado().getId());
            stmt.setDouble(5, proc.getValor());
            stmt.setString(6, proc.getLaudo());
            Timestamp timestampModificacao = Timestamp.valueOf(proc.getDataModificacao());
            java.sql.Date sqlDateModificacao = new java.sql.Date(timestampModificacao.getTime());
            java.sql.Time sqlTimeModificacao = new java.sql.Time(timestampModificacao.getTime());
            stmt.setString(7, sqlDateModificacao + " " + sqlTimeModificacao);
            stmt.setInt(8, proc.getId());
            stmt.execute();
            System.out.println("procedimento alterado com sucesso.");
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public Procedimento buscaPorId(int proc, EstadoConsultaDAO estado, ConsultaDAO consulta) {
        //recebe o id e depois pega o id da consulta e busca a consulta;
        String sql = "SELECT * FROM procedimento where idProcedimento = ?";
        Procedimento procedimento = new Procedimento();
        try (Connection con = new ConnectionFactory().getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                procedimento.setId(rs.getInt("idProcedimento"));
                procedimento.setNome(rs.getString("nome"));
                procedimento.setLaudo(rs.getString("laudo"));
                procedimento.setValor(rs.getDouble("valor"));
                EstadoConsulta estadoNovo = estado.buscaPorId(rs.getInt("estadoId"));
                Consulta consul = consulta.buscaPorID(rs.getInt("consultaId"));
                procedimento.setConsulta(consul);
                procedimento.setEstado(estadoNovo);
                
                Timestamp timestampDataCriacao = Timestamp.valueOf(rs.getString("dataCriacao"));
                LocalDateTime dataCriacao = timestampDataCriacao.toLocalDateTime();
                Timestamp timestampDataModificacao = Timestamp.valueOf(rs.getString("dataModificacao"));
                LocalDateTime dataModificacao = timestampDataModificacao.toLocalDateTime();
                
                Timestamp timestampDiaHorario = Timestamp.valueOf(rs.getString("diaHorario"));
                LocalDateTime diaHorario = timestampDiaHorario.toLocalDateTime();
                procedimento.setDataHora(diaHorario);
                procedimento.setDataCriacao(dataCriacao);
                procedimento.setDataModificacao(dataModificacao);
                
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return procedimento;
    }
    public List<Procedimento> listarProcedimentos(EstadoConsultaDAO estadoDAO, ConsultaDAO consultaDAO){
        String sql = "select * from procedimento";
        Procedimento procNovo = new Procedimento();
        List<Procedimento> procedimentos = new ArrayList<>();
        try(Connection con = new ConnectionFactory().getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                procNovo.setId(rs.getInt("idProcedimento"));
                procNovo.setValor(rs.getDouble("valor"));
                procNovo.setLaudo(rs.getString("laudo"));
                EstadoConsulta es = estadoDAO.buscaPorId(rs.getInt("estadoId"));
                Consulta consul = consultaDAO.buscaPorID(rs.getInt("consultaId"));
                procNovo.setConsulta(consul);
                procNovo.setEstado(es);
                 Timestamp timestampDataCriacao = Timestamp.valueOf(rs.getString("dataCriacao"));
                LocalDateTime dataCriacao = timestampDataCriacao.toLocalDateTime();
                Timestamp timestampDataModificacao = Timestamp.valueOf(rs.getString("dataModificacao"));
                LocalDateTime dataModificacao = timestampDataModificacao.toLocalDateTime();
                
                Timestamp timestampDiaHorario = Timestamp.valueOf(rs.getString("diaHorario"));
                LocalDateTime diaHorario = timestampDiaHorario.toLocalDateTime();
                procNovo.setDataHora(diaHorario);
                procNovo.setDataCriacao(dataCriacao);
                procNovo.setDataModificacao(dataModificacao);
                procedimentos.add(procNovo);
            }
            
        }catch(SQLException e){
            throw new RuntimeException();
        }
        return procedimentos;
    }
    
}
