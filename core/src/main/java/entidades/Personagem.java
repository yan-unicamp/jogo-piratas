package entidades;

import java.util.ArrayList;
import java.util.List;

public abstract class Personagem {
    protected String nome;
    protected int vidaAtual;
    protected int vidaMaxima;
    protected float defesa;
    protected float defesaAtual; 
    protected int iniciativa;
    protected java.util.List<Habilidade> habilidades;
    protected String caminhoImagem;
    private int turnosDePausa;

    public Personagem(String nome, int vidaMaxima, float defesa, int iniciativa, String caminhoImagem) {
        this.nome = nome;
        this.vidaMaxima = vidaMaxima;
        this.vidaAtual = vidaMaxima;
        this.defesa = defesa;
        this.defesaAtual = defesa;
        this.iniciativa = iniciativa;
        this.habilidades = new ArrayList<>();
        this.caminhoImagem = caminhoImagem;
        this.turnosDePausa = 0;
    }


    public void receberDano(int valor) {
        int dano = (int)(valor * defesaAtual);
        this.vidaAtual -= dano;
        if (this.vidaAtual < 1)
            this.vidaAtual = 0;
    }

    public void curar(int valor) {
        this.vidaAtual += valor;
        if (this.vidaAtual > this.vidaMaxima) {
            this.vidaAtual = this.vidaMaxima;
        }
    }

    public void aumentarDefesaTemporaria(float valor) {
        float novadefesa = defesa - valor;
        if (novadefesa < 0)
            novadefesa = 0; // Impede que a defesa fique negativa (o que faria o dano curar o personagem)
        
        if (novadefesa < this.defesaAtual) {
            this.defesaAtual = novadefesa;
        }
    }

    public void resetarDefesa() {
        this.defesaAtual = this.defesa;
        this.turnosDePausa = 0;
    }

    public boolean estaVivo() {
        return this.vidaAtual > 0;
    }

    public String getNome() {

        return nome;
    }

    public int getVidaAtual() {

        return vidaAtual;
    }

    public int getVidaMaxima() {

        return vidaMaxima;
    }

    public float getDefesa() {
        return defesaAtual;
    }

    public int getTurnosDePausa() {
        return turnosDePausa;
    }

    public void setTurnosDePausa(int turnosDePausa) {
        this.turnosDePausa = turnosDePausa;
    }

    public void decrementarTurnoDePausa() {
        if (this.turnosDePausa > 0) {
            this.turnosDePausa--;
        }
    }

    public int getIniciativa() {

        return iniciativa;
    }

    public List<Habilidade> getHabilidades() {

        return habilidades;
    }

    public String getCaminhoImagem() { 
        return caminhoImagem; 
    }

    public void adicionarHabilidade(Habilidade habilidade) { 
        if (habilidade.isEspecial()) {
            for (Habilidade h : this.habilidades) {
                if (h.isEspecial()) {
                    h.setEspecial(false);
                }
            }
        }
        this.habilidades.add(habilidade);
    }
    
    protected com.badlogic.gdx.graphics.Texture texturaCarregada;
    
    public com.badlogic.gdx.graphics.Texture getTextura() {
        if (texturaCarregada != null) return texturaCarregada;
        if (caminhoImagem == null || caminhoImagem.isEmpty()) return null;
        
        String[] possiblePaths = {
            "personagens/chapeu_de_palha/" + caminhoImagem,
            "personagens/" + caminhoImagem,
            "inimigos/" + caminhoImagem,
            "inimigos/capangas/" + caminhoImagem,
            caminhoImagem
        };
        
        for (String path : possiblePaths) {
            if (com.badlogic.gdx.Gdx.files.internal(path).exists()) {
                texturaCarregada = new com.badlogic.gdx.graphics.Texture(com.badlogic.gdx.Gdx.files.internal(path));
                return texturaCarregada;
            }
        }
        
        String fallbackPath = "personagens/aliados_especiais/placeholder.png";
        
        if (this instanceof Inimigo) {
            String n = (nome != null) ? nome.toLowerCase() : "";
            String i = (caminhoImagem != null) ? caminhoImagem.toLowerCase() : "";
            if (n.contains("marinh") || i.contains("marinh") || n.contains("smoker") || n.contains("akainu") || n.contains("kizaru") || n.contains("lucci") || n.contains("magellan")) {
                fallbackPath = "inimigos/capangas/capanga_marinha.png";
            } else {
                fallbackPath = "inimigos/capangas/capanga_pirata.png";
            }
        }
        
        if (com.badlogic.gdx.Gdx.files.internal(fallbackPath).exists()) {
            texturaCarregada = new com.badlogic.gdx.graphics.Texture(com.badlogic.gdx.Gdx.files.internal(fallbackPath));
            return texturaCarregada;
        }
        
        String defaultPlaceholder = "personagens/aliados_especiais/placeholder.png";
        if (com.badlogic.gdx.Gdx.files.internal(defaultPlaceholder).exists()) {
            texturaCarregada = new com.badlogic.gdx.graphics.Texture(com.badlogic.gdx.Gdx.files.internal(defaultPlaceholder));
            return texturaCarregada;
        }
        return null;
    }
}