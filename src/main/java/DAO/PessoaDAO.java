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
import javaBin.Pessoa;
import javaBin.TipoPessoa;

/**
 *
 * @author Vinicius Augusto
 */
public class PessoaDAO {
    //INSERT INTO franquia_medica.pessoa (nome, endereco,CPF,telefone,login,senha,tipoUsuarioId,dataCriacao,dataModificacao) VALUES ('test', 'rua das palmeiras',12345678990,33169770,'test','123',null,'2023-03-15 00:00:00.0','2023-03-15 00:00:00.0');

    public Pessoa addPessoaSemTipo(Pessoa pessoaAdicionada) {

        String sql = "INSERT INTO pessoa"
                + "(nome, endereco,CPF,telefone,login,senha,tipoUsuarioId,dataCriacao,dataModificacao)"
                + "VALUES (?, ?,?,?,?,?,null,?,?)";

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            // seta os valores

            stmt.setString(1, pessoaAdicionada.getNome());
            stmt.setString(2, pessoaAdicionada.getEndereco());
            stmt.setString(3, pessoaAdicionada.getCpf());
            stmt.setString(4, pessoaAdicionada.getTelefone());
            stmt.setString(5, pessoaAdicionada.getLogin());
            stmt.setString(6, pessoaAdicionada.getSenha());

            Timestamp timestamp = Timestamp.valueOf(pessoaAdicionada.getDataCriacao());
            java.sql.Date sqlDate = new java.sql.Date(timestamp.getTime());
            java.sql.Time sqlTime = new java.sql.Time(timestamp.getTime());
            stmt.setString(7, sqlDate + " " + sqlTime);
            stmt.setString(8, sqlDate + " " + sqlTime);

            stmt.execute();

            System.out.println("pessoa inserido com sucesso.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return pessoaAdicionada;

    }

    
        public List<Pessoa> listaDePessoasTipoPaciente(List<TipoPessoa> tipoUserBanco) {

        List<Pessoa> listaRetorno = new ArrayList<>();

        String sql = "select * from pessoa where tipoUsuarioId = 1";

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
//nome, endereco,CPF,telefone,login,senha,tipoUsuarioId,dataCriacao,dataModificacao

                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String endereco = rs.getString("endereco");
                String cpf = rs.getString("CPF");
                String telefone = rs.getString("telefone");
                String login = rs.getString("login");
                String senha = rs.getString("senha");
                int idTipoUser = rs.getInt("tipoUsuarioId");

                Timestamp timestampDataCriacao = Timestamp.valueOf(rs.getString("dataCriacao"));
                LocalDateTime dataCriacao = timestampDataCriacao.toLocalDateTime();
                Timestamp timestampDataModificacao = Timestamp.valueOf(rs.getString("dataModificacao"));
                LocalDateTime dataModificacao = timestampDataModificacao.toLocalDateTime();

                Pessoa novaPessoa = new Pessoa();

                novaPessoa.setTipoUsuario(null);

                for (TipoPessoa tipoPessoa : tipoUserBanco) {

                    if (tipoPessoa.getId() == idTipoUser) {
                        novaPessoa.setTipoUsuario(tipoPessoa);
                    }

                }

                novaPessoa.setId(id);
                novaPessoa.setNome(nome);
                novaPessoa.setTelefone(telefone);
                novaPessoa.setEndereco(endereco);
                novaPessoa.setCpf(cpf);
                novaPessoa.setLogin(login);
                novaPessoa.setSenha(senha);
                novaPessoa.setDataModificacao(dataModificacao);
                novaPessoa.setDataCriacao(dataCriacao);

                listaRetorno.add(novaPessoa);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listaRetorno;

    }
    
    public Pessoa addPessoaComTipo(Pessoa pessoaAdicionada) {

        String sql = "INSERT INTO pessoa"
                + "(nome, endereco,CPF,telefone,login,senha,tipoUsuarioId,dataCriacao,dataModificacao)"
                + "VALUES (?,?,?,?,?,?,?,?,?)";

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            // seta os valores

            stmt.setString(1, pessoaAdicionada.getNome());
            stmt.setString(2, pessoaAdicionada.getEndereco());
            stmt.setString(3, pessoaAdicionada.getCpf());
            stmt.setString(4, pessoaAdicionada.getTelefone());
            stmt.setString(5, pessoaAdicionada.getLogin());
            stmt.setString(6, pessoaAdicionada.getSenha());

            stmt.setInt(7, pessoaAdicionada.getTipoUsuario().getId());

            Timestamp timestamp = Timestamp.valueOf(pessoaAdicionada.getDataCriacao());
            java.sql.Date sqlDate = new java.sql.Date(timestamp.getTime());
            java.sql.Time sqlTime = new java.sql.Time(timestamp.getTime());
            stmt.setString(8, sqlDate + " " + sqlTime);
            stmt.setString(9, sqlDate + " " + sqlTime);

            stmt.execute();

            System.out.println("pessoa inserido com sucesso.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return pessoaAdicionada;

    }

    public Pessoa acharPessoaPorLoginSenha(String login, String senha, List<TipoPessoa> tipoUserBanco) {

        String sql = "SELECT * FROM pessoa WHERE login = ? AND senha = ?";

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql);) {

            stmt.setString(1, login);
            stmt.setString(2, senha);

            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                System.out.println("Login bem-sucedido!");

                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                String endereco = resultSet.getString("endereco");
                String cpf = resultSet.getString("CPF");
                String telefone = resultSet.getString("telefone");
                String loginAchado = resultSet.getString("login");
                String senhaAchada = resultSet.getString("senha");
                int idTipoUser = resultSet.getInt("tipoUsuarioId");

                Timestamp timestampDataCriacao = Timestamp.valueOf(resultSet.getString("dataCriacao"));
                LocalDateTime dataCriacao = timestampDataCriacao.toLocalDateTime();
                Timestamp timestampDataModificacao = Timestamp.valueOf(resultSet.getString("dataModificacao"));
                LocalDateTime dataModificacao = timestampDataModificacao.toLocalDateTime();

                Pessoa novaPessoa = new Pessoa();
                novaPessoa.setTipoUsuario(null);

                for (TipoPessoa tipoPessoa : tipoUserBanco) {

                    if (tipoPessoa.getId() == idTipoUser) {
                        novaPessoa.setTipoUsuario(tipoPessoa);
                    }

                }

                novaPessoa.setId(id);
                novaPessoa.setNome(nome);
                novaPessoa.setTelefone(telefone);
                novaPessoa.setEndereco(endereco);
                novaPessoa.setCpf(cpf);
                novaPessoa.setLogin(login);
                novaPessoa.setSenha(senha);
                novaPessoa.setDataModificacao(dataModificacao);
                novaPessoa.setDataCriacao(dataCriacao);

                return novaPessoa;
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Pessoa> listaDePessoas(List<TipoPessoa> tipoUserBanco) {

        List<Pessoa> listaRetorno = new ArrayList<>();

        String sql = "select * from pessoa";

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
//nome, endereco,CPF,telefone,login,senha,tipoUsuarioId,dataCriacao,dataModificacao

                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String endereco = rs.getString("endereco");
                String cpf = rs.getString("CPF");
                String telefone = rs.getString("telefone");
                String login = rs.getString("login");
                String senha = rs.getString("senha");
                int idTipoUser = rs.getInt("tipoUsuarioId");

                Timestamp timestampDataCriacao = Timestamp.valueOf(rs.getString("dataCriacao"));
                LocalDateTime dataCriacao = timestampDataCriacao.toLocalDateTime();
                Timestamp timestampDataModificacao = Timestamp.valueOf(rs.getString("dataModificacao"));
                LocalDateTime dataModificacao = timestampDataModificacao.toLocalDateTime();

                Pessoa novaPessoa = new Pessoa();

                novaPessoa.setTipoUsuario(null);

                for (TipoPessoa tipoPessoa : tipoUserBanco) {

                    if (tipoPessoa.getId() == idTipoUser) {
                        novaPessoa.setTipoUsuario(tipoPessoa);
                    }

                }

                novaPessoa.setId(id);
                novaPessoa.setNome(nome);
                novaPessoa.setTelefone(telefone);
                novaPessoa.setEndereco(endereco);
                novaPessoa.setCpf(cpf);
                novaPessoa.setLogin(login);
                novaPessoa.setSenha(senha);
                novaPessoa.setDataModificacao(dataModificacao);
                novaPessoa.setDataCriacao(dataCriacao);

                listaRetorno.add(novaPessoa);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listaRetorno;

    }

    /*public Pessoa buscarPorID(int idPessoa, List<TipoPessoa> tipoUserBanco) {

        String sql = "select * from pessoa where id = ?";
        Pessoa novaPessoa = new Pessoa();
        return novaPessoa;
    }*/
    public Pessoa buscaPorID(int code, List<TipoPessoa> tipoUserBanco) {
        Pessoa pessoaBuscada = null;
        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement ps = createPreparedStatement(connection, code); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                pessoaBuscada = new Pessoa();
                //nome, endereco,CPF,telefone,login,senha,tipoUsuarioId,dataCriacao,dataModificacao
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String endereco = rs.getString("endereco");
                String cpf = rs.getString("CPF");
                String telefone = rs.getString("telefone");
                String login = rs.getString("login");
                String senha = rs.getString("senha");
                int idTipoUser = rs.getInt("tipoUsuarioId");

                Timestamp timestampDataCriacao = Timestamp.valueOf(rs.getString("dataCriacao"));
                LocalDateTime dataCriacao = timestampDataCriacao.toLocalDateTime();
                Timestamp timestampDataModificacao = Timestamp.valueOf(rs.getString("dataModificacao"));
                LocalDateTime dataModificacao = timestampDataModificacao.toLocalDateTime();

                pessoaBuscada.setTipoUsuario(null);

                for (TipoPessoa tipoPessoa : tipoUserBanco) {

                    if (tipoPessoa.getId() == idTipoUser) {
                        System.out.println("achou");
                        pessoaBuscada.setTipoUsuario(tipoPessoa);
                    }

                }

                pessoaBuscada.setId(id);
                pessoaBuscada.setNome(nome);
                pessoaBuscada.setEndereco(endereco);
                pessoaBuscada.setTelefone(telefone);
                pessoaBuscada.setCpf(cpf);
                pessoaBuscada.setLogin(login);
                pessoaBuscada.setSenha(senha);
                pessoaBuscada.setDataCriacao(dataCriacao);
                pessoaBuscada.setDataModificacao(dataModificacao);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pessoaBuscada;
    }

    private PreparedStatement createPreparedStatement(Connection con, long id) throws SQLException {
        String sql = "select * from pessoa where id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setLong(1, id);
        return ps;
    }

    public List<Pessoa> buscaPorTipoUserNULL() {

        String sql = "SELECT * FROM pessoa where tipoUsuarioId is null";
        List<Pessoa> listaRetorno = new ArrayList<>();

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
//nome, endereco,CPF,telefone,login,senha,tipoUsuarioId,dataCriacao,dataModificacao

                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String endereco = rs.getString("endereco");
                String cpf = rs.getString("CPF");
                String telefone = rs.getString("telefone");
                String login = rs.getString("login");
                String senha = rs.getString("senha");
                int idTipoUser = rs.getInt("tipoUsuarioId");

                Timestamp timestampDataCriacao = Timestamp.valueOf(rs.getString("dataCriacao"));
                LocalDateTime dataCriacao = timestampDataCriacao.toLocalDateTime();
                Timestamp timestampDataModificacao = Timestamp.valueOf(rs.getString("dataModificacao"));
                LocalDateTime dataModificacao = timestampDataModificacao.toLocalDateTime();

                Pessoa novaPessoa = new Pessoa();

                novaPessoa.setTipoUsuario(null);

                novaPessoa.setId(id);
                novaPessoa.setNome(nome);
                novaPessoa.setTelefone(telefone);
                novaPessoa.setEndereco(endereco);
                novaPessoa.setCpf(cpf);
                novaPessoa.setLogin(login);
                novaPessoa.setSenha(senha);
                novaPessoa.setDataModificacao(dataModificacao);
                novaPessoa.setDataCriacao(dataCriacao);

                listaRetorno.add(novaPessoa);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listaRetorno;
    }

    public void alterarTipoPessoaParaNULL(int id) {
        
        String sql = " UPDATE pessoa SET"
                + "        tipoUsuarioId = NULL,"
                + "        dataModificacao = ?"
                + "        WHERE id = ?";

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            /*stmt.setString(1, elemento.getEndereco());
            stmt.setLong(2, elemento.getId());*/
            Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
            java.sql.Date sqlDate = new java.sql.Date(timestamp.getTime());
            java.sql.Time sqlTime = new java.sql.Time(timestamp.getTime());
            stmt.setString(1, sqlDate + " " + sqlTime);
            stmt.setInt(2, id);

            stmt.execute();

            System.out.println("pessoa alterado com sucesso NULL.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Pessoa alterarPessoa(Pessoa pessoaAlterada) {
        /*
        UPDATE pessoa
         SET nome = 'Novo Nome',
        endereco = 'Nova Endereço',
         CPF = 'Novo CPF',
        telefone = 'Novo Telefone',
        login = 'Novo Login',
        senha = 'Nova Senha',
        dataModificacao = NOW()
        WHERE id = 1;
         */

        String sql = " UPDATE pessoa"
                + "         SET nome = ?,"
                + "        endereco = ?,"
                + "         CPF = ?,"
                + "        telefone = ?,"
                + "        login = ?,"
                + "        senha = ?,"
                + "        tipoUsuarioId = ?,"
                + "        dataModificacao = ?"
                + "        WHERE id = ?";

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            /*stmt.setString(1, elemento.getEndereco());
            stmt.setLong(2, elemento.getId());*/
            stmt.setString(1, pessoaAlterada.getNome());
            stmt.setString(2, pessoaAlterada.getEndereco());
            stmt.setString(3, pessoaAlterada.getCpf());
            stmt.setString(4, pessoaAlterada.getTelefone());
            stmt.setString(5, pessoaAlterada.getLogin());
            stmt.setString(6, pessoaAlterada.getSenha());

            if (pessoaAlterada.getTipoUsuario() == null) {
                stmt.setString(7, null);
            } else {
                stmt.setInt(7, pessoaAlterada.getTipoUsuario().getId());
            }

            Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
            java.sql.Date sqlDate = new java.sql.Date(timestamp.getTime());
            java.sql.Time sqlTime = new java.sql.Time(timestamp.getTime());
            stmt.setString(8, sqlDate + " " + sqlTime);
            stmt.setInt(9, pessoaAlterada.getId());

            stmt.execute();

            System.out.println("pessoa alterado com sucesso.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public void excluirPessoa(int id) {

        String sql = "DELETE FROM pessoa WHERE id = ?";

        try (Connection connection = new ConnectionFactory().getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);

            stmt.execute();

            System.out.println("pessoa excluído com sucesso.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
