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

import connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javaBin.Franquia;
import javaBin.TipoPessoa;
import javaBin.Pessoa;
import javaBin.UnidadeFranquia;

/**
 *
 * @author bibis
 */
public class UnidadeFranquiaDAO {

    public UnidadeFranquia addUnidade(UnidadeFranquia unidade, PessoaDAO pessoaDAO, List<TipoPessoa> listaDeTiposPessoa) {

        String sql = "insert into unidade "
                + "(franquiaId,cidade, endereco, responsavelId,dataCriacao,dataModificacao)" + " values (?,?,?,?,?,?)";

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            // seta os valores
            stmt.setInt(1, unidade.getFranquia().getId());
            stmt.setString(2, unidade.getCidade());
            stmt.setString(3, unidade.getEndereco());
            stmt.setInt(4, unidade.getResponsavel().getID());

            Timestamp timestamp = Timestamp.valueOf(unidade.getDataCriacao());
            java.sql.Date sqlDate = new java.sql.Date(timestamp.getTime());
            java.sql.Time sqlTime = new java.sql.Time(timestamp.getTime());
            stmt.setString(5, sqlDate + " " + sqlTime);
            stmt.setString(6, sqlDate + " " + sqlTime);

            stmt.execute();

            System.out.println("unidade inserida com sucesso.");

            for (TipoPessoa tipoPessoa : listaDeTiposPessoa) {

                if (tipoPessoa.getTipo().equals("DONO UNIDADE")) {
                    unidade.getResponsavel().setTipoUsuario(tipoPessoa);
                }

            }

            pessoaDAO.alterarPessoa(unidade.getResponsavel());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //na verdade deveria retornar o elemento que foi inserido agora
        return unidade;

    }

    public List<UnidadeFranquia> listaDeUnidades(List<Franquia> franquias, List<Pessoa> pessoas) {

        List<UnidadeFranquia> listaRetorno = new ArrayList<>();

        String sql = "select * from unidade";

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
//nome, endereco,CPF,telefone,login,senha,tipoUsuarioId,dataCriacao,dataModificacao

                int id = rs.getInt("idUnidade");
                int idFranquia = rs.getInt("franquiaId");
                int idResponsavel = rs.getInt("responsavelId");
                String cidade = rs.getString("cidade");
                String endereco = rs.getString("endereco");

                Timestamp timestampDataCriacao = Timestamp.valueOf(rs.getString("dataCriacao"));
                LocalDateTime dataCriacao = timestampDataCriacao.toLocalDateTime();
                Timestamp timestampDataModificacao = Timestamp.valueOf(rs.getString("dataModificacao"));
                LocalDateTime dataModificacao = timestampDataModificacao.toLocalDateTime();

                UnidadeFranquia novaUnidade = new UnidadeFranquia();

                novaUnidade.setResponsavel(null);

                novaUnidade.setId(id);
                for (Franquia franquia : franquias) {
                    if (franquia != null) {
                        if (franquia.getId() == idFranquia) {
                            novaUnidade.setFranquia(franquia);
                        }
                    }
                }

                for (Pessoa pessoa : pessoas) {
                    if (pessoa != null) {
                        if (pessoa.getID() == idResponsavel) {
                            novaUnidade.setResponsavel(pessoa);
                        }
                    }
                }
                novaUnidade.setEndereco(endereco);
                novaUnidade.setCidade(cidade);

                novaUnidade.setDataModificacao(dataModificacao);
                novaUnidade.setDataCriacao(dataCriacao);

                listaRetorno.add(novaUnidade);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listaRetorno;

    }

    public UnidadeFranquia alterarUnidade(UnidadeFranquia unidadeAlterada) {
        String sql = " UPDATE unidade SET"
                + "       franquiaId = ?,"
                + "       responsavelId = ?,"
                + "       cidade = ?, "
                + "       endereco = ?,"
                + "       dataModificacao = ?"
                + "       WHERE idUnidade = ?";
        System.out.println(unidadeAlterada);
        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, unidadeAlterada.getFranquia().getId());
            stmt.setInt(2, unidadeAlterada.getResponsavel().getID());
            stmt.setString(3, unidadeAlterada.getCidade());
            stmt.setString(4, unidadeAlterada.getEndereco());

            Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
            java.sql.Date sqlDate = new java.sql.Date(timestamp.getTime());
            java.sql.Time sqlTime = new java.sql.Time(timestamp.getTime());
            stmt.setString(5, sqlDate + " " + sqlTime);

            stmt.setInt(6, unidadeAlterada.getId());

            stmt.execute();

            System.out.println("unidade alterada com sucesso.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return unidadeAlterada;

    }
    public  UnidadeFranquia buscarUnidade(int code, List<Franquia> franquias, List<Pessoa> pessoas){
        String sql= "select * from unidade where idUnidade = ?";
        int id = 0;
        int franquiaId =0;
        int idResponsavel=0;
        String cidade= "";
        String endereco = "";
        LocalDateTime dtCriacao = LocalDateTime.now();
        LocalDateTime dtModificacao = LocalDateTime.now();
       
        
        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, code);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    id = rs.getInt("pessoaId");
                    franquiaId = rs.getInt("franquiaId");
                    idResponsavel = rs.getInt("resposavelId");
                    cidade = rs.getString("cidade");
                    endereco = rs.getString("endereco");
                     Timestamp timestampDataCriacao = Timestamp.valueOf(rs.getString("dataCriacao"));
                    dtCriacao = timestampDataCriacao.toLocalDateTime();
                    Timestamp timestampDataModificacao = Timestamp.valueOf(rs.getString("dataModificacao"));
                    dtModificacao = timestampDataModificacao.toLocalDateTime();
                    
                }
                
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        UnidadeFranquia unidade = new UnidadeFranquia();
                unidade.setId(id);
                unidade.setCidade(cidade);
                unidade.setEndereco(endereco);
                unidade.setDataCriacao(dtCriacao);
                unidade.setDataModificacao(dtModificacao);
                for(Franquia franquia : franquias){
                    if(franquia!=null){
                        if(franquia.getId() == franquiaId){
                            unidade.setFranquia(franquia);
                        }
                    }
                }
                for(Pessoa p  : pessoas){
                    if(p != null){
                        if(p.getId() == idResponsavel){
                            unidade.setResponsavel(p);
                        }
                    }
                }
                
        return unidade;
    
    }
    public int BuscarIdResponsavelUnidade(int idUnidade, List<Franquia> franquias){
        String sql = "select * from unidade where idUnidade = ?";
        int id = 0;
        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, idUnidade);
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
    public int buscaIdResponsavelUnidade(int code) {
        String sql = "select * from unidade where idUnidade = ?";
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
    public void deleteUnidade(int idUnidade,PessoaDAO pessoaDAO){
        String sql = "DELETE FROM unidade where idUnidade = ?";
        int idResponsavel = this.buscaIdResponsavelUnidade(idUnidade);
 

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, idUnidade);
            stmt.execute();

            System.out.println("unidade excluída com sucesso.");

            pessoaDAO.alterarTipoPessoaParaNULL(idResponsavel);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    /*public void excluiUnidade(int idUnidade, List<Franquia> franquias){
        String sql = "drop from unidade where idUnidade = ?";
        
    }*/

}
