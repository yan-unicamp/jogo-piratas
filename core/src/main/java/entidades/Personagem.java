package entidades;

import java.util.ArrayList;
import java.util.List;

public abstract class Personagem {
    protected String nome;
    protected float vidaAtual;
    protected int vidaMaxima;
    protected float defesa;
    protected float defesaAtual; 
    protected int iniciativa;
    protected List<Habilidade> habilidades;
    protected String caminhoImagem;
    protected List<EfeitoTemporario> efeitos;

    public Personagem(String nome, int vidaMaxima, float defesa, int iniciativa, String caminhoImagem) {
        this.nome = nome;
        this.vidaMaxima = vidaMaxima;
        this.vidaAtual = vidaMaxima;
        this.defesa = defesa;
        this.defesaAtual = defesa;
        this.iniciativa = iniciativa;
        this.habilidades = new ArrayList<>();
        this.caminhoImagem = caminhoImagem;
        this.efeitos = new ArrayList<>();
    }

    public void receberDano(float valor) {
        // Verifica imunidade
        for (EfeitoTemporario e : efeitos) {
            if (e.getTipo() == EfeitoTemporario.Tipo.IMUNIDADE) {
                e.zerarTurnos(); // Consome a imunidade
                return; // 0 dano
            }
        }

        // Verifica multiplicadores de defesa (ex: -20% dano tomado = 0.8)
        float multiplicadorDefesa = 1.0f;
        for (EfeitoTemporario e : efeitos) {
            if (e.getTipo() == EfeitoTemporario.Tipo.MULTIPLICADOR_DEFESA) {
                multiplicadorDefesa *= e.getValor();
            }
        }
        
        float danoFinal = valor * defesaAtual * multiplicadorDefesa;

        // Verifica barreira
        for (EfeitoTemporario e : efeitos) {
            if (e.getTipo() == EfeitoTemporario.Tipo.BARREIRA) {
                float absorvido = Math.min(danoFinal, e.getValor());
                e.setValor(e.getValor() - absorvido);
                danoFinal -= absorvido;
                if (e.getValor() <= 0) {
                    e.zerarTurnos(); // Quebra a barreira
                }
                break; // Apenas uma barreira atua por vez
            }
        }

        this.vidaAtual -= danoFinal;
        if (this.vidaAtual < 1) this.vidaAtual = 0; 
    }

    public void curar(float valor) {
        this.vidaAtual += valor;
        if (this.vidaAtual > this.vidaMaxima) {
            this.vidaAtual = this.vidaMaxima;
        }
    }

    public void aumentarDefesaTemporaria(float valor) { 
        float novadefesa = defesa - valor;
        if (novadefesa < 0) novadefesa = 0; // Impede que a defesa fique negativa
        this.defesaAtual = novadefesa;
    }

    public void resetarDefesa() {
        this.defesaAtual = this.defesa;
    }

    public void adicionarEfeito(EfeitoTemporario efeito) {
        this.efeitos.add(efeito);
    }

    public void processarEfeitosTurno() {
        List<EfeitoTemporario> expirados = new ArrayList<>();
        for (EfeitoTemporario e : efeitos) {
            e.decrescerTurno();
            if (e.expirou()) {
                expirados.add(e);
            }
        }
        efeitos.removeAll(expirados);
    }
    
    public void limparEfeitos() {
        efeitos.clear();
    }

    public float getMultiplicadorAtaque() {
        float mult = 1.0f;
        for (EfeitoTemporario e : efeitos) {
            if (e.getTipo() == EfeitoTemporario.Tipo.MULTIPLICADOR_DANO || e.getTipo() == EfeitoTemporario.Tipo.REDUCAO_DANO_INIMIGO) {
                mult *= e.getValor();
            } else if (e.getTipo() == EfeitoTemporario.Tipo.PRENDER) {
                mult *= e.getValor();
                e.zerarTurnos(); // Consome a teia no próximo golpe
            }
        }
        return mult;
    }

    public float getDanoFixoExtra() {
        float extra = 0f;
        for (EfeitoTemporario e : efeitos) {
            if (e.getTipo() == EfeitoTemporario.Tipo.DANO_FIXO_EXTRA) {
                extra += e.getValor();
                e.zerarTurnos(); // Consome o dano extra
            }
        }
        return extra;
    }

    public boolean estaVivo() {
        return this.vidaAtual > 0;
    }

    public String getNome() { 

        return nome; 
    }
    public int getVidaAtual() {
        return (int) vidaAtual;
    }
    public int getVidaMaxima() {
        return vidaMaxima;
    }
    public float getDefesa() { 
        
        return defesa; 
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
        this.habilidades.add(habilidade);
    }
}
