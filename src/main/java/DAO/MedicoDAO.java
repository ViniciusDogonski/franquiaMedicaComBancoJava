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
import javaBin.Medico;
import javaBin.TipoPessoa;

/**
 *
 * @author Vinicius Augusto
 */
public class MedicoDAO {

    public Medico addMedico(Medico medico, PessoaDAO pessoaDAO, List<TipoPessoa> listaDeTiposPessoa) {

        String sql = "insert into medico "
                + "(CRM,pessoaId,especialidade,dataCriacao,dataModificacao)" + " values (?,?,?,?,?)";

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            // seta os valores
            stmt.setString(1, medico.getCrm());
            stmt.setInt(2, medico.getPessoa().getID());
            stmt.setString(3, medico.getEspecialidade());

            Timestamp timestamp = Timestamp.valueOf(medico.getDataCriacao());
            java.sql.Date sqlDate = new java.sql.Date(timestamp.getTime());
            java.sql.Time sqlTime = new java.sql.Time(timestamp.getTime());
            stmt.setString(4, sqlDate + " " + sqlTime);
            stmt.setString(5, sqlDate + " " + sqlTime);

            stmt.execute();

            System.out.println("medico inserido com sucesso.");

            for (TipoPessoa tipoPessoa : listaDeTiposPessoa) {

                if (tipoPessoa.getTipo().equals("MEDICO")) {
                    medico.getPessoa().setTipoUsuario(tipoPessoa);
                }

            }

            pessoaDAO.alterarPessoa(medico.getPessoa());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //na verdade deveria retornar o elemento que foi inserido agora
        return medico;

    }

    public List<Medico> listaDeMedicos() {

        List<Medico> listaRetorno = new ArrayList<>();

        String sql = "select * from medico";

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
//nome, endereco,CPF,telefone,login,senha,tipoUsuarioId,dataCriacao,dataModificacao

                int id = rs.getInt("idMedico");
                String crm = rs.getString("CRM");
                String especialidade = rs.getString("especialidade");

                Timestamp timestampDataCriacao = Timestamp.valueOf(rs.getString("dataCriacao"));
                LocalDateTime dataCriacao = timestampDataCriacao.toLocalDateTime();
                Timestamp timestampDataModificacao = Timestamp.valueOf(rs.getString("dataModificacao"));
                LocalDateTime dataModificacao = timestampDataModificacao.toLocalDateTime();

                Medico novoMedico = new Medico();

                novoMedico.setPessoa(null);
                novoMedico.setId(id);
                novoMedico.setCrm(crm);
                novoMedico.setEspecialidade(especialidade);
                novoMedico.setDataModificacao(dataModificacao);
                novoMedico.setDataCriacao(dataCriacao);

                listaRetorno.add(novoMedico);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listaRetorno;

    }

    public int buscaIdDaPessoaMedico(int code) {
        String sql = "select * from medico where idMedico = ?";
        int id = 0;
        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, code);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    id = rs.getInt("pessoaId");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    public void excluirMedico(int id, PessoaDAO pessoaDAO) {

        String sql = "DELETE FROM medico WHERE idMedico = ?";

        int idPessoaMedico = this.buscaIdDaPessoaMedico(id);
        System.out.println(idPessoaMedico);

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.execute();

            System.out.println("medico excluído com sucesso.");

            pessoaDAO.alterarTipoPessoaParaNULL(idPessoaMedico);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Medico alterarMedico(Medico medicoAlterado) {

        String sql = " UPDATE medico SET"
                + "       CRM = ?,"
                + "       especialidade = ?,"
                + "       dataModificacao = ?"
                + "       WHERE idMedico = ?";

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, medicoAlterado.getCrm());
            stmt.setString(2, medicoAlterado.getEspecialidade());

            Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
            java.sql.Date sqlDate = new java.sql.Date(timestamp.getTime());
            java.sql.Time sqlTime = new java.sql.Time(timestamp.getTime());
            stmt.setString(3, sqlDate + " " + sqlTime);

            stmt.setInt(4, medicoAlterado.getId());

            stmt.execute();

            System.out.println("medico alterado com sucesso.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return medicoAlterado;

    }

}
