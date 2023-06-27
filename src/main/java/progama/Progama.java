/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package progama;

import DAO.ConsultaDAO;
import DAO.EstadoConsultaDAO;
import DAO.FranquiaDAO;
import DAO.MedicoDAO;
import DAO.PessoaDAO;
import DAO.TipoPessoaDAO;
import DAO.UnidadeFranquiaDAO;
import UI.GUI;
import connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javaBin.Consulta;
import javaBin.EstadoConsulta;
import javaBin.Medico;
import javaBin.Pessoa;
import javaBin.TipoPessoa;
import javaBin.Franquia;
import javaBin.UnidadeFranquia;

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
    FranquiaDAO franquiaDAO = new FranquiaDAO();
    UnidadeFranquiaDAO unidadeFranquiaDAO = new UnidadeFranquiaDAO();

    EstadoConsultaDAO estadoConsultaDAO = new EstadoConsultaDAO();
    ConsultaDAO consultaDAO = new ConsultaDAO();

    List<TipoPessoa> tipoUserBanco = tipoPessoaDAO.listarTipos();
    List<EstadoConsulta> estadosConsulta = estadoConsultaDAO.listarEstadoConsulta();

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
                case 9:
                    System.out.println("------ CADASTRAR FRANQUIA ------");
                    gui.mostrarPessoas(pessoaDAO.listaDePessoas(tipoUserBanco));
                    System.out.println("Informe o id do Responsavel pela Franquia:");
                    int idResp = 0;
                    idResp = Integer.parseInt(scan.nextLine());
                    Pessoa responsavel = pessoaDAO.buscaPorID(idResp, tipoUserBanco);

                    franquiaDAO.addFranquia(gui.CadastraFranquia(responsavel), pessoaDAO, tipoUserBanco);
                    break;
                case 10:
                    System.out.println("FRANQUIAS:");
                    gui.mostrarFranquias(franquiaDAO.listaDeFranquias(pessoaDAO.listaDePessoas(tipoUserBanco)));
                    break;
                case 11:
                    System.out.println("--------DELETE FRANQUIA:--------");
                    System.out.println("Por favor, informe o id da franquia:");
                    int franquiaId = Integer.parseInt(scan.nextLine());
                    franquiaDAO.deleteFranquia(franquiaId, pessoaDAO);
                    break;

                case 12:
                    System.out.println("--------Editar franquia:-------");
                    System.out.println("Informe o id da Franquia:");
                    int idFranquiaNova = Integer.parseInt(scan.nextLine());
                    System.out.println("Informe o id do responsavel");
                    int idResponsavel = 0;
                    idResponsavel = Integer.parseInt(scan.nextLine());
                    Pessoa responsavelNovo = pessoaDAO.buscaPorID(idResponsavel, tipoUserBanco);
                    Franquia franquiaNova = gui.CadastraFranquia(responsavelNovo);
                    franquiaNova.setId(idFranquiaNova);

                    franquiaDAO.editarFranquia(franquiaNova);
                    break;

                case 13:
                    System.out.println("--------CADASTRAR UNIDADE DE FRANQUIA: ----------");
                    int responsaUnid = 0;
                    gui.mostrarPessoas(pessoaDAO.listaDePessoas(tipoUserBanco));
                    System.out.println("Por favor, informe o id do responsavel:");
                    responsaUnid = Integer.parseInt(scan.nextLine());
                    Pessoa respUnidade = pessoaDAO.buscaPorID(responsaUnid, tipoUserBanco);
                    System.out.println("Por favor, informe o id da franquia:");
                    int franId = 0;
                    franId = Integer.parseInt(scan.nextLine());
                    Franquia franquiaUnid = franquiaDAO.buscaPorID(franId, pessoaDAO.listaDePessoas(tipoUserBanco));
                    UnidadeFranquia unidade = gui.CadastraUnidade(respUnidade, franquiaUnid);
                    System.out.println("endereco: " + unidade.getEndereco());

                    unidadeFranquiaDAO.addUnidade(unidade, pessoaDAO, tipoUserBanco);

                    break;
                case 14:
                    System.out.println("-------MOSTRAR UNIDADES:---------");
                    List<Franquia> franquias = franquiaDAO.listaDeFranquias(pessoaDAO.listaDePessoas(tipoUserBanco));
                    List<Pessoa> pessoas = pessoaDAO.listaDePessoas(tipoUserBanco);
                    gui.mostrarUnidades(unidadeFranquiaDAO.listaDeUnidades(franquias, pessoas));
                    break;
                case 15:
                    System.out.println("--------DELETE UNIDADE --------");
                    System.out.println("Por favor, informe o id da Unidade");
                    int idUnidel = Integer.parseInt(scan.nextLine());
                    unidadeFranquiaDAO.deleteUnidade(idUnidel, pessoaDAO);
                    break;
                case 16:
                    System.out.println("-------EDITAR UNIDADE:");
                    int idUnidade = 0;
                    System.out.println("Por favor, informe o id da unidade");
                    idUnidade = Integer.parseInt(scan.nextLine());
                    gui.mostrarFranquias(franquiaDAO.listaDeFranquias(pessoaDAO.listaDePessoas(tipoUserBanco)));
                    System.out.println("Informe o id da franquia");
                    int idFranquiaUnidNova = Integer.parseInt(scan.nextLine());
                    Franquia fraquiaUnidNova = franquiaDAO.buscaPorID(idFranquiaUnidNova, pessoaDAO.listaDePessoas(tipoUserBanco));
                    gui.mostrarPessoas(pessoaDAO.listaDePessoas(tipoUserBanco));
                    System.out.println("informe o id do responsavel pela unidade:");
                    int idRespUnidNova = Integer.parseInt(scan.nextLine());
                    Pessoa responsaUnidNova = pessoaDAO.buscaPorID(idRespUnidNova, tipoUserBanco);
                    UnidadeFranquia unidadeNova = gui.CadastraUnidade(responsaUnidNova, fraquiaUnidNova);
                    unidadeNova.setId(idUnidade);
                    // System.out.println("endereco:" + unidadeNova.getEndereco() + "cidade:" + unidadeNova.getCidade());
                    unidadeFranquiaDAO.alterarUnidade(unidadeNova);
                    break;
                case 17:
                    System.out.println("----------ADD CONSULTA----------");

                    gui.mostrarPessoas(pessoaDAO.listaDePessoasTipoPaciente(tipoUserBanco));
                    System.out.println("Por favor, informe o id do paciente");
                    int idPaciente = Integer.parseInt(scan.nextLine());

                    Pessoa pessoaPaciente = pessoaDAO.buscaPorID(idPaciente, tipoUserBanco);

                    gui.mostrarMedicos(medicoDAO.listaDeMedicos());
                    System.out.println("Por favor, informe o id do paciente");
                    int idMedicoConsulta = Integer.parseInt(scan.nextLine());

                    Medico medicoConsulta = medicoDAO.buscarMedicoPorID(idMedicoConsulta);

                    List<Pessoa> pessoasList = pessoaDAO.listaDePessoas(tipoUserBanco);
                    gui.mostrarUnidades(unidadeFranquiaDAO.listaDeUnidades(franquiaDAO.listaDeFranquias(pessoasList), pessoasList));
                    System.out.println("Por favor, informe o id da unidade");
                    int idunidade = Integer.parseInt(scan.nextLine());

                    Consulta consultaAdd = gui.cadastrarConsulta(medicoConsulta, pessoaPaciente, estadosConsulta, idunidade);

                    consultaDAO.addConsulta(consultaAdd);

                    break;

                case 18:

                    gui.mostrarConsultas(consultaDAO.listaDeConsultas(estadosConsulta));

                    break;
                case 19:

                    System.out.println("----------DEL CONSULTA----------");
                    System.out.println("Por favor, informe o id da consulta");
                    int idConsulta = Integer.parseInt(scan.nextLine());

                    consultaDAO.excluirConsulta(idConsulta);

                    break;
                case 20:

                    System.out.println("----------EDIT CONSULTA----------");

                    System.out.println("Por favor, informe o id do consulta");
                    int idConsultaEdit = Integer.parseInt(scan.nextLine());

                    Consulta consultaAchada = consultaDAO.buscaPorID(idConsultaEdit);

                    gui.mostrarPessoas(pessoaDAO.listaDePessoasTipoPaciente(tipoUserBanco));
                    System.out.println("Por favor, informe o id do paciente");
                    int idPacienteConsulta = Integer.parseInt(scan.nextLine());

                    Pessoa pessoaPacienteConsulta = pessoaDAO.buscaPorID(idPacienteConsulta, tipoUserBanco);

                    gui.mostrarMedicos(medicoDAO.listaDeMedicos());
                    System.out.println("Por favor, informe o id do paciente");
                    int idMedicoConsultaEdit = Integer.parseInt(scan.nextLine());

                    Medico medicoConsultaAchado = medicoDAO.buscarMedicoPorID(idMedicoConsultaEdit);

                    List<Pessoa> pessoasListedit = pessoaDAO.listaDePessoas(tipoUserBanco);
                    gui.mostrarUnidades(unidadeFranquiaDAO.listaDeUnidades(franquiaDAO.listaDeFranquias(pessoasListedit), pessoasListedit));
                    System.out.println("Por favor, informe o id da unidade");
                    int idunidadeedit = Integer.parseInt(scan.nextLine());

                    Consulta consultaEdit = gui.cadastrarConsulta(medicoConsultaAchado, pessoaPacienteConsulta, estadosConsulta, idunidadeedit);
                    consultaEdit.setId(consultaAchada.getId());
                    consultaEdit.setDataCriacao(consultaAchada.getDataCriacao());
                    consultaEdit.setDataModificacao(LocalDateTime.now());

                    consultaDAO.alterarConsulta(consultaEdit);

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
