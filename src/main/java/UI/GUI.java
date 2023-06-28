/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import javaBin.Consulta;
import javaBin.EstadoConsulta;
import javaBin.Medico;
import javaBin.Pessoa;
import javaBin.Franquia;
import javaBin.UnidadeFranquia;
import javaBin.InfoConsulta;
import javaBin.Procedimento;

/**
 *
 * @author Vinicius Augusto
 */
public class GUI {

    Scanner scan = new Scanner(System.in);

    public Pessoa cadastrarPessoa() {

        System.out.println("Por favor, informe os dados:");

        Pessoa pessoa = new Pessoa();

        System.out.print("Nome: ");
        String nome = scan.nextLine();
        pessoa.setNome(nome);

        System.out.print("Endereço: ");
        String endereco = scan.nextLine();
        pessoa.setEndereco(endereco);

        System.out.print("CPF: ");
        String cpf = scan.nextLine();
        pessoa.setCpf(cpf);

        System.out.print("Telefone: ");
        String telefone = scan.nextLine();
        pessoa.setTelefone(telefone);

        System.out.print("Login: ");
        String login = scan.nextLine();
        pessoa.setLogin(login);

        System.out.print("Senha: ");
        String senha = scan.nextLine();
        pessoa.setSenha(senha);

        /*  System.out.print("Tipo de usuário (1-Dono Franquia, 2-Dono Unidade, 3-Administrativo, 4-Médico, 5-Paciente, 6-Administrador): ");
        int tipoUsuario = Integer.parseInt(scan.nextLine());

        switch (tipoUsuario) {
            case 1:
                pessoa.setTipoUsuario(TipoUsuario.DONO_FRANQUIA);
                break;
            case 2:
                pessoa.setTipoUsuario(TipoUsuario.DONO_UNIDADE);
                break;
            case 3:
                pessoa.setTipoUsuario(TipoUsuario.ADMINISTRATIVO);
                break;
            case 4:
                pessoa.setTipoUsuario(TipoUsuario.MEDICO);
                break;
            case 5:
                pessoa.setTipoUsuario(TipoUsuario.PACIENTE);
                break;
            case 6:
                pessoa.setTipoUsuario(TipoUsuario.ADMINISTRADOR);
                break;
            default:
                System.out.print("opçao invalida!");
        }*/
        return pessoa;
    }

    public Medico cadastrarMedico(Pessoa pessoa) {

        System.out.println("Por favor, informe os dados do medico:");

        Medico medico = new Medico();

        System.out.print("CRM:");
        String crm = scan.nextLine();
        medico.setCrm(crm);

        System.out.print("Especialidade: ");
        String especialidade = scan.nextLine();
        medico.setEspecialidade(especialidade);

        medico.setDataCriacao(LocalDateTime.now());
        medico.setDataModificacao(LocalDateTime.now());

        medico.setPessoa(pessoa);

        return medico;
    }

    public Franquia CadastraFranquia(Pessoa responsavel) {
        System.out.println("Por favor, informe os dados da Franquia:");
        Franquia franquia = new Franquia();
        System.out.println("Nome:");
        String nome = scan.nextLine();
        System.out.println("CNPJ:");
        String cnpj = scan.nextLine();
        System.out.println("Cidade:");
        String cidade = scan.nextLine();
        System.out.println("Endereco:");
        String endereco = scan.nextLine();
        franquia.setNome(nome);
        franquia.setCnpj(cnpj);
        franquia.setCidade(cidade);
        franquia.setEndereco(endereco);
        franquia.setResponsavel(responsavel);
        return franquia;

    }

    public UnidadeFranquia CadastraUnidade(Pessoa responsavel, Franquia franquia) {
        System.out.println("Por favor, informe os dados da Unidade:");
        UnidadeFranquia unidade = new UnidadeFranquia();
        System.out.println("Cidade:");
        String cidade = scan.nextLine();
        System.out.println("Endereco:");
        String endereco = scan.nextLine();
        unidade.setCidade(cidade);
        unidade.setEndereco(endereco);
        unidade.setResponsavel(responsavel);
        unidade.setFranquia(franquia);
        return unidade;

    }

    public Consulta cadastrarConsulta(Medico medico, Pessoa paciente, List<EstadoConsulta> estadosConsulta, int idUnidae) {

        System.out.println("Por favor, informe os dados da Consulta:");
        Consulta consulta = new Consulta();

        System.out.print("data da consulta (dd/MM/yyyy):");
        String dataConsulta = scan.nextLine();
        LocalDate data = DateConverter(dataConsulta);

        System.out.print("horario da consulta (HH:mm):");
        String horaConsulta = scan.nextLine();
        LocalTime horario = TimeConverter(horaConsulta);

        LocalDateTime localDateTime = data.atTime(horario);

        consulta.setDataHora(localDateTime);

        //consulta.setDataHora(LocalDateTime.MAX);
        System.out.print("Valor da consulta (10.00):");
        double valor = Double.parseDouble(scan.nextLine());

        //consulta.setEstado(Estados.AGENDADA);
        for (EstadoConsulta estadoConsulta : estadosConsulta) {
            System.out.println(estadoConsulta);
        }

        int idEstado = Integer.parseInt(scan.nextLine());

        EstadoConsulta estado = new EstadoConsulta(idEstado, null);

        consulta.setEstado(estado);

        UnidadeFranquia unidade = new UnidadeFranquia();
        unidade.setId(idUnidae);

        consulta.setMedico(medico);
        consulta.setPaciente(paciente);
        consulta.setValor(valor);
        consulta.setUnidade(unidade);

        return consulta;

    }

