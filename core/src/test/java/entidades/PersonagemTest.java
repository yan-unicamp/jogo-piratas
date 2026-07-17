package entidades;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class PersonagemTest {

    // Criar uma classe concreta para testar Personagem
    class PersonagemConcreto extends Personagem {
        public PersonagemConcreto(String nome, int vidaMaxima, float defesa, int iniciativa, String caminhoImagem) {
            super(nome, vidaMaxima, defesa, iniciativa, caminhoImagem);
        }
    }

    @Test
    void deveGerenciarAtributosCore() {
        Personagem p = new PersonagemConcreto("Teste", 100, 0.5f, 10, "teste.png");
        
        assertEquals("Teste", p.getNome());
        assertEquals(100, p.getVidaAtual());
        assertEquals(100, p.getVidaMaxima());
        assertEquals(0.5f, p.getDefesa());
        assertEquals(10, p.getIniciativa());
        assertEquals("teste.png", p.getCaminhoImagem());
        assertNotNull(p.getHabilidades());
        
        p.aumentarIniciativa(5);
        assertEquals(15, p.getIniciativa());
        
        p.setTurnosDePausa(2);
        assertEquals(2, p.getTurnosDePausa());
        p.decrementarTurnoDePausa();
        assertEquals(1, p.getTurnosDePausa());
        p.decrementarTurnoDePausa();
        assertEquals(0, p.getTurnosDePausa());
        p.decrementarTurnoDePausa(); // Nao deve ficar negativo
        assertEquals(0, p.getTurnosDePausa());
        
        p.aumentarDefesaTemporaria(0.1f);
        assertEquals(0.4f, p.getDefesa(), 0.01f); // 0.5 - 0.1 = 0.4
        
        p.resetarDefesa();
        assertEquals(0.5f, p.getDefesa(), 0.01f);
        
        // Testa nao deixar defesa menor que 0
        p.aumentarDefesaTemporaria(1.0f);
        assertEquals(0.0f, p.getDefesa(), 0.01f); // Limite inferior 0
    }

    @Test
    void deveGerenciarVidaEDano() {
        Personagem p = new PersonagemConcreto("Teste", 100, 0.5f, 10, "teste.png");
        assertTrue(p.estaVivo());
        
        p.aumentarVidaMaxima(20);
        assertEquals(120, p.getVidaMaxima());
        assertEquals(120, p.getVidaAtual());
        
        // Recebe 50 de dano com 0.5 de defesa = toma 25
        p.receberDano(50);
        assertEquals(95, p.getVidaAtual());
        
        p.curar(10);
        assertEquals(105, p.getVidaAtual());
        
        p.curar(100);
        assertEquals(120, p.getVidaAtual()); // Limita no max
        
        p.receberDano(9999);
        assertFalse(p.estaVivo());
        assertEquals(0, p.getVidaAtual()); // Nao fica negativo
    }

    @Test
    void deveGerenciarHabilidades() {
        Personagem p = new PersonagemConcreto("Teste", 100, 0.5f, 10, "teste.png");
        Habilidade h1 = new Habilidade("H1", TipoHabilidade.DANO, 10f, false);
        Habilidade h2 = new Habilidade("Especial", TipoHabilidade.DANO, 20f, false).setEspecial(true);
        Habilidade h3 = new Habilidade("Novo Especial", TipoHabilidade.DANO, 30f, false).setEspecial(true);
        
        p.adicionarHabilidade(h1);
        p.adicionarHabilidade(h2);
        assertTrue(p.getHabilidades().contains(h2));
        assertTrue(h2.isEspecial());
        
        p.adicionarHabilidade(h3);
        // O h2 perde o especial porque p so pode ter 1
        assertFalse(h2.isEspecial());
        assertTrue(h3.isEspecial());
    }

    @Test
    void deveTestarGetTextura() {
        Personagem p = new PersonagemConcreto("Teste", 100, 0.5f, 10, "teste.png");
        
        // Isso lanca NullPointerException por causa do Gdx.files
        assertThrows(NullPointerException.class, () -> p.getTextura());
    }
}
