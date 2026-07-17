package sistema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import entidades.Aliado;
import entidades.Habilidade;
import entidades.Inimigo;
import entidades.Personagem;


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
    private String ultimoLog = "Batalha Iniciada!";

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
            if (aliado.getVidaAtual() > 0 && aliado.getTurnosDePausa() == 0) {
                aliadosAguardandoAcao.add(aliado);
            }
        }

        if (!aliadosAguardandoAcao.isEmpty()) {
            estadoAtual = EstadoBatalha.PLANEJAMENTO_JOGADOR;
        } else {
            estadoAtual = EstadoBatalha.PLANEJAMENTO_INIMIGOS;
            processarAcoesInimigos(); // Pula direto se nao houver aliados vivos (embora ja seria derrota)
        }

        if (!aliadosAguardandoAcao.isEmpty()) {
            estadoAtual = EstadoBatalha.PLANEJAMENTO_JOGADOR;
        } else {
            estadoAtual = EstadoBatalha.PLANEJAMENTO_INIMIGOS;
            processarAcoesInimigos(); // Pula direto se nao houver aliados vivos (embora ja seria derrota)
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
            System.out.println("Acao registrada para: " + aliado.getNome());
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
            if (inimigo.getVidaAtual() > 0 && inimigo.getTurnosDePausa() == 0) {
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
                    System.out.println("Inimigo " + inimigo.getNome() + " planejou sua acao.");
                }
            }
        }
        estadoAtual = EstadoBatalha.EXECUCAO_TURNOS;
    }

    private AcaoPlanejada ultimaAcaoExecutada;

    public AcaoPlanejada getUltimaAcaoExecutada() {
        return ultimaAcaoExecutada;
    }

    private Personagem personagemDaVez;

    public Personagem prepararProximaAcao() {
        if (estadoAtual != EstadoBatalha.EXECUCAO_TURNOS) return null;

        personagemDaVez = null;
        ultimaAcaoExecutada = null;
        
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
                if (personagemDaVez.getTurnosDePausa() > 0) {
                    personagemDaVez.decrementarTurnoDePausa();
                    ultimoLog = personagemDaVez.getNome() + " esta exausto e perdeu o turno!";
                    System.out.println("[" + ultimoLog + "]");
                    return personagemDaVez;
                }
                break;
            }
        }
        
        if (personagemDaVez != null) {
            ultimaAcaoExecutada = acoesPlanejadas.get(personagemDaVez);
            if (ultimaAcaoExecutada != null) {
                ultimoLog = personagemDaVez.getNome() + " vai usar " + ultimaAcaoExecutada.habilidade.getNome() + "!";
                if (ultimaAcaoExecutada.habilidade.isEspecial()) {
                    personagemDaVez.setTurnosDePausa(1);
                }
            }
        }
        return personagemDaVez;
    }

    public void aplicarAcaoPreparada() {
        if (personagemDaVez != null && personagemDaVez.getVidaAtual() > 0 && ultimaAcaoExecutada != null) {
            if (ultimaAcaoExecutada.alvo.getVidaAtual() > 0 || ultimaAcaoExecutada.habilidade.getTipo() == entidades.TipoHabilidade.CURA) {
                ultimoLog = personagemDaVez.getNome() + " usou " + ultimaAcaoExecutada.habilidade.getNome() + " em " + ultimaAcaoExecutada.alvo.getNome() + "!";
                System.out.println("[" + ultimoLog + "]");
                ultimaAcaoExecutada.habilidade.executarAcao(ultimaAcaoExecutada.alvo);
            } else {
                ultimoLog = personagemDaVez.getNome() + " tentou atacar, mas o alvo ja foi derrotado!";
                System.out.println("[" + ultimoLog + "]");
            }
        }
        verificarVitoriaOuDerrota();
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
            System.out.println("\nDerrota! Todos os aliados cairam.");
            estadoAtual = EstadoBatalha.DERROTA;
            for (Personagem aliado : aliados) aliado.resetarDefesa();
        } else if (!inimigosVivos) {
            System.out.println("\nVitoria! Inimigos derrotados.");
            estadoAtual = EstadoBatalha.VITORIA;
            for (Personagem aliado : aliados) aliado.resetarDefesa();
            recompensa();
        }

    }

    private int xpGanhoBatalha = 0;
    private int dinheiroGanhoBatalha = 0;
    private java.util.List<entidades.Aliado> aliadosDesbloqueados = new java.util.ArrayList<>();
    
    public int getXpGanho() { return xpGanhoBatalha; }
    public int getDinheiroGanho() { return dinheiroGanhoBatalha; }
    public java.util.List<entidades.Aliado> getAliadosDesbloqueados() { return aliadosDesbloqueados; }

    private java.util.List<String> recompensasExtras = new java.util.ArrayList<>();
    public java.util.List<String> getRecompensasExtras() { return recompensasExtras; }

    public void recompensa() {
        int dinheiroTotal = 0;
        int xpTotal = 0;
        ArrayList<Inimigo> oponentes = new ArrayList<>();
        for (Personagem inimigo : inimigos) {
            if (inimigo instanceof Inimigo oponente) {
                oponentes.add(oponente);
            }
        }
        recompensasExtras.clear();
        for (Inimigo oponente : oponentes) {
            dinheiroTotal += oponente.getRecompensaDinheiro();
            xpTotal += oponente.getRecompensaExperiencia();
            if (oponente.getRecompensaAliado() != null) {
                progressao.Recompensa.darAliado(tripulacao, oponente.getRecompensaAliado());
                aliadosDesbloqueados.add(oponente.getRecompensaAliado());
                recompensasExtras.add("Novo aliado: " + oponente.getRecompensaAliado().getNome());
            }
            if (oponente.getRecompensaItem() != null) {
                progressao.Recompensa.darItem(tripulacao, oponente.getRecompensaItem());
                recompensasExtras.add("Novo item: " + oponente.getRecompensaItem().getNome());
            }
        }

        this.xpGanhoBatalha = xpTotal;
        this.dinheiroGanhoBatalha = dinheiroTotal;

        int xpDividido = xpTotal / aliados.size();

        ArrayList<Aliado> amigos = new ArrayList<>();
        for (Personagem aliado : aliados) {
            if (aliado instanceof Aliado amigo) {
                amigos.add(amigo);
            }
        }
        for (Aliado amigo : amigos) {
            int nivelAntigo = amigo.getNivel();
            java.util.List<entidades.Habilidade> novas = amigo.ganharExperiencia(xpDividido);
            int nivelNovo = amigo.getNivel();
            if (nivelNovo > nivelAntigo) {
                recompensasExtras.add(amigo.getNome() + " subiu para o nivel " + nivelNovo + "!");
            }
            for (entidades.Habilidade h : novas) {
                if (amigo.getHabilidades().size() < 4) {
                    amigo.adicionarHabilidade(h);
                    System.out.println(amigo.getNome() + " aprendeu " + h.getNome() + "!");
                    recompensasExtras.add(amigo.getNome() + " aprendeu " + h.getNome() + "!");
                } else {
                    habilidadesPendentes.add(new sistema.HabilidadePendente(amigo, h));

                }
            }
        }
        tripulacao.receberDinheiro(dinheiroTotal);
    }

    private java.util.List<sistema.HabilidadePendente> habilidadesPendentes = new java.util.ArrayList<>();
    
    public java.util.List<sistema.HabilidadePendente> getHabilidadesPendentes() {
        return habilidadesPendentes;
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

    public FilaDeTurnos getFilaDeTurnos() {
        return filaDeTurnos;
    }

    public String getUltimoLog() {
        return ultimoLog;
    }

    public void setUltimoLog(String msg) {
        this.ultimoLog = msg;
    }
}
