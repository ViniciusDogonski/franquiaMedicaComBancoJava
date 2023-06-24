/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package progama;

import DAO.MedicoDAO;
import DAO.PessoaDAO;
import DAO.TipoPessoaDAO;
import UI.GUI;
import connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javaBin.Medico;
import javaBin.Pessoa;
import javaBin.TipoPessoa;

/**
 *
 * @author Vinicius Augusto
 */
public class Progama {
    
    ConnectionFactory c = new ConnectionFactory();
    TipoPessoaDAO tipoPessoaDAO = new TipoPessoaDAO();
    Connection connection = c.getConnection();
    
    Scanner scan = new Scanner(System.in);
    GUI gui = new GUI();
    PessoaDAO pessoaDAO = new PessoaDAO();
    MedicoDAO medicoDAO = new MedicoDAO();
    
    List<TipoPessoa> tipoUserBanco = tipoPessoaDAO.listarTipos();
    
    public Progama() {
        this.inicioMenu();
    }
    
    public void inicioMenu() {

        /* Pessoa pessoaTest = new Pessoa();
        pessoaTest.setId(13);
        pessoaTest.setNome("testAlter");
        pessoaTest.setCpf("12345678000");
        pessoaTest.setLogin("dsad");
        pessoaTest.setSenha("123");
        pessoaTest.setEndereco("erede");
        pessoaTest.setTelefone("123123");

        pessoaDAO.alterarPessoa(pessoaTest);*/
 /*pessoaDAO.addPessoa(pessoaTest);*/
 /*List<Pessoa> retornolistaesoa = pessoaDAO.listaDePessoas(tipoUserBanco);

        for (Pessoa pessoa : retornolistaesoa) {
            System.out.println(pessoa);
        }*/
        int op = gui.pegaOpcaoLoginCadastro();
        
        switch (op) {
            case 1:
                System.out.println("------ CADASTRO ------");
                Pessoa pessoaCadastro = gui.cadastrarPessoa();
                for (TipoPessoa tipoPessoa : tipoUserBanco) {
                    if (tipoPessoa.getTipo().equals("PACIENTE")) {
                        pessoaCadastro.setTipoUsuario(tipoPessoa);
                    }
                }
                
                System.out.println(pessoaCadastro);
                pessoaDAO.addPessoaComTipo(pessoaCadastro);
                
                inicioMenu();
                break;
            case 2:
                System.out.println("------ LOGIN ------");
                System.out.print("login:");
                String log = scan.nextLine();
                System.out.print("senha:");
                String senha = scan.nextLine();
                login(log, senha);
                break;
            default:
                throw new AssertionError();
        }
        
    }
    
    public void login(String log, String senha) {
        
        Pessoa pessoaAchada = pessoaDAO.acharPessoaPorLoginSenha(log, senha, tipoUserBanco);
        
        if (pessoaAchada == null) {
            System.out.println("Login inválido.");
            return;
        } else {
            System.out.println(pessoaAchada);
            
            switch (pessoaAchada.getTipoUsuario().getTipo()) {
                case "PACIENTE":
                    System.out.println("menu PACIENTE");
                    break;
                case "MEDICO":
                    System.out.println("menu MEDICO");
                    break;
                case "DONO FRANQUIA":
                    System.out.println("DONO FRANQUIA");
                    break;
                case "DONO UNIDADE":
                    System.out.println("DONO UNIDADE");
                    break;
                case "ADMINISTRATIVO":
                    System.out.println("menu ADMINISTRATIVO");
                    break;
                case "ADM":
                    System.out.println("menu ADM");
                    admMenu();
                    break;
                
                default:
                    System.out.println("Tipo de usuário inválido.");
            }
            
        }
    }
    
    public void admMenu() {
        int opcaoUsuario;
        
        do {
            opcaoUsuario = gui.pegaOpcaoADM();
            
            switch (opcaoUsuario) {
                case 1:
                    System.out.println("------ Criar Pessoa ------");
                    Pessoa p = gui.cadastrarPessoa();
                    pessoaDAO.addPessoaSemTipo(p);
                    break;
                case 2:
                    List<Pessoa> retornolistaesoa = pessoaDAO.listaDePessoas(tipoUserBanco);
                    gui.mostrarPessoas(retornolistaesoa);
                    break;
                case 3:
                    System.out.println("------ Editar Pessoa------");
                    int idEdit = 0;
                    
                    System.out.print("id da pessoa:");
                    idEdit = Integer.parseInt(scan.nextLine());
                    
                    Pessoa pessoaAchada = pessoaDAO.buscaPorID(idEdit, tipoUserBanco);
                    Pessoa pessoaEdit = gui.cadastrarPessoa();
                    
                    pessoaEdit.setId(pessoaAchada.getID());
                    pessoaEdit.setTipoUsuario(pessoaAchada.getTipoUsuario());
                    
                    pessoaDAO.alterarPessoa(pessoaEdit);
                    
                    break;
                case 4:
                    
                    System.out.println("------ Delete Pessoa------");
                    
                    System.out.print("id da pessoa:");
                    int idDel = Integer.parseInt(scan.nextLine());
                    
                    pessoaDAO.excluirPessoa(idDel);
                    
                    break;
                case 5:
                    System.out.println("------ Criar Medico ------");
                    
                    List<Pessoa> listaNull = pessoaDAO.buscaPorTipoUserNULL();
                    gui.mostrarPessoas(listaNull);
                    
                    System.out.print("id da pessoa:");
                    int idPessoaMedico = Integer.parseInt(scan.nextLine());
                    
                    Pessoa pessoaMedicoAchada = pessoaDAO.buscaPorID(idPessoaMedico, tipoUserBanco);
                    System.out.println(pessoaMedicoAchada);
                    
                    Medico medicoCriado = gui.cadastrarMedico(pessoaMedicoAchada);
                    
                    medicoDAO.addMedico(medicoCriado, pessoaDAO, tipoUserBanco);
                    
                    break;
                case 6:
                    gui.mostrarMedicos(medicoDAO.listaDeMedicos());
                    
                    break;
                case 7:
                    System.out.println("------ excluir Medico ------");
                    
                    System.out.print("id da Medico:");
                    int idMedico = Integer.parseInt(scan.nextLine());
                    
                    medicoDAO.excluirMedico(idMedico, pessoaDAO);
                    
                    break;
                case 8:
                    System.out.println("------ editar Medico ------");
                    
                    System.out.print("id da Medico:");
                    int idMedicoEdit = Integer.parseInt(scan.nextLine());
                    
                    System.out.print("id da pessoa:");
                    int idPessoaMedicoEdit = Integer.parseInt(scan.nextLine());
                    
                    Pessoa pessoaMedicoAchadaEdit = pessoaDAO.buscaPorID(idPessoaMedicoEdit, tipoUserBanco);
                    System.out.println(pessoaMedicoAchadaEdit);
                    
                    Medico medicoCriadoEdit = gui.cadastrarMedico(pessoaMedicoAchadaEdit);
                    medicoCriadoEdit.setId(idMedicoEdit);
                    
                    medicoDAO.alterarMedico(medicoCriadoEdit);
                    
                    break;
                case 0:
                    inicioMenu();
                    break;
                default:
                    throw new AssertionError();
            }
            
        } while (opcaoUsuario != 0);
    }
    
    public static void main(String[] args) {
        new Progama();
    }
    
}
