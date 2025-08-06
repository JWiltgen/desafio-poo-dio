package br.com.dio.desafio.dominio;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ProgressTracker {
    private Dev desenvolvedor;
    private Bootcamp bootcamp;
    private LocalDate dataInscricao;
    private LocalDate ultimaAtividade;
    private List<LocalDate> diasAtivos;
    private int streakAtual;
    private int maiorStreak;

    public ProgressTracker(Dev desenvolvedor, Bootcamp bootcamp) {
        this.desenvolvedor = desenvolvedor;
        this.bootcamp = bootcamp;
        this.dataInscricao = LocalDate.now();
        this.ultimaAtividade = LocalDate.now();
        this.diasAtivos = new ArrayList<>();
        this.streakAtual = 0;
        this.maiorStreak = 0;
        this.diasAtivos.add(LocalDate.now());
    }

    public double calcularPorcentagemProgresso() {
        int totalConteudos = bootcamp.getConteudos().size();
        int conteudosConcluidos = desenvolvedor.getConteudosConcluidos().size();
        
        if (totalConteudos == 0) return 0.0;
        
        return (double) conteudosConcluidos / totalConteudos * 100.0;
    }

    public void atualizarProgresso() {
        LocalDate hoje = LocalDate.now();
        
        // Atualizar última atividade
        this.ultimaAtividade = hoje;
        
        // Adicionar dia ativo se não existe
        if (!diasAtivos.contains(hoje)) {
            diasAtivos.add(hoje);
        }
        
        // Calcular streak
        calcularStreak();
    }

    private void calcularStreak() {
        LocalDate hoje = LocalDate.now();
        LocalDate dataVerificacao = hoje;
        int streak = 0;
        
        // Contar dias consecutivos trabalhando para trás
        while (diasAtivos.contains(dataVerificacao)) {
            streak++;
            dataVerificacao = dataVerificacao.minusDays(1);
        }
        
        this.streakAtual = streak;
        
        // Atualizar maior streak
        if (this.streakAtual > this.maiorStreak) {
            this.maiorStreak = this.streakAtual;
        }
    }

    public long getDiasDesdeInscricao() {
        return ChronoUnit.DAYS.between(dataInscricao, LocalDate.now());
    }

    public boolean isBootcampConcluido() {
        return calcularPorcentagemProgresso() >= 100.0;
    }

    public Certificado gerarCertificado() {
        if (isBootcampConcluido()) {
            return new Certificado(
                desenvolvedor.getNome(),
                bootcamp.getNome(),
                desenvolvedor.calcularTotalXp(),
                desenvolvedor.getConteudosConcluidos().size(),
                bootcamp.getConteudos().size()
            );
        }
        return null;
    }

    public String gerarRelatorioProgresso() {
        StringBuilder relatorio = new StringBuilder();
        
        relatorio.append("\n=== RELATÓRIO DE PROGRESSO ===\n");
        relatorio.append("Desenvolvedor: ").append(desenvolvedor.getNome()).append("\n");
        relatorio.append("Bootcamp: ").append(bootcamp.getNome()).append("\n");
        relatorio.append("Data de Inscrição: ").append(dataInscricao).append("\n");
        relatorio.append("Dias desde a inscrição: ").append(getDiasDesdeInscricao()).append("\n");
        relatorio.append("Última atividade: ").append(ultimaAtividade).append("\n");
        relatorio.append("\n--- PROGRESSO ---\n");
        relatorio.append("Progresso: ").append(String.format("%.1f%%", calcularPorcentagemProgresso())).append("\n");
        relatorio.append("Conteúdos concluídos: ").append(desenvolvedor.getConteudosConcluidos().size())
                 .append("/").append(bootcamp.getConteudos().size()).append("\n");
        relatorio.append("XP Total: ").append(desenvolvedor.calcularTotalXp()).append("\n");
        relatorio.append("\n--- ATIVIDADE ---\n");
        relatorio.append("Streak atual: ").append(streakAtual).append(" dias\n");
        relatorio.append("Maior streak: ").append(maiorStreak).append(" dias\n");
        relatorio.append("Dias ativos: ").append(diasAtivos.size()).append("\n");
        
        // Status
        relatorio.append("\n--- STATUS ---\n");
        if (isBootcampConcluido()) {
            relatorio.append("🎉 BOOTCAMP CONCLUÍDO! 🎉\n");
        } else {
            relatorio.append("📚 Em progresso...\n");
            int conteudosRestantes = bootcamp.getConteudos().size() - desenvolvedor.getConteudosConcluidos().size();
            relatorio.append("Conteúdos restantes: ").append(conteudosRestantes).append("\n");
        }
        
        relatorio.append("==============================\n");
        
        return relatorio.toString();
    }

    // Getters and Setters
    public Dev getDesenvolvedor() {
        return desenvolvedor;
    }

    public void setDesenvolvedor(Dev desenvolvedor) {
        this.desenvolvedor = desenvolvedor;
    }

    public Bootcamp getBootcamp() {
        return bootcamp;
    }

    public void setBootcamp(Bootcamp bootcamp) {
        this.bootcamp = bootcamp;
    }

    public LocalDate getDataInscricao() {
        return dataInscricao;
    }

    public void setDataInscricao(LocalDate dataInscricao) {
        this.dataInscricao = dataInscricao;
    }

    public LocalDate getUltimaAtividade() {
        return ultimaAtividade;
    }

    public void setUltimaAtividade(LocalDate ultimaAtividade) {
        this.ultimaAtividade = ultimaAtividade;
    }

    public int getStreakAtual() {
        return streakAtual;
    }

    public int getMaiorStreak() {
        return maiorStreak;
    }

    public List<LocalDate> getDiasAtivos() {
        return diasAtivos;
    }
}
