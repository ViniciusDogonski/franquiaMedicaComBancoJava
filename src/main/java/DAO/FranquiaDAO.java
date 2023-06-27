/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javaBin.Franquia;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javaBin.Pessoa;
import javaBin.TipoPessoa;
import DAO.PessoaDAO;

/**
 *
 * @author bibis
 */
public class FranquiaDAO {

    public Franquia addFranquia(Franquia franquia, PessoaDAO pessoaDAO, List<TipoPessoa> listaDeTiposPessoa) {

        String sql = "insert into franquia "
                + "(nome, CNPJ, cidade, endereco, responsavelId,dataCriacao,dataModificacao)" + " values (?,?,?,?,?,?,?)";

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            // seta os valores
            stmt.setString(1, franquia.getNome());
            stmt.setString(2, franquia.getCnpj());
            stmt.setString(3, franquia.getCidade());
            stmt.setString(4, franquia.getEndereco());
            stmt.setInt(5, franquia.getResponsavel().getID());
            Timestamp timestamp = Timestamp.valueOf(franquia.getDataCriacao());
            java.sql.Date sqlDate = new java.sql.Date(timestamp.getTime());
            java.sql.Time sqlTime = new java.sql.Time(timestamp.getTime());
            stmt.setString(6, sqlDate + " " + sqlTime);
            stmt.setString(7, sqlDate + " " + sqlTime);

            stmt.execute();

            System.out.println("franquia inserida com sucesso.");

            for (TipoPessoa tipoPessoa : listaDeTiposPessoa) {

                if (tipoPessoa.getTipo().equals("DONO FRANQUIA")) {
                    franquia.getResponsavel().setTipoUsuario(tipoPessoa);
                }

            }

            pessoaDAO.alterarPessoa(franquia.getResponsavel());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //na verdade deveria retornar o elemento que foi inserido agora
        return franquia;

    }

    public List<Franquia> listaDeFranquias(List<Pessoa> pessoas) {

        List<Franquia> listaRetorno = new ArrayList<>();

        String sql = "select * from franquia";

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
//nome, endereco,CPF,telefone,login,senha,tipoUsuarioId,dataCriacao,dataModificacao

                int id = rs.getInt("idFranquia");
                String nome = rs.getString("nome");
                String cnpj = rs.getString("CNPJ");
                String cidade = rs.getString("cidade");
                String endereco = rs.getString("endereco");
                int idResp = rs.getInt("responsavelId");
                Timestamp timestampDataCriacao = Timestamp.valueOf(rs.getString("dataCriacao"));
                LocalDateTime dataCriacao = timestampDataCriacao.toLocalDateTime();
                Timestamp timestampDataModificacao = Timestamp.valueOf(rs.getString("dataModificacao"));
                LocalDateTime dataModificacao = timestampDataModificacao.toLocalDateTime();

                Franquia novaFranquia = new Franquia();

                //novaFranquia.setResponsavel(null);
                for(Pessoa p : pessoas){
                    if(p != null){
                        if(p.getID() == idResp){
                            novaFranquia.setResponsavel(p);
                        }
                    }
                }
                novaFranquia.setId(id);
                novaFranquia.setNome(nome);
                novaFranquia.setCnpj(cnpj);
                novaFranquia.setEndereco(endereco);
                novaFranquia.setCidade(cidade);

                novaFranquia.setDataModificacao(dataModificacao);
                novaFranquia.setDataCriacao(dataCriacao);

                listaRetorno.add(novaFranquia);
                
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listaRetorno;

    }

    public Franquia editarFranquia(Franquia franquia) {
        String sql = " UPDATE franquia SET"
                + "       nome = ?,"
                + "       CNPJ = ?,"
                + "       cidade = ?,"
                + "       endereco = ?,"
                + "       responsavelId = ?,"
                + "       dataModificacao = ?"
                + "       WHERE idFranquia = ?";

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, franquia.getNome());
            stmt.setString(2, franquia.getCnpj());
            stmt.setString(3, franquia.getCidade());
            stmt.setString(4, franquia.getEndereco());
            stmt.setInt(5, franquia.getResponsavel().getID());

            Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
            java.sql.Date sqlDate = new java.sql.Date(timestamp.getTime());
            java.sql.Time sqlTime = new java.sql.Time(timestamp.getTime());
            stmt.setString(6, sqlDate + " " + sqlTime);

            stmt.setInt(7, franquia.getId());

            stmt.execute();

            System.out.println("franquia alterada com sucesso.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return franquia;

    }

    public int buscaIdResponsavelFranquia(int code) {
        String sql = "select * from franquia where idFranquia = ?";
        int id = 0;
        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, code);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    id = rs.getInt("responsavelId");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    public void deleteFranquia(int idFranquia, PessoaDAO pessoaDAO) {
        String sql = "DELETE FROM franquia where idFranquia = ?";

        int idResponsavel = this.buscaIdResponsavelFranquia(idFranquia);
        System.out.println(idResponsavel);

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, idFranquia);
            stmt.execute();

            System.out.println("franquia excluída com sucesso.");

            pessoaDAO.alterarTipoPessoaParaNULL(idResponsavel);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Franquia buscaPorID(int code, List<Pessoa> pessoas) {
        Franquia franquia = null;
        
       // String sql= "select * from franquia where idFranquia = ?";
        int id = 0;
        int idResponsavel=0;
        String nome = "";
        String cidade= "";
        String endereco = "";
        String cnpj = "";
        LocalDateTime dtCriacao = LocalDateTime.now();
        LocalDateTime dtModificacao = LocalDateTime.now();
       
        
        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement ps = createPreparedStatement(connection, code); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                franquia = new Franquia();
                //nome, cnpj, cidade, endereco,CPF,dataCriacao,dataModificacao
                id = rs.getInt("idFranquia");
                nome = rs.getString("nome");
                cidade = rs.getString("cidade");
                endereco = rs.getString("endereco");
                cnpj = rs.getString("CNPJ");
                int idResp = rs.getInt("responsavelId");

                Timestamp timestampDataCriacao = Timestamp.valueOf(rs.getString("dataCriacao"));
                dtCriacao= timestampDataCriacao.toLocalDateTime();
                Timestamp timestampDataModificacao = Timestamp.valueOf(rs.getString("dataModificacao"));
                dtModificacao = timestampDataModificacao.toLocalDateTime();

                //pessoaBuscada.setTipoUsuario(null);
                for (Pessoa p : pessoas) {

                    if (p.getID() == idResp) {
                        franquia.setResponsavel(p);
                    }

                }

                franquia.setId(id);
                franquia.setNome(nome);
                franquia.setEndereco(endereco);
                franquia.setCidade(cidade);
                franquia.setCnpj(cnpj);
                franquia.setDataCriacao(dtCriacao);
                franquia.setDataModificacao(dtModificacao);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return franquia;
    }

    private PreparedStatement createPreparedStatement(Connection con, long id) throws SQLException {
        String sql = "select * from franquia where idFranquia = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setLong(1, id);
        return ps;
    }


}
