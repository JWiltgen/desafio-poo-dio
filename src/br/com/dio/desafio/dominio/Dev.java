package br.com.dio.desafio.dominio;

import java.util.*;

public class Dev {
    private String nome;
    private Set<Conteudo> conteudosInscritos = new LinkedHashSet<>();
    private Set<Conteudo> conteudosConcluidos = new LinkedHashSet<>();
    private List<ProgressTracker> progressTrackers = new ArrayList<>();

    public void inscreverBootcamp(Bootcamp bootcamp){
        this.conteudosInscritos.addAll(bootcamp.getConteudos());
        bootcamp.getDevsInscritos().add(this);
        
        // Criar tracker de progresso para este bootcamp
        ProgressTracker tracker = new ProgressTracker(this, bootcamp);
        this.progressTrackers.add(tracker);
    }

    public void progredir() {
        Optional<Conteudo> conteudo = this.conteudosInscritos.stream().findFirst();
        if(conteudo.isPresent()) {
            this.conteudosConcluidos.add(conteudo.get());
            this.conteudosInscritos.remove(conteudo.get());
            
            // Atualizar todos os trackers de progresso
            for (ProgressTracker tracker : progressTrackers) {
                tracker.atualizarProgresso();
            }
        } else {
            System.err.println("Você não está matriculado em nenhum conteúdo!");
        }
    }

    public double calcularTotalXp() {
        Iterator<Conteudo> iterator = this.conteudosConcluidos.iterator();
        double soma = 0;
        while(iterator.hasNext()){
            double next = iterator.next().calcularXp();
            soma += next;
        }
        return soma;

        /*return this.conteudosConcluidos
                .stream()
                .mapToDouble(Conteudo::calcularXp)
                .sum();*/
    }

    public ProgressTracker getProgressTracker(Bootcamp bootcamp) {
        return progressTrackers.stream()
            .filter(tracker -> tracker.getBootcamp().equals(bootcamp))
            .findFirst()
            .orElse(null);
    }

    public List<Certificado> gerarCertificados() {
        List<Certificado> certificados = new ArrayList<>();
        for (ProgressTracker tracker : progressTrackers) {
            Certificado certificado = tracker.gerarCertificado();
            if (certificado != null) {
                certificados.add(certificado);
            }
        }
        return certificados;
    }

    public void exibirRelatorioProgresso(Bootcamp bootcamp) {
        ProgressTracker tracker = getProgressTracker(bootcamp);
        if (tracker != null) {
            System.out.println(tracker.gerarRelatorioProgresso());
        } else {
            System.out.println("Desenvolvedor não está inscrito neste bootcamp.");
        }
    }

    public double calcularPorcentagemProgresso(Bootcamp bootcamp) {
        ProgressTracker tracker = getProgressTracker(bootcamp);
        return tracker != null ? tracker.calcularPorcentagemProgresso() : 0.0;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Conteudo> getConteudosInscritos() {
        return conteudosInscritos;
    }

    public void setConteudosInscritos(Set<Conteudo> conteudosInscritos) {
        this.conteudosInscritos = conteudosInscritos;
    }

    public Set<Conteudo> getConteudosConcluidos() {
        return conteudosConcluidos;
    }

    public void setConteudosConcluidos(Set<Conteudo> conteudosConcluidos) {
        this.conteudosConcluidos = conteudosConcluidos;
    }

    public List<ProgressTracker> getProgressTrackers() {
        return progressTrackers;
    }

    public void setProgressTrackers(List<ProgressTracker> progressTrackers) {
        this.progressTrackers = progressTrackers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dev dev = (Dev) o;
        return Objects.equals(nome, dev.nome) && Objects.equals(conteudosInscritos, dev.conteudosInscritos) && Objects.equals(conteudosConcluidos, dev.conteudosConcluidos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, conteudosInscritos, conteudosConcluidos);
    }
}
