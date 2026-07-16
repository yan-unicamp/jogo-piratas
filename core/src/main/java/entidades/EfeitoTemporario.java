package entidades;

public class EfeitoTemporario {

    public enum Tipo {
        MULTIPLICADOR_DANO,      // Multiplica o dano causado (ex: 1.15 para +15%)
        MULTIPLICADOR_DEFESA,    // Multiplica o dano sofrido (ex: 0.8 para -20% dano tomado)
        REDUCAO_DANO_INIMIGO,    // Multiplica o dano do inimigo (ex: 0.8 para CEGAR)
        DANO_FIXO_EXTRA,         // Adiciona valor fixo ao causar dano
        PRENDER,                 // Corta dano do próximo golpe pela metade (0.5)
        BARREIRA,                // Absorve X pontos de dano
        IMUNIDADE                // 0 dano recebido
    }

    private Tipo tipo;
    private float valor;
    private int turnosRestantes;

    public EfeitoTemporario(Tipo tipo, float valor, int turnosRestantes) {
        this.tipo = tipo;
        this.valor = valor;
        this.turnosRestantes = turnosRestantes;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public int getTurnosRestantes() {
        return turnosRestantes;
    }

    public void decrescerTurno() {
        if (turnosRestantes > 0) {
            turnosRestantes--;
        }
    }
    
    public void zerarTurnos() {
        this.turnosRestantes = 0;
    }

    public boolean expirou() {
        return turnosRestantes <= 0;
    }
}
