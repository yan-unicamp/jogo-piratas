package progressao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ArvoreNaoBinaria {

    public static class NoDaArvore {
        private NoMapa valor;
        private List<NoDaArvore> filhos;

        public NoDaArvore(NoMapa valor) {
            this.valor = valor;
            this.filhos = new ArrayList<>();
        }

        public void adicionarFilho(NoDaArvore filho) {
            this.filhos.add(filho);
        }

        public NoMapa getValor() {
            return valor;
        }

        public List<NoDaArvore> getFilhos() {
            return filhos;
        }
    }

    public enum TipoNo {
        BATALHA,
        BATALHA_CANONICA,
        BATALHA_FINAL,
        EVENTO,
        DESCANSO
    }

    private int quantidadeFilhos;
    private int profundidadeMax;
    private int capitulo;
    private Random random;

    public ArvoreNaoBinaria(int quantidadeFilhos, int profundidadeMax, int capitulo) {
        this.quantidadeFilhos = quantidadeFilhos;
        this.profundidadeMax = profundidadeMax;
        this.capitulo = capitulo;
        this.random = new Random();
    }

    public int getProfundidadeMax() {
        return profundidadeMax;
    }

    public int getQuantidadeFilhos() {
        return quantidadeFilhos;
    }

    /**
     * Define como os eventos serao distribuidos para os nos com base na profundidade.
     */
    public List<TipoNo> escolherEvento(int profundidadeAtual) {
        List<TipoNo> opcoes = new ArrayList<>();

        if (profundidadeAtual == 3 || profundidadeAtual == 6) {
            opcoes.add(TipoNo.BATALHA_CANONICA);
        } else if (profundidadeAtual == 9) {
            opcoes.add(TipoNo.BATALHA_FINAL);
        } else if (profundidadeAtual == 0) {
            opcoes.add(TipoNo.BATALHA);
        } else if (profundidadeAtual == profundidadeMax) {
            opcoes.add(TipoNo.BATALHA_FINAL);
        } else if (profundidadeAtual == profundidadeMax - 1) {
            opcoes.add(TipoNo.DESCANSO);
        } else if (profundidadeAtual < profundidadeMax / 2) {
            opcoes.addAll(Arrays.asList(TipoNo.BATALHA, TipoNo.BATALHA, TipoNo.EVENTO));
        } else {
            opcoes.addAll(Arrays.asList(TipoNo.BATALHA, TipoNo.EVENTO, TipoNo.DESCANSO));
        }

        return opcoes;
    }

    private NoMapa instanciarNo(TipoNo tipo, int profundidade) {
        int nivel = profundidade + 1;
        switch (tipo) {
            case BATALHA:
                List<entidades.Inimigo> inimigos = new ArrayList<>();
                inimigos.add(factories.PersonagemFactory.criarMarinheiro(nivel));
                return new NoBatalha(inimigos);
            case BATALHA_CANONICA:
                return new NoBatalhaCanonica(factories.PersonagemFactory.criarChefe(nivel));
            case BATALHA_FINAL:
                return new NoBatalhaFinal(factories.PersonagemFactory.criarChefe(nivel));
            case EVENTO:
                return new NoEvento();
            case DESCANSO:
                return null;
            default:
                return new NoEvento();
        }
    }

    /**
     * Cria a arvore completa recursivamente, distribuindo os nos pelas regras.
     */
    public NoDaArvore criarArvore(int profundidade, TipoNo tipoAtual) {
        NoMapa noMapa = instanciarNo(tipoAtual, profundidade);
        NoDaArvore node = new NoDaArvore(noMapa);

        if (profundidade == profundidadeMax) {
            return node;
        }

        List<TipoNo> tiposFilhos = escolherEvento(profundidade + 1);

        for (int i = 0; i < quantidadeFilhos; i++) {
            TipoNo tipoSorteado = tiposFilhos.get(random.nextInt(tiposFilhos.size()));
            NoDaArvore novoFilho = criarArvore(profundidade + 1, tipoSorteado);
            node.adicionarFilho(novoFilho);
        }

        return node;
    }

    /**
     * Ponto de entrada para gerar a arvore.
     */
    public NoDaArvore gerarArvore() {
        return criarArvore(0, escolherEvento(0).get(0));
    }
}
