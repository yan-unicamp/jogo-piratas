package sistema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import entidades.Aliado;
import entidades.Habilidade;
import entidades.Inimigo;
import entidades.Personagem;
import progressao.Recompensa;

public class GerenciadorDeBatalha {
    public enum EstadoBatalha {
        AGUARDANDO_INICIO,
        PLANEJAMENTO_JOGADOR,
        PLANEJAMENTO_INIMIGOS,
        EXECUCAO_TURNOS,
        VITORIA,
        DERROTA
    }

    private final FilaDeTurnos filaDeTurnos;
    private ArrayList<Personagem> aliados;
    private ArrayList<Personagem> inimigos;
    private Tripulacao tripulacao;
    private Random random;
    private HashMap<Personagem, AcaoPlanejada> acoesPlanejadas;

    private EstadoBatalha estadoAtual;
    private List<Personagem> aliadosAguardandoAcao;

    public static class AcaoPlanejada {
        public Habilidade habilidade;
        public Personagem alvo;

        public AcaoPlanejada(Habilidade habilidade, Personagem alvo) {
            this.habilidade = habilidade;
            this.alvo = alvo;
        }
    }

    public GerenciadorDeBatalha() {
        this.filaDeTurnos = new FilaDeTurnos();
        this.random = new Random();
        this.acoesPlanejadas = new HashMap<>();
        this.aliadosAguardandoAcao = new ArrayList<>();
        this.estadoAtual = EstadoBatalha.AGUARDANDO_INICIO;
    }

    public void iniciarCombate(ArrayList<Personagem> aliados, ArrayList<Personagem> inimigos, Tripulacao tripulacao) {
        this.aliados = aliados;
        this.inimigos = inimigos;
        this.tripulacao = tripulacao;

        for (Personagem aliado : aliados) {
            filaDeTurnos.adicionar(aliado);
        }
        for (Personagem inimigo : inimigos) {
            filaDeTurnos.adicionar(inimigo);
        }
        filaDeTurnos.ordenarPorIniciativa();

        iniciarNovoTurno();
    }

    private void iniciarNovoTurno() {
        acoesPlanejadas.clear();
        aliadosAguardandoAcao.clear();

        for (Personagem aliado : aliados) {
            if (aliado.getVidaAtual() > 0) {
                aliadosAguardandoAcao.add(aliado);
            }
        }

        if (!aliadosAguardandoAcao.isEmpty()) {
            estadoAtual = EstadoBatalha.PLANEJAMENTO_JOGADOR;
        } else {
            estadoAtual = EstadoBatalha.PLANEJAMENTO_INIMIGOS;
            processarAcoesInimigos(); // Pula direto se não houver aliados vivos (embora já seria derrota)
        }

        if (!aliadosAguardandoAcao.isEmpty()) {
            estadoAtual = EstadoBatalha.PLANEJAMENTO_JOGADOR;
        } else {
            estadoAtual = EstadoBatalha.PLANEJAMENTO_INIMIGOS;
            processarAcoesInimigos(); // Pula direto se não houver aliados vivos (embora já seria derrota)
        }
    }

    public Personagem getAliadoAguardandoAcao() {
        if (!aliadosAguardandoAcao.isEmpty()) {
            return aliadosAguardandoAcao.get(0);
        }
        return null;
    }

    public void registrarAcaoJogador(Habilidade habilidade, Personagem alvo) {
        if (estadoAtual != EstadoBatalha.PLANEJAMENTO_JOGADOR)
            return;

        Personagem aliado = getAliadoAguardandoAcao();
        if (aliado != null) {
            acoesPlanejadas.put(aliado, new AcaoPlanejada(habilidade, alvo));
            aliadosAguardandoAcao.remove(0);
            System.out.println("Ação registrada para: " + aliado.getNome());
        }
        if (aliadosAguardandoAcao.isEmpty()) {
            estadoAtual = EstadoBatalha.PLANEJAMENTO_INIMIGOS;
            processarAcoesInimigos();
        }
    }

