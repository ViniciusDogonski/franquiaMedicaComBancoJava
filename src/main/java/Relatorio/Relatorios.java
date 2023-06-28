/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Relatorio;

import com.itextpdf.text.BaseColor;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import connection.ConnectionFactory;
import java.io.File;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author Vinicius Augusto
 */
public class Relatorios {

    String caminhoArquivo = "pdf/";
    private Connection connection;

    public Relatorios() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public List<String> gerarRelatorioUnidade(int idUnidade) {
        List<String> relatorio = new ArrayList<>();

        String sql = "SELECT "
                + "YEAR(dataCriacao) AS ano, "
                + "MONTH(dataCriacao) AS mes, "
                + "SUM(CASE WHEN tipoMovimentoId = 1 THEN valor ELSE 0 END) AS entradas, "
                + "SUM(CASE WHEN tipoMovimentoId = 2 THEN valor ELSE 0 END) AS saidas, "
                + "SUM(CASE WHEN movimentoId = 4 THEN valor ELSE 0 END) AS saidas_medico "
                + "FROM franquia_medica.financeiroadm "
                + "WHERE unidadeId = ? "
                + "GROUP BY YEAR(dataCriacao), MONTH(dataCriacao) "
                + "ORDER BY YEAR(dataCriacao), MONTH(dataCriacao)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idUnidade);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int ano = resultSet.getInt("ano");
                int mes = resultSet.getInt("mes");
                double entradas = resultSet.getDouble("entradas");
                double saidas = resultSet.getDouble("saidas");
                double saidasMedico = resultSet.getDouble("saidas_medico");

                String linha = "Ano: " + ano + " Mês: " + mes
                        + " Entradas: " + entradas + " Saídas: " + saidas
                        + " Saídas Médico: " + saidasMedico;

                //System.out.println(linha);
                relatorio.add(linha);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro relatorio", e);
        }

        relatorioGene(relatorio, "RelatorioUnidade" + idUnidade + "_" + LocalDate.now());

