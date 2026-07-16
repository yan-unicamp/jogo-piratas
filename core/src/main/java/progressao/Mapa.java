package progressao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Random;

import factories.IlhaFactory;

public class Mapa {
    private int etapaAtual;
    private int capitulo;
    private final int MAX_ETAPAS_POR_CAPITULO = 5;
    private NoMapa noAtual;
    
    private List<Ilha> ilhasConcluidas;
    private List<Ilha> opcoesAtuais;
    private Set<IlhasGenericasEnum> ilhasMostradas;
    private Random random;

    public Mapa() {
        this.etapaAtual = 0;
        this.capitulo = 1;
        this.ilhasConcluidas = new ArrayList<>();
        this.opcoesAtuais = new ArrayList<>();
        this.ilhasMostradas = new HashSet<>();
        this.random = new Random();
        gerarProximosNos();
    }

    public List<Ilha> getIlhasConcluidas() { return ilhasConcluidas; }
    public List<Ilha> getOpcoesAtuais() { return opcoesAtuais; }

    public void gerarProximosNos() {
        opcoesAtuais.clear();

        if (etapaAtual == 0) {
            // Início do capítulo (Canon)
            if (capitulo == 1) opcoesAtuais.add(IlhaFactory.criar(IlhaEnum.SHELLS_TOWN));
            else if (capitulo == 2) opcoesAtuais.add(IlhaFactory.criar(IlhaEnum.LITTLE_GARDEN));
            else if (capitulo == 3) opcoesAtuais.add(IlhaFactory.criar(IlhaEnum.ILHA_HOMENS_PEIXE));
            else opcoesAtuais.add(IlhaFactory.criar(IlhaEnum.EGGHEAD));
        } else if (etapaAtual == MAX_ETAPAS_POR_CAPITULO) {
            // Fim do capítulo (Canon)
            if (capitulo == 1) opcoesAtuais.add(IlhaFactory.criar(IlhaEnum.ARLONG_PARK));
            else if (capitulo == 2) opcoesAtuais.add(IlhaFactory.criar(IlhaEnum.MARINEFORD));
            else if (capitulo == 3) opcoesAtuais.add(IlhaFactory.criar(IlhaEnum.WANO));
            else opcoesAtuais.add(IlhaFactory.criar(IlhaEnum.ELBAPH));
        } else {
            // Ilhas Genéricas (2 a 3 opções)
            List<IlhasGenericasEnum> disponiveis = new ArrayList<>();
            for (IlhasGenericasEnum ig : IlhasGenericasEnum.values()) {
                if (!ilhasMostradas.contains(ig) && (ig.getCapitulo() == capitulo || ig.getCapitulo() == 0)) {
                    disponiveis.add(ig);
                }
            }

            int qtd = Math.min(3, disponiveis.size());
            if (qtd == 0) {
                // Fallback caso fiquemos sem ilhas genéricas
                opcoesAtuais.add(IlhaFactory.criarGenerica(IlhasGenericasEnum.BARCO_PIRATA_INIMIGO));
            } else {
                for (int i = 0; i < qtd; i++) {
                    int idx = random.nextInt(disponiveis.size());
                    IlhasGenericasEnum sorteada = disponiveis.get(idx);
                    disponiveis.remove(idx);
                    ilhasMostradas.add(sorteada);
                    opcoesAtuais.add(IlhaFactory.criarGenerica(sorteada));
                }
            }
        }
    }

    public void entrarIlha(Ilha ilhaEscolhida) {
        ilhasConcluidas.add(ilhaEscolhida);
        this.etapaAtual++;
        
        if (this.etapaAtual > MAX_ETAPAS_POR_CAPITULO) {
            this.etapaAtual = 0;
            this.capitulo++;
        }

        gerarProximosNos();
    }

    // Compatibilidade com lógica legada de GameManager
    public void avancarParaNo(NoMapa proximo) { 
        this.noAtual = proximo;
        this.etapaAtual++;
        if (this.etapaAtual > MAX_ETAPAS_POR_CAPITULO) {
            this.etapaAtual = 0;
            this.capitulo++;
        }
    }

    public int getEtapaAtual() { return etapaAtual; }
    public int getCapitulo() { return capitulo; }
    public NoMapa getNoAtual() { return noAtual; }

    // Retorna a fusão do histórico com as opções atuais para a TelaMapa legada (se necessário)
    public java.util.List<Ilha> getIlhas() { 
        List<Ilha> todas = new ArrayList<>(ilhasConcluidas);
        todas.addAll(opcoesAtuais);
        return todas;
    }
}
