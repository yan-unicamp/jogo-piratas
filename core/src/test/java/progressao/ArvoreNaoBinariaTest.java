package progressao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArvoreNaoBinariaTest {

    private ArvoreNaoBinaria arvore;

    @BeforeEach
    void setUp() {
        // Quantidade de filhos = 3, Profundidade Maxima = 10, Capitulo = 1
        arvore = new ArvoreNaoBinaria(3, 10, 1);
    }

    @Test
    void deveGerarArvoreComProfundidadeCorretaEFiltros() {
        ArvoreNaoBinaria.NoDaArvore raiz = arvore.gerarArvore();
        
        assertNotNull(raiz);
        assertNotNull(raiz.getValor());
        
        // A raiz gerada por gerarArvore() usa a profundidade 0.
        // E como tem 3 filhos (quantidadeFilhos = 3) e profundidade > 0, 
        // a raiz deve ter 3 filhos exatos se não for folha.
        assertEquals(3, raiz.getFilhos().size());
        
        // Verifica se a folha em profundidade maxima nao tem filhos
        ArvoreNaoBinaria.NoDaArvore folha = navegarAteFundo(raiz);
        assertTrue(folha.getFilhos().isEmpty(), "No em profundidade maxima não deve ter filhos");
    }
    
    @Test
    void deveEscolherTiposCorretosComBaseNaProfundidade() {
        List<ArvoreNaoBinaria.TipoNo> tiposP0 = arvore.escolherEvento(0);
        assertTrue(tiposP0.contains(ArvoreNaoBinaria.TipoNo.BATALHA));
        
        List<ArvoreNaoBinaria.TipoNo> tiposP3 = arvore.escolherEvento(3);
        assertTrue(tiposP3.contains(ArvoreNaoBinaria.TipoNo.BATALHA_CANONICA));
        
        List<ArvoreNaoBinaria.TipoNo> tiposPMax = arvore.escolherEvento(10);
        assertTrue(tiposPMax.contains(ArvoreNaoBinaria.TipoNo.BATALHA_FINAL));
    }

    private ArvoreNaoBinaria.NoDaArvore navegarAteFundo(ArvoreNaoBinaria.NoDaArvore node) {
        if (node.getFilhos().isEmpty()) return node;
        return navegarAteFundo(node.getFilhos().get(0));
    }
}