        return relatorio;
    }

    public ArrayList<String> gerarRelatorioFranquia(int franquiaId) {
        ArrayList<String> relatorio = new ArrayList<>();

        String sql = "SELECT "
                + "YEAR(dataCriacao) AS ano, "
                + "MONTH(dataCriacao) AS mes, "
                + "SUM(CASE WHEN tipoMovimentoId = 1 THEN valor ELSE 0 END) AS entradas, "
                + "SUM(CASE WHEN tipoMovimentoId = 2 THEN valor ELSE 0 END) AS saidas, "
                + "SUM(CASE WHEN movimentoId = 4 THEN valor ELSE 0 END) AS saidas_medico "
                + "FROM franquia_medica.financeiroadm "
                + "WHERE unidadeId IN (SELECT idUnidade FROM franquia_medica.unidade WHERE franquiaId = ?) "
                + "GROUP BY YEAR(dataCriacao), MONTH(dataCriacao) "
                + "ORDER BY YEAR(dataCriacao), MONTH(dataCriacao)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, franquiaId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int ano = resultSet.getInt("ano");
                int mes = resultSet.getInt("mes");
                double entradas = resultSet.getDouble("entradas");
                double saidas = resultSet.getDouble("saidas");
                double saidasMedico = resultSet.getDouble("saidas_medico");

                String linha = "Ano: " + ano + " Mês: " + mes
                        + " Entradas: " + entradas + " Saídas: " + saidas
                        + " Saídas Médico: " + saidasMedico;

                System.out.println(linha);

                relatorio.add(linha);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro relatorio", e);
        }

        relatorioGene(relatorio, "RelatorioUnidade" + franquiaId + "_" + LocalDate.now());

        return relatorio;
    }

    public ArrayList<String> gerarRelatorioConsultasFranquiasPaciente(int idPaciente) {
        ArrayList<String> relatorio = new ArrayList<>();

        // Consulta para obter as informações das consultas do paciente
        String consultaSql = "SELECT "
                + "c.idConsulta, "
                + "c.diaHorario, "
                + "e.nome AS estado, "
                + "m.CRM AS crm_medico, "
                + "u.idUnidade AS idUnidade, "
                + "c.valor "
                + "FROM franquia_medica.consulta c "
                + "INNER JOIN franquia_medica.estado e ON c.estadoId = e.idEstado "
                + "INNER JOIN franquia_medica.medico m ON c.medicoId = m.idMedico "
                + "INNER JOIN franquia_medica.unidade u ON c.unidadeId = u.idUnidade "
                + "WHERE c.pacienteId = ?";

        // Consulta para obter as informações dos procedimentos do paciente
        String procedimentoSql = "SELECT "
                + "p.idProcedimento, "
                + "p.diaHorario, "
                + "e.nome AS estado, "
                + "m.CRM AS crm_medico, "
                + "u.idUnidade AS idUnidade, "
                + "p.valor "
                + "FROM franquia_medica.procedimento p "
                + "INNER JOIN franquia_medica.consulta c ON p.consultaId = c.idConsulta "
                + "INNER JOIN franquia_medica.estado e ON c.estadoId = e.idEstado "
                + "INNER JOIN franquia_medica.medico m ON c.medicoId = m.idMedico "
                + "INNER JOIN franquia_medica.unidade u ON c.unidadeId = u.idUnidade "
                + "WHERE c.pacienteId = ?";

        try (PreparedStatement consultaStatement = connection.prepareStatement(consultaSql); PreparedStatement procedimentoStatement = connection.prepareStatement(procedimentoSql)) {

            // Consulta das consultas
            consultaStatement.setInt(1, idPaciente);
            ResultSet consultaResultSet = consultaStatement.executeQuery();

            while (consultaResultSet.next()) {
                int idConsulta = consultaResultSet.getInt("idConsulta");
                String diaHorarioConsulta = consultaResultSet.getString("diaHorario");
                String estadoConsulta = consultaResultSet.getString("estado");
                String crmMedicoConsulta = consultaResultSet.getString("crm_medico");
                int idUnidadeConsulta = consultaResultSet.getInt("idUnidade");
                double valorConsulta = consultaResultSet.getDouble("valor");

                String linhaConsulta = "Consulta - ID: " + idConsulta
                        + ", Dia/Horário: " + diaHorarioConsulta
                        + ", Estado: " + estadoConsulta
                        + ", CRM Médico: " + crmMedicoConsulta
                        + ", ID Unidade: " + idUnidadeConsulta
                        + ", Valor: " + valorConsulta;

                relatorio.add(linhaConsulta);
            }

            // Consulta dos procedimentos
            procedimentoStatement.setInt(1, idPaciente);
            ResultSet procedimentoResultSet = procedimentoStatement.executeQuery();

            while (procedimentoResultSet.next()) {
                int idProcedimento = procedimentoResultSet.getInt("idProcedimento");
                String diaHorarioProcedimento = procedimentoResultSet.getString("diaHorario");
                String estadoProcedimento = procedimentoResultSet.getString("estado");
                String crmMedicoProcedimento = procedimentoResultSet.getString("crm_medico");
                int idUnidadeProcedimento = procedimentoResultSet.getInt("idUnidade");
                double valorProcedimento = procedimentoResultSet.getDouble("valor");

                String linhaProcedimento = "Procedimento - ID: " + idProcedimento
                        + ", Dia/Horário: " + diaHorarioProcedimento
                        + ", Estado: " + estadoProcedimento
                        + ", CRM Médico: " + crmMedicoProcedimento
                        + ", ID Unidade: " + idUnidadeProcedimento
                        + ", Valor: " + valorProcedimento;

                relatorio.add(linhaProcedimento);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro relatorio", e);
        }
        relatorioGene(relatorio, "RelatorioPaciente" + idPaciente + "_" + LocalDate.now());
        return relatorio;
    }

    public ArrayList<String> gerarRelatorioValoresMedico(int idMedico) {
        ArrayList<String> relatorio = new ArrayList<>();

        // Consulta para obter as informações dos valores recebidos pelo médico
        String sql = "SELECT "
                + "fm.valor, "
                + "ef.nome AS estado_financeiro, "
                + "fm.franquiaId, "
                + "fm.dataCriacao "
                + "FROM franquia_medica.financeiromedico fm "
                + "INNER JOIN franquia_medica.estadofinanceiro ef ON fm.estadofinanceiro = ef.idestadofinanceiro "
                + "WHERE fm.medicoId = ? "
                + "ORDER BY fm.dataCriacao";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idMedico);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                double valor = resultSet.getDouble("valor");
                String estadoFinanceiro = resultSet.getString("estado_financeiro");
                int franquiaId = resultSet.getInt("franquiaId");
                String dataCriacao = resultSet.getString("dataCriacao");

                String linhaRelatorio = "Valor: " + valor
                        + ", Estado Financeiro: " + estadoFinanceiro
                        + ", Franquia ID: " + franquiaId
                        + ", Data de Criação: " + dataCriacao;

                relatorio.add(linhaRelatorio);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro relatorio", e);
        }
        relatorioGene(relatorio, "RelatorioValoresMedicos" + idMedico + "_" + LocalDate.now());
        return relatorio;
    }

    /*public static void main(String[] args) {
        Relatorios relatorioDAO = new Relatorios();

        //exemplo 1:
        relatorioDAO.relatorio1();

        //exemplo 2:
        relatorioDAO.relatorio2();

    }*/
    public void relatorioGene(List<String> textos, String nomeArqv) {

        PdfPTable table = new PdfPTable(1); //numero de colunas

        //area disponivel para a tabela
        table.setWidthPercentage(97);
        //altura das colunas
        int height = 60;

        //String nomeArquivo = "CExemplos2";
        try (OutputStream file = new FileOutputStream(new File(caminhoArquivo + nomeArqv + ".pdf"));) {

            PdfPCell titulo2 = new PdfPCell(new Paragraph(nomeArqv, FontFactory.getFont(FontFactory.HELVETICA, 10)));
            titulo2.setColspan(1);
            titulo2.setHorizontalAlignment(Element.ALIGN_CENTER);
            titulo2.setPadding(3.0f);
            titulo2.setBackgroundColor(new BaseColor(255, 255, 255));
            titulo2.setFixedHeight(20);
            table.addCell(titulo2);

            /* Map<String, String> map = new HashMap();
            for (int i = 0; i < 200; i++) {
                map.put(i + "", "conteudo" + i);
            }*/
            for (String texto : textos) {
                PdfPCell titulo4 = new PdfPCell(new Paragraph(texto, FontFactory.getFont(FontFactory.HELVETICA, 10)));
                titulo4.setColspan(1);
                titulo4.setHorizontalAlignment(Element.ALIGN_CENTER);
                titulo4.setPadding(3.0f);
                titulo4.setBackgroundColor(new BaseColor(255, 255, 255));
                titulo4.setFixedHeight(height);
                table.addCell(titulo4);
            }

            /*for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                PdfPCell titulo4 = new PdfPCell(new Paragraph(value, FontFactory.getFont(FontFactory.HELVETICA, 10)));
                titulo4.setColspan(1);
                titulo4.setHorizontalAlignment(Element.ALIGN_CENTER);
                titulo4.setPadding(3.0f);
                titulo4.setBackgroundColor(new BaseColor(255, 255, 255));
                titulo4.setFixedHeight(height);
                table.addCell(titulo4);

            }*/
            Document document = new Document();

            PdfWriter.getInstance(document, file);

            document.open();

            document.add(table);

            document.close();

        } catch (DocumentException | IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        System.out.println("Relatório gerado com sucesso!");

    }

    //preenche um texto para imprimir o texto como saída
    public void relatorio1() {
        String var1 = "bonjour";
        String var2 = "bonsoir";
        String texto = "Grupos e integrantes: \n\n";

        texto += "ID do trabalho: " + var1 + "\n";
        texto += "Título do trabalho: " + var2 + "\n";

        List<String> listaAlunos = new ArrayList();
        listaAlunos.add("joana");
        listaAlunos.add("priscila");

        texto += "Integrantes: \n";

        for (String object : listaAlunos) {
            texto += object + "\n";
        }

        texto += "\n";

        String nomeArquivo = "CExemplos1";

        try (OutputStream file = new FileOutputStream(new File(caminhoArquivo + nomeArquivo + ".pdf"));) {

            Document document = new Document();

            PdfWriter.getInstance(document, file);

            document.open();
            document.add(new Paragraph(texto, FontFactory.getFont(FontFactory.HELVETICA, 10)));
            document.close();

        } catch (DocumentException | IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        System.out.println("Relatório gerado com sucesso!");
    }

    public void relatorio2() {

        PdfPTable table = new PdfPTable(1); //numero de colunas

        //area disponivel para a tabela
        table.setWidthPercentage(97);
        //altura das colunas
        int height = 60;

        String nomeArquivo = "CExemplos2";

        try (OutputStream file = new FileOutputStream(new File(caminhoArquivo + nomeArquivo + ".pdf"));) {

            PdfPCell titulo2 = new PdfPCell(new Paragraph("Orientadores", FontFactory.getFont(FontFactory.HELVETICA, 10)));
            titulo2.setColspan(1);
            titulo2.setHorizontalAlignment(Element.ALIGN_CENTER);
            titulo2.setPadding(3.0f);
            titulo2.setBackgroundColor(new BaseColor(255, 255, 255));
            titulo2.setFixedHeight(20);
            table.addCell(titulo2);

            Map<String, String> map = new HashMap();
            for (int i = 0; i < 200; i++) {
                map.put(i + "", "conteudo" + i);
            }
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                PdfPCell titulo4 = new PdfPCell(new Paragraph(value, FontFactory.getFont(FontFactory.HELVETICA, 10)));
                titulo4.setColspan(1);
                titulo4.setHorizontalAlignment(Element.ALIGN_CENTER);
                titulo4.setPadding(3.0f);
                titulo4.setBackgroundColor(new BaseColor(255, 255, 255));
                titulo4.setFixedHeight(height);
                table.addCell(titulo4);

            }

            Document document = new Document();

            PdfWriter.getInstance(document, file);

            document.open();

            document.add(table);

            document.close();

        } catch (DocumentException | IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        System.out.println("Relatório gerado com sucesso!");

    }

}