    public void processarAcoesInimigos() {
        if (estadoAtual != EstadoBatalha.PLANEJAMENTO_INIMIGOS)
            return;

        for (Personagem inimigo : inimigos) {
            if (inimigo.getVidaAtual() > 0) {
                List<Habilidade> habs = inimigo.getHabilidades();
                if (!habs.isEmpty()) {
                    Habilidade habEscolhida = habs.get(random.nextInt(habs.size()));

                    List<Personagem> alvos = new ArrayList<>();
                    if (habEscolhida.getTipo() == entidades.TipoHabilidade.CURA
                            || habEscolhida.getTipo() == entidades.TipoHabilidade.DEFESA) {
                        for (Personagem p : inimigos)
                            if (p.getVidaAtual() > 0)
                                alvos.add(p);
                    } else {
                        for (Personagem p : aliados)
                            if (p.getVidaAtual() > 0)
                                alvos.add(p);
                    }

                    Personagem alvEscolhido = null;
                    if (!alvos.isEmpty()) {
                        alvEscolhido = alvos.get(random.nextInt(alvos.size()));
                    }
                    acoesPlanejadas.put(inimigo, new AcaoPlanejada(habEscolhida, alvEscolhido));
                    System.out.println("Inimigo " + inimigo.getNome() + " planejou sua ação.");
                }
            }
        }
        estadoAtual = EstadoBatalha.EXECUCAO_TURNOS;
    }

    public Personagem executarProximaAcao() {
        if (estadoAtual != EstadoBatalha.EXECUCAO_TURNOS)
            return null;

        Personagem personagemDaVez = null;
        
        // Pula os turnos de quem já morreu
        while (true) {
            personagemDaVez = filaDeTurnos.obterProximoPersonagem();
            if (personagemDaVez == null) {
                verificarVitoriaOuDerrota();
                if (estadoAtual != EstadoBatalha.VITORIA && estadoAtual != EstadoBatalha.DERROTA) {
                    iniciarNovoTurno();
                }
                return null;
            }
            if (personagemDaVez.getVidaAtual() > 0) {
                break; // Achou o próximo vivo!
            }
        }

        if (personagemDaVez.getVidaAtual() > 0) {
            AcaoPlanejada acao = acoesPlanejadas.get(personagemDaVez);
            if (acao != null && acao.habilidade != null && acao.alvo != null) {
                if (acao.alvo.getVidaAtual() > 0 || acao.habilidade.getTipo() == entidades.TipoHabilidade.CURA) {
                    System.out.println("[" + personagemDaVez.getNome() + " usa " + acao.habilidade.getNome() + " em "
                            + acao.alvo.getNome() + "]");
                    acao.habilidade.executarAcao(acao.alvo);
                } else {
                    System.out
                            .println("[" + personagemDaVez.getNome() + " tenta atacar, mas o alvo já foi derrotado!]");
                }
            }
        }

        verificarVitoriaOuDerrota();
        return personagemDaVez;
    }

    public void verificarVitoriaOuDerrota() {
        boolean aliadosVivos = false;
        for (Personagem aliado : aliados) {
            if (aliado.getVidaAtual() > 0) {
                aliadosVivos = true;
                break;
            }
        }

        boolean inimigosVivos = false;
        for (Personagem inimigo : inimigos) {
            if (inimigo.getVidaAtual() > 0) {
                inimigosVivos = true;
                break;
            }
        }

        if (!aliadosVivos) {
            System.out.println("\nDerrota! Todos os aliados caíram.");
            estadoAtual = EstadoBatalha.DERROTA;
            for (Personagem aliado : aliados) aliado.resetarDefesa();
        } else if (!inimigosVivos) {
            System.out.println("\nVitória! Inimigos derrotados.");
            estadoAtual = EstadoBatalha.VITORIA;
            for (Personagem aliado : aliados) aliado.resetarDefesa();
            recompensa();
        }

    }

    public void recompensa() {
        int dinheiroTotal = 0;
        int xpTotal = 0;
        ArrayList<Inimigo> oponentes = new ArrayList<>();
        for (Personagem inimigo : inimigos) {
            if (inimigo instanceof Inimigo oponente) {
                oponentes.add(oponente);
            }
        }
        for (Inimigo oponente : oponentes) {
            dinheiroTotal += oponente.getRecompensa().getDinheiroGanhado();
            xpTotal += oponente.getRecompensa().getExperienciaGanhada();
        }

        int xpDividido = xpTotal / aliados.size();

        ArrayList<Aliado> amigos = new ArrayList<>();
        for (Personagem aliado : aliados) {
            if (aliado instanceof Aliado amigo) {
                amigos.add(amigo);
            }
        }
        for (Aliado amigo : amigos) {
            amigo.addXp(xpDividido);
        }
        tripulacao.receberDinheiro(dinheiroTotal);
    }

    public EstadoBatalha getEstadoAtual() {
        return estadoAtual;
    }

    public ArrayList<Personagem> getAliados() {
        return aliados;
    }

    public ArrayList<Personagem> getInimigos() {
        return inimigos;
    }
}
