package progressao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import factories.IlhaFactory;

import static org.junit.jupiter.api.Assertions.*;

class MapaTest {

    private Mapa mapa;

    @BeforeEach
    void setUp() {
        mapa = new Mapa();
    }

    @Test
    void deveIniciarComEstadoCorreto() {
        assertEquals(0, mapa.getEtapaAtual());
        assertEquals(1, mapa.getCapitulo());
        assertTrue(mapa.getIlhasConcluidas().isEmpty());
        // Na inicializacao, ele chama gerarProximosNos, que popula opcoesAtuais
        assertFalse(mapa.getOpcoesAtuais().isEmpty());
    }

    @Test
    void deveAvancarEtapaCorretamenteAoEntrarNaIlha() {
        Ilha ilhaInicial = mapa.getOpcoesAtuais().get(0);
        mapa.entrarIlha(ilhaInicial);
        
        assertEquals(1, mapa.getEtapaAtual());
        assertEquals(1, mapa.getCapitulo());
        assertEquals(1, mapa.getIlhasConcluidas().size());
        assertEquals(ilhaInicial.getNome(), mapa.getIlhasConcluidas().get(0).getNome());
    }

    @Test
    void deveAvancarParaProximoCapituloAoPassarDaEtapaMaxima() {
        // Forcando o avanco ate o limite do capitulo (10 etapas)
        for (int i = 0; i <= 10; i++) {
            Ilha opcao = mapa.getOpcoesAtuais().isEmpty() ? IlhaFactory.criar(IlhaEnum.LOGUETOWN) : mapa.getOpcoesAtuais().get(0);
            mapa.entrarIlha(opcao);
        }
        
        // Apos 11 entradas (0 a 10), o capitulo deve aumentar
        assertEquals(2, mapa.getCapitulo());
        assertEquals(1, mapa.getEtapaAtual()); // Etapa reinicia em 1
        assertTrue(mapa.getIlhasConcluidas().isEmpty(), "Ilhas concluidas devem ser limpas ao mudar de capitulo");
    }
    
    @Test
    void deveAvancarParaNo() {
        NoMapa noFake = new NoEvento();
        mapa.avancarParaNo(noFake);
        assertEquals(1, mapa.getEtapaAtual());
        assertEquals(noFake, mapa.getNoAtual());
    }

    @Test
    void deveGerarOpcoesCorretasNasEtapasCanonicas() {
        // Avancar ate etapa 3 (etapaAtual vira 3 apos 3 avancos se comecou no 0)
        mapa.avancarParaNo(new NoEvento()); // vai pra 1
        mapa.avancarParaNo(new NoEvento()); // vai pra 2
        mapa.avancarParaNo(new NoEvento()); // vai pra 3
        mapa.gerarProximosNos(); // Forca regerar pra testar
        
        assertFalse(mapa.getOpcoesAtuais().isEmpty());
        // A ilha gerada deve ser canonica do cap 1
        
        // Vamos ate 10
        for (int i = 0; i < 7; i++) {
            mapa.avancarParaNo(new NoEvento());
        }
        assertEquals(10, mapa.getEtapaAtual());
        mapa.gerarProximosNos();
        assertFalse(mapa.getOpcoesAtuais().isEmpty());
        assertEquals("Loguetown", mapa.getOpcoesAtuais().get(0).getNome());
    }
    
    @Test
    void deveGerarOpcoesCap2() {
        // Avancar ate o capitulo 2
        for (int i = 0; i < 12; i++) {
            mapa.avancarParaNo(new NoEvento());
        }
        assertEquals(2, mapa.getCapitulo());
        
        // Etapas: 3, 6, 9 devem usar sortearCanonicaCap2
        mapa.avancarParaNo(new NoEvento()); // vai pra 2
        mapa.avancarParaNo(new NoEvento()); // vai pra 3
        mapa.gerarProximosNos();
        assertFalse(mapa.getOpcoesAtuais().isEmpty());
    }
    
    @Test
    void deveGerarOpcoesCap3ECap4() {
        for (int i = 0; i < 22; i++) {
            mapa.avancarParaNo(new NoEvento());
        }
        assertEquals(3, mapa.getCapitulo());
        mapa.avancarParaNo(new NoEvento()); // 2
        mapa.avancarParaNo(new NoEvento()); // 3
        mapa.gerarProximosNos();
        assertFalse(mapa.getOpcoesAtuais().isEmpty());
        
        // Avancar ate cap 4 (wano)
        for (int i = 0; i < 10; i++) {
            mapa.avancarParaNo(new NoEvento());
        }
        assertEquals(4, mapa.getCapitulo());
        mapa.avancarParaNo(new NoEvento()); // 2
        mapa.avancarParaNo(new NoEvento()); // 3
        mapa.gerarProximosNos();
        assertFalse(mapa.getOpcoesAtuais().isEmpty());
    }

    @Test
    void deveRestaurarEstadoComItens() {
        java.util.List<String> mostradas = new java.util.ArrayList<>();
        mostradas.add("BARCO_PIRATA_INIMIGO");
        
        java.util.List<String> opcoes = new java.util.ArrayList<>();
        opcoes.add("LOJA");
        
        java.util.List<String> concluidas = new java.util.ArrayList<>();
        concluidas.add("DESCANSO");
        
        mapa.restaurarEstado(3, 5, opcoes, mostradas, concluidas);
        
        assertEquals(3, mapa.getCapitulo());
        assertEquals(5, mapa.getEtapaAtual());
        assertFalse(mapa.getIlhas().isEmpty());
        assertFalse(mapa.getIlhasMostradas().isEmpty());
        assertTrue(mapa.getIlhasMostradas().contains(progressao.IlhasGenericasEnum.BARCO_PIRATA_INIMIGO));
    }
}
