package br.com.dio.desafio.dominio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Certificado {
    private String nomeDesenvolvedor;
    private String nomeBootcamp;
    private LocalDate dataEmissao;
    private double xpTotal;
    private int conteudosConcluidos;
    private int totalConteudos;

    public Certificado(String nomeDesenvolvedor, String nomeBootcamp, double xpTotal, 
                      int conteudosConcluidos, int totalConteudos) {
        this.nomeDesenvolvedor = nomeDesenvolvedor;
        this.nomeBootcamp = nomeBootcamp;
        this.xpTotal = xpTotal;
        this.conteudosConcluidos = conteudosConcluidos;
        this.totalConteudos = totalConteudos;
        this.dataEmissao = LocalDate.now();
    }

    public String gerarCertificado() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return String.format(
            "\n" +
            "========================================\n" +
            "           CERTIFICADO DE CONCLUSÃO    \n" +
            "========================================\n" +
            "Certificamos que %s\n" +
            "concluiu com êxito o bootcamp:\n" +
            "'%s'\n" +
            "\n" +
            "Conteúdos concluídos: %d/%d\n" +
            "XP Total obtido: %.1f\n" +
            "Data de emissão: %s\n" +
            "========================================\n",
            nomeDesenvolvedor, nomeBootcamp, conteudosConcluidos, 
            totalConteudos, xpTotal, dataEmissao.format(formatter)
        );
    }

    // Getters and Setters
    public String getNomeDesenvolvedor() {
        return nomeDesenvolvedor;
    }

    public void setNomeDesenvolvedor(String nomeDesenvolvedor) {
        this.nomeDesenvolvedor = nomeDesenvolvedor;
    }

    public String getNomeBootcamp() {
        return nomeBootcamp;
    }

    public void setNomeBootcamp(String nomeBootcamp) {
        this.nomeBootcamp = nomeBootcamp;
    }

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public double getXpTotal() {
        return xpTotal;
    }

    public void setXpTotal(double xpTotal) {
        this.xpTotal = xpTotal;
    }

    public int getConteudosConcluidos() {
        return conteudosConcluidos;
    }

    public void setConteudosConcluidos(int conteudosConcluidos) {
        this.conteudosConcluidos = conteudosConcluidos;
    }

    public int getTotalConteudos() {
        return totalConteudos;
    }

    public void setTotalConteudos(int totalConteudos) {
        this.totalConteudos = totalConteudos;
    }
}
