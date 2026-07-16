package entidades;

import java.util.ArrayList;

public class ConfiguracaoBatalha {
    private String tituloArco;
    private String caminhoFundo;
    private ArrayList<Personagem> aliados;
    private ArrayList<Personagem> inimigos;

    public ConfiguracaoBatalha(String tituloArco, String caminhoFundo, ArrayList<Personagem> aliados, ArrayList<Personagem> inimigos) {
        this.tituloArco = tituloArco;
        this.caminhoFundo = caminhoFundo;
        this.aliados = aliados;
        this.inimigos = inimigos;
    }

    public String getTituloArco() {
        return tituloArco;
    }

    public String getCaminhoFundo() {
        return caminhoFundo;
    }

    public ArrayList<Personagem> getAliados() {
        return aliados;
    }

    public ArrayList<Personagem> getInimigos() {
        return inimigos;
    }
}
