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
    private final int MAX_ETAPAS_POR_CAPITULO = 10;
    private NoMapa noAtual;
    
    private List<Ilha> ilhasConcluidas;
    private List<Ilha> opcoesAtuais;
    private Set<IlhasGenericasEnum> ilhasMostradas;
    private Random random;

    public Mapa() {
        this.ilhasConcluidas = new ArrayList<>();
        this.opcoesAtuais = new ArrayList<>();
        this.ilhasMostradas = new HashSet<>();
        this.random = new Random();
        resetar();
    }

    public void resetar() {
        this.etapaAtual = 0;
        this.capitulo = 1;
        this.ilhasConcluidas.clear();
        this.opcoesAtuais.clear();
        this.ilhasMostradas.clear();
        gerarProximosNos();
    }

    public List<Ilha> getIlhasConcluidas() { return ilhasConcluidas; }
    public List<Ilha> getOpcoesAtuais() { return opcoesAtuais; }

    private List<IlhaEnum> canonicasCap1 = new ArrayList<>();
    private IlhaEnum sortearCanonicaCap1() {
        if (canonicasCap1.isEmpty()) {
            canonicasCap1.addAll(java.util.Arrays.asList(IlhaEnum.VILA_SYRUP, IlhaEnum.BARATIE, IlhaEnum.ARLONG_PARK));
        }
        int idx = random.nextInt(canonicasCap1.size());
        return canonicasCap1.remove(idx);
    }

    private List<IlhaEnum> canonicasCap2 = new ArrayList<>();
    private IlhaEnum sortearCanonicaCap2() {
        if (canonicasCap2.isEmpty()) {
            canonicasCap2.addAll(java.util.Arrays.asList(IlhaEnum.THRILLER_BARK, IlhaEnum.ALABASTA, IlhaEnum.ENIES_LOBBY, IlhaEnum.ILHA_DRUM));
        }
        int idx = random.nextInt(canonicasCap2.size());
        return canonicasCap2.remove(idx);
    }

    private List<IlhaEnum> canonicasCap3 = new ArrayList<>();
    private IlhaEnum sortearCanonicaCap3() {
        if (canonicasCap3.isEmpty()) {
            canonicasCap3.addAll(java.util.Arrays.asList(IlhaEnum.DRESSROSA, IlhaEnum.WHOLE_CAKE, IlhaEnum.WANO));
        }
        int idx = random.nextInt(canonicasCap3.size());
        return canonicasCap3.remove(idx);
    }

    public void gerarProximosNos() {
        opcoesAtuais.clear();

        if (etapaAtual == 0) {
            opcoesAtuais.add(IlhaFactory.criar(IlhaEnum.SHELLS_TOWN));
        } else if (etapaAtual == 3) {
            if (capitulo == 1) opcoesAtuais.add(IlhaFactory.criar(sortearCanonicaCap1()));
            else if (capitulo == 2) opcoesAtuais.add(IlhaFactory.criar(sortearCanonicaCap2()));
            else if (capitulo == 3) opcoesAtuais.add(IlhaFactory.criar(sortearCanonicaCap3()));
            else opcoesAtuais.add(IlhaFactory.criar(IlhaEnum.WANO));
        } else if (etapaAtual == 6) {
            if (capitulo == 1) opcoesAtuais.add(IlhaFactory.criar(sortearCanonicaCap1()));
            else if (capitulo == 2) opcoesAtuais.add(IlhaFactory.criar(sortearCanonicaCap2()));
            else if (capitulo == 3) opcoesAtuais.add(IlhaFactory.criar(sortearCanonicaCap3()));
            else opcoesAtuais.add(IlhaFactory.criar(IlhaEnum.WANO));
        } else if (etapaAtual == 9) {
            if (capitulo == 1) opcoesAtuais.add(IlhaFactory.criar(sortearCanonicaCap1()));
            else if (capitulo == 2) opcoesAtuais.add(IlhaFactory.criar(sortearCanonicaCap2()));
            else if (capitulo == 3) opcoesAtuais.add(IlhaFactory.criar(sortearCanonicaCap3()));
            else opcoesAtuais.add(IlhaFactory.criar(IlhaEnum.WANO));
        } else if (etapaAtual == 10) {
            if (capitulo == 1) opcoesAtuais.add(IlhaFactory.criar(IlhaEnum.LOGUETOWN));
            else if (capitulo == 2) opcoesAtuais.add(IlhaFactory.criar(IlhaEnum.MARINEFORD));
            else if (capitulo == 3) opcoesAtuais.add(IlhaFactory.criar(IlhaEnum.ELBAPH));
            else opcoesAtuais.add(IlhaFactory.criar(IlhaEnum.ELBAPH));
        } else {
            int nNodes = 3;
            for (int i = 0; i < nNodes; i++) {
                int tipo = random.nextInt(10);
                if (tipo < 2) {
                    opcoesAtuais.add(new IlhaLoja());
                } else if (tipo < 4) {
                    opcoesAtuais.add(new IlhaDescanso());
                } else {
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
            this.capitulo++;
            this.etapaAtual = 1; // Capítulos seguintes começam na etapa 1 (pulam a profundidade 0)
            this.ilhasConcluidas.clear();
        }

        gerarProximosNos();
    }

    public void avancarParaNo(NoMapa proximo) { 
        this.noAtual = proximo;
        this.etapaAtual++;
        if (this.etapaAtual > MAX_ETAPAS_POR_CAPITULO) {
            this.capitulo++;
            this.etapaAtual = 1; // Capítulos seguintes começam na etapa 1 (pulam a profundidade 0)
            this.ilhasConcluidas.clear();
        }
    }

    public int getEtapaAtual() { return etapaAtual; }
    public int getCapitulo() { return capitulo; }
    public NoMapa getNoAtual() { return noAtual; }
    
    public java.util.Set<IlhasGenericasEnum> getIlhasMostradas() {
        return ilhasMostradas;
    }

    public java.util.List<Ilha> getIlhas() { 
        List<Ilha> todas = new ArrayList<>(ilhasConcluidas);
        todas.addAll(opcoesAtuais);
        return todas;
    }

    public void restaurarEstado(int capitulo, int etapaAtual, List<String> opcoesIds, List<String> ilhasMostradasNomes, List<String> ilhasConcluidasIds) {
        this.capitulo = capitulo;
        this.etapaAtual = etapaAtual;
        
        this.ilhasMostradas.clear();
        for (String igName : ilhasMostradasNomes) {
            try {
                this.ilhasMostradas.add(IlhasGenericasEnum.valueOf(igName));
            } catch (Exception e) {}
        }

        this.opcoesAtuais.clear();
        for (String idSave : opcoesIds) {
            Ilha ilha = sistema.SaveManager.recriarIlhaPeloIdSave(idSave);
            if (ilha != null) {
                this.opcoesAtuais.add(ilha);
            }
        }
        
        this.ilhasConcluidas.clear();
        for (String idSave : ilhasConcluidasIds) {
            Ilha ilha = sistema.SaveManager.recriarIlhaPeloIdSave(idSave);
            if (ilha != null) {
                this.ilhasConcluidas.add(ilha);
            }
        }
    }
}