    public InfoConsulta cadastraInfo(Consulta consulta) {
        InfoConsulta info = new InfoConsulta();
        System.out.println("Descricao:");
        info.setDescricao(scan.nextLine());
        info.setConsulta(consulta);
        return info;
    }

    public Procedimento cadastraProcedimento(Consulta consulta, EstadoConsulta estado) {
        Procedimento procedimento = new Procedimento();
        System.out.println("Nome:");
        String nome = scan.nextLine();
        System.out.println("Valor");
        Double valor = Double.valueOf(scan.nextLine());
        System.out.print("data do procedimento (dd/MM/yyyy):");
        String dataProc = scan.nextLine();
        LocalDate data = DateConverter(dataProc);

        System.out.print("horario do procedimento (HH:mm):");
        String horaProc = scan.nextLine();
        LocalTime horario = TimeConverter(horaProc);

        LocalDateTime localDateTime = data.atTime(horario);

        procedimento.setDataHora(localDateTime);
        System.out.println("laudo");
        String laudo = scan.nextLine();
        procedimento.setLaudo(laudo);
        procedimento.setNome(nome);
        procedimento.setValor(valor);
        procedimento.setConsulta(consulta);
        procedimento.setEstado(estado);

        return procedimento;
    }

    public int pegaOpcaoLoginCadastro() {

        System.out.println("1 cadastrar");
        System.out.println("2 logar");

        System.out.print("Qual sua opcao ?");
        return Integer.parseInt(scan.nextLine());

    }

    public int pegaOpcaoADM() {
        System.out.println("------ PESSOA------");
        System.out.println("1 cadastrar PESSOA");
        System.out.println("2 mostrar todas PESSOAS");
        System.out.println("3 alterar PESSOA");
        System.out.println("4 excluir pelo id  PESSOAS");
        System.out.println("------ MEDICO------");
        System.out.println("5 cadastrar MEDICO");
        System.out.println("6 mostrar MEDICO");
        System.out.println("7 deletar MEDICO");
        System.out.println("8 editar MEDICO");
        System.out.println("------FRANQUIA------");
        System.out.println("9 cadastrar FRANQUIA");
        System.out.println("10 mostrar FRANQUIA");
        System.out.println("11 deletar FRANQUIA");
        System.out.println("12 editar FRANQUIA");
        System.out.println("------UNIDADE FRANQUIA------");
        System.out.println("13 cadastrar UNIDADE FRANQUIA");
        System.out.println("14 mostrar UNIDADE FRANQUIA");
        System.out.println("15 deletar UNIDADE FRANQUIA");
        System.out.println("16 editar UNIDADE FRANQUIA");
        System.out.println("------CONSULTA------");
        System.out.println("17 cadastrar CONSULTA");
        System.out.println("18 mostrar CONSULTAS");
        System.out.println("19 deletar CONSULTA");
        System.out.println("20 editar CONSULTA");
        System.out.println("------ INFO CONSULTA------");
        System.out.println("21 cadastrar INFO CONSULTA");
        System.out.println("22 mostrar INFO CONSULTAS");
        System.out.println("23 deletar INFO CONSULTA");
        System.out.println("24 editar INFO CONSULTA");
        System.out.println("------ PROCEDIMENTO------");
        System.out.println("25 cadastrar PROCEDIMENTO");
        System.out.println("26 mostrar PROCEDIMENTO");
        System.out.println("27 deletar PROCEDIMENTO");
        System.out.println("28 editar PROCEDIMENTO");
        System.out.println("-------Finaceniro ADM ---------");
        System.out.println("29 - Criar Finaceiro ADM");
        System.out.println("30 - Mostrar finanças Franquia");
        System.out.println("31 - Editar Finaceiro ADM Franquia");
        System.out.println("32 - Deletar Finaceiro ADM Franquia");
        System.out.println("-------Finaceniro MEDICO ---------");
        System.out.println("33 - Criar Finaceiro MEDICO");
        System.out.println("34 - Mostrar finanças MEDICO");
        System.out.println("35 - Editar Finaceiro ADM MEDICO");
        System.out.println("36 - Deletar Finaceiro ADM MEDICO");
        System.out.println("37 - Relatórios FINANCEIRO  Franquia");
        System.out.println("0 sair");

        System.out.print("Qual sua opcao ?");
        return Integer.parseInt(scan.nextLine());

    }

    /*mostragens*/
    public void mostrarPessoas(List<Pessoa> pessoas) {

        for (Pessoa pessoa : pessoas) {
            System.out.println(pessoa);
        }

    }

    public void mostrarMedicos(List<Medico> medicos) {

        for (Medico medico : medicos) {
            System.out.println(medico);
        }

    }

    public void mostrarFranquias(List<Franquia> franquias) {
        for (Franquia f : franquias) {
            System.out.println(f);
        }

    }

    public void mostrarUnidades(List<UnidadeFranquia> unidades) {
        for (UnidadeFranquia u : unidades) {
            System.out.println(u);
        }
    }

    public void mostrarConsultas(List<Consulta> consultas) {

        for (Consulta consulta : consultas) {
            System.out.println(consulta);
        }

    }

    public void mostrarInfoConsultas(List<InfoConsulta> infos) {

        for (InfoConsulta info : infos) {
            System.out.println(info);
        }

    }
    public void mostrarProcedimentos(List<Procedimento> procedimentos){
       for (Procedimento p : procedimentos) {
            System.out.println(p);
        }
    }

    private LocalDate DateConverter(String dataConsulta) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(dataConsulta, formatter);
    }

    private LocalTime TimeConverter(String timeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return LocalTime.parse(timeString, formatter);
    }

}
