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
    private final int MAX_ETAPAS_POR_CAPITULO = 9;
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

    private List<IlhaEnum> canonicasCap2 = new ArrayList<>();
    private IlhaEnum sortearCanonicaCap2() {
        if (canonicasCap2.isEmpty()) {
            canonicasCap2.addAll(java.util.Arrays.asList(IlhaEnum.THRILLER_BARK, IlhaEnum.ALABASTA, IlhaEnum.ENIES_LOBBY, IlhaEnum.ILHA_DRUM, IlhaEnum.LONG_RING_LONG_LAND, IlhaEnum.LITTLE_GARDEN, IlhaEnum.JAYA, IlhaEnum.SKYPIEA, IlhaEnum.IMPEL_DOWN));
        }
        int idx = random.nextInt(canonicasCap2.size());
        return canonicasCap2.remove(idx);
    }

    public void gerarProximosNos() {
        opcoesAtuais.clear();

        if (etapaAtual == 0) {
            if (capitulo == 1) opcoesAtuais.add(IlhaFactory.criar(IlhaEnum.SHELLS_TOWN));
            else if (capitulo == 2) opcoesAtuais.add(IlhaFactory.criar(IlhaEnum.WHISKY_PEAK));
            else if (capitulo == 3) opcoesAtuais.add(IlhaFactory.criar(IlhaEnum.ILHA_HOMENS_PEIXE));
            else opcoesAtuais.add(IlhaFactory.criar(IlhaEnum.PUNK_HAZARD));
        } else if (etapaAtual == 3) {
            if (capitulo == 1) opcoesAtuais.add(IlhaFactory.criar(IlhaEnum.VILA_SYRUP));
            else if (capitulo == 2) opcoesAtuais.add(IlhaFactory.criar(sortearCanonicaCap2()));
            else if (capitulo == 3) opcoesAtuais.add(IlhaFactory.criar(IlhaEnum.SABAODY));
            else opcoesAtuais.add(IlhaFactory.criar(IlhaEnum.WANO));
        } else if (etapaAtual == 6) {
            if (capitulo == 1) opcoesAtuais.add(IlhaFactory.criar(IlhaEnum.BARATIE));
            else if (capitulo == 2) opcoesAtuais.add(IlhaFactory.criar(sortearCanonicaCap2()));
            else if (capitulo == 3) opcoesAtuais.add(IlhaFactory.criar(IlhaEnum.DRESSROSA));
            else opcoesAtuais.add(IlhaFactory.criar(IlhaEnum.EGGHEAD));
        } else if (etapaAtual == 9) {
            if (capitulo == 1) opcoesAtuais.add(IlhaFactory.criar(IlhaEnum.ARLONG_PARK));
            else if (capitulo == 2) opcoesAtuais.add(IlhaFactory.criar(IlhaEnum.MARINEFORD));
            else if (capitulo == 3) opcoesAtuais.add(IlhaFactory.criar(IlhaEnum.WHOLE_CAKE));
            else opcoesAtuais.add(IlhaFactory.criar(IlhaEnum.ELBAPH));
        } else {
            // Aleatorio (1, 2, 4, 5, 7, 8)
            int nNodes = 3;
            for (int i = 0; i < nNodes; i++) {
                int tipo = random.nextInt(10);
                if (tipo < 2) {
                    opcoesAtuais.add(new IlhaLoja());
                } else if (tipo < 4) {
                    opcoesAtuais.add(new IlhaDescanso());
                } else {
                    // Generica
                    List<IlhasGenericasEnum> disponiveis = new ArrayList<>();
                    for (IlhasGenericasEnum ig : IlhasGenericasEnum.values()) {
                        if ((ig.getCapitulo() == capitulo || ig.getCapitulo() == 0) && !ilhasMostradas.contains(ig)) {
                            disponiveis.add(ig);
                        }
                    }
                    if (disponiveis.isEmpty()) {
                        opcoesAtuais.add(IlhaFactory.criarGenerica(IlhasGenericasEnum.BARCO_PIRATA_INIMIGO));
                    } else {
                        IlhasGenericasEnum sorteada = disponiveis.get(random.nextInt(disponiveis.size()));
                        ilhasMostradas.add(sorteada);
                        opcoesAtuais.add(IlhaFactory.criarGenerica(sorteada));
                    }
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
            this.ilhasConcluidas.clear();
        }

        gerarProximosNos();
    }

    // Compatibilidade com logica legada de GameManager
    public void avancarParaNo(NoMapa proximo) { 
        this.noAtual = proximo;
        this.etapaAtual++;
        if (this.etapaAtual > MAX_ETAPAS_POR_CAPITULO) {
            this.etapaAtual = 0;
            this.capitulo++;
            this.ilhasConcluidas.clear();
        }
    }

    public int getEtapaAtual() { return etapaAtual; }
    public int getCapitulo() { return capitulo; }
    public NoMapa getNoAtual() { return noAtual; }

    // Retorna a fusao do historico com as opcoes atuais para a TelaMapa legada (se necessario)
    public java.util.List<Ilha> getIlhas() { 
        List<Ilha> todas = new ArrayList<>(ilhasConcluidas);
        todas.addAll(opcoesAtuais);
        return todas;
    }
}
