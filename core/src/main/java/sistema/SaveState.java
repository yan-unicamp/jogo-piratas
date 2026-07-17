package sistema;

import java.util.ArrayList;

public class SaveState {
    public int capitulo;
    public int etapaAtual;
    public int dinheiro;

    public ArrayList<AliadoSave> aliados = new ArrayList<>();
    public ArrayList<String> itens = new ArrayList<>();

    public ArrayList<String> opcoesAtuais = new ArrayList<>();
    public ArrayList<String> ilhasMostradas = new ArrayList<>();
    public ArrayList<String> ilhasConcluidas = new ArrayList<>();

    public static class AliadoSave {
        public String nome;
        public int nivel;
        public int experiencia;
        public int vidaAtual;
        public boolean ativo;
        
        public AliadoSave() {}
    }
}
