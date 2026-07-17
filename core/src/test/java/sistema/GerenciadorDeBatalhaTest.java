package sistema;

import entidades.Aliado;
import entidades.Habilidade;
import entidades.Inimigo;
import entidades.Personagem;
import factories.PersonagemFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GerenciadorDeBatalhaTest {

    private GerenciadorDeBatalha gerenciador;
    private Tripulacao tripulacao;
    private Aliado aliado;
    private Inimigo inimigo;

    @BeforeEach
    void setUp() {
        gerenciador = new GerenciadorDeBatalha();
        tripulacao = new Tripulacao();
        
        aliado = PersonagemFactory.criarLuffy();
        inimigo = PersonagemFactory.criarMarinheiro(1);
        
        // Vamos forçar a iniciativa para garantir a ordem
        aliado.aumentarIniciativa(100); 
    }

    @Test
    void deveIniciarCombateCorretamente() {
        ArrayList<Personagem> aliados = new ArrayList<>();
        aliados.add(aliado);
        
        ArrayList<Personagem> inimigos = new ArrayList<>();
        inimigos.add(inimigo);
        
        gerenciador.iniciarCombate(aliados, inimigos, tripulacao);
        
        // Verifica se as listas foram preenchidas
        assertEquals(1, gerenciador.getAliados().size());
        assertEquals(1, gerenciador.getInimigos().size());
        
        // Como o aliado está vivo, o estado deve ser PLANEJAMENTO_JOGADOR
        assertEquals(GerenciadorDeBatalha.EstadoBatalha.PLANEJAMENTO_JOGADOR, gerenciador.getEstadoAtual());
        
        // O aliado deve estar aguardando ação
        assertNotNull(gerenciador.getAliadoAguardandoAcao());
        assertEquals(aliado.getNome(), gerenciador.getAliadoAguardandoAcao().getNome());
    }

    @Test
    void deveRegistrarAcaoEAplicarDano() {
        ArrayList<Personagem> aliados = new ArrayList<>();
        aliados.add(aliado);
        ArrayList<Personagem> inimigos = new ArrayList<>();
        inimigos.add(inimigo);
        
        gerenciador.iniciarCombate(aliados, inimigos, tripulacao);
        
        // 1. Fase de Planejamento do Jogador
        Habilidade ataque = aliado.getHabilidades().get(0);
        gerenciador.registrarAcaoJogador(ataque, inimigo);
        
        // Após o jogador registrar, o inimigo automaticamente planeja sua ação
        // e o estado vai para EXECUCAO_TURNOS
        assertEquals(GerenciadorDeBatalha.EstadoBatalha.EXECUCAO_TURNOS, gerenciador.getEstadoAtual());
        
        // 2. Preparar a próxima ação (deve ser o Luffy, pois tem mais iniciativa)
        Personagem turnoAtual = gerenciador.prepararProximaAcao();
        assertNotNull(turnoAtual);
        assertEquals(aliado.getNome(), turnoAtual.getNome());
        
        // 3. Aplicar a Ação
        int vidaAnteriorInimigo = inimigo.getVidaAtual();
        gerenciador.aplicarAcaoPreparada();
        
        // Verifica se a vida do inimigo diminuiu
        assertTrue(inimigo.getVidaAtual() < vidaAnteriorInimigo, "A vida do inimigo deveria ter diminuído após o ataque do aliado");
    }

    @Test
    void deveDeclararVitoriaQuandoInimigosMorrem() {
        ArrayList<Personagem> aliados = new ArrayList<>();
        aliados.add(aliado);
        ArrayList<Personagem> inimigos = new ArrayList<>();
        inimigos.add(inimigo);
        
        gerenciador.iniciarCombate(aliados, inimigos, tripulacao);
        
        // Força a morte do inimigo diretamente para testar a verificação
        inimigo.receberDano(9999);
        
        gerenciador.verificarVitoriaOuDerrota();
        
        assertEquals(GerenciadorDeBatalha.EstadoBatalha.VITORIA, gerenciador.getEstadoAtual());
    }

    @Test
    void deveRegistrarAcaoJogadorEAvancarEstado() {
        GerenciadorDeBatalha gb = new GerenciadorDeBatalha();
        Tripulacao t = new Tripulacao();
        Aliado aliado = PersonagemFactory.criarLuffy();
        Inimigo inimigo = PersonagemFactory.criarMarinheiro(1);
        
        ArrayList<Personagem> aliados = new ArrayList<>();
        aliados.add(aliado);
        ArrayList<Personagem> inimigos = new ArrayList<>();
        inimigos.add(inimigo);
        
        gb.iniciarCombate(aliados, inimigos, t);
        assertEquals(GerenciadorDeBatalha.EstadoBatalha.PLANEJAMENTO_JOGADOR, gb.getEstadoAtual());
        
        // Aliado 1 age
        gb.registrarAcaoJogador(aliado.getHabilidades().get(0), inimigo);
        
        // Ao todos agirem, deve ir para planejamento dos inimigos, e como eh automatico, processa e vai pra EXECUCAO_TURNOS
        assertEquals(GerenciadorDeBatalha.EstadoBatalha.EXECUCAO_TURNOS, gb.getEstadoAtual());
    }
    
    @Test
    void deveExecutarTurnoAteVitoria() {
        GerenciadorDeBatalha gb = new GerenciadorDeBatalha();
        Tripulacao t = new Tripulacao();
        Aliado aliado = PersonagemFactory.criarLuffy();
        Inimigo inimigo = PersonagemFactory.criarMarinheiro(1);
        inimigo.receberDano(inimigo.getVidaAtual() - 1); // Deixa com 1 de vida
        
        ArrayList<Personagem> aliados = new ArrayList<>();
        aliados.add(aliado);
        ArrayList<Personagem> inimigos = new ArrayList<>();
        inimigos.add(inimigo);
        
        gb.iniciarCombate(aliados, inimigos, t);
        
        gb.registrarAcaoJogador(aliado.getHabilidades().get(0), inimigo); // Ataque basico
        
        Personagem vez = gb.prepararProximaAcao();
        while (vez != null && gb.getEstadoAtual() == GerenciadorDeBatalha.EstadoBatalha.EXECUCAO_TURNOS) {
            gb.aplicarAcaoPreparada();
            vez = gb.prepararProximaAcao();
        }
        
        assertEquals(GerenciadorDeBatalha.EstadoBatalha.VITORIA, gb.getEstadoAtual());
    }
    
    @Test
    void deveDarRecompensasEVitoria() {
        GerenciadorDeBatalha gb = new GerenciadorDeBatalha();
        Tripulacao t = new Tripulacao();
        Aliado aliado = PersonagemFactory.criarLuffy();
        
        Inimigo inimigo = PersonagemFactory.criarMarinheiro(1);
        inimigo.setRecompensaAliado(PersonagemFactory.criarZoro());
        inimigo.setRecompensaItem(factories.ItemFactory.criarBandagemSimples());
        
        ArrayList<Personagem> aliados = new ArrayList<>();
        aliados.add(aliado);
        ArrayList<Personagem> inimigos = new ArrayList<>();
        inimigos.add(inimigo);
        
        gb.iniciarCombate(aliados, inimigos, t);
        inimigo.receberDano(10000); // Morre
        gb.verificarVitoriaOuDerrota();
        
        assertEquals(GerenciadorDeBatalha.EstadoBatalha.VITORIA, gb.getEstadoAtual());
        assertTrue(gb.getXpGanho() > 0);
        assertTrue(gb.getDinheiroGanho() > 0);
        assertFalse(gb.getAliadosDesbloqueados().isEmpty());
    }

    @Test
    void deveDarDerrota() {
        GerenciadorDeBatalha gb = new GerenciadorDeBatalha();
        Tripulacao t = new Tripulacao();
        Aliado aliado = PersonagemFactory.criarLuffy();
        Inimigo inimigo = PersonagemFactory.criarMarinheiro(1);
        
        ArrayList<Personagem> aliados = new ArrayList<>();
        aliados.add(aliado);
        ArrayList<Personagem> inimigos = new ArrayList<>();
        inimigos.add(inimigo);
        
        gb.iniciarCombate(aliados, inimigos, t);
        aliado.receberDano(10000); // Morre
        gb.verificarVitoriaOuDerrota();
        
        assertEquals(GerenciadorDeBatalha.EstadoBatalha.DERROTA, gb.getEstadoAtual());
    }
}
