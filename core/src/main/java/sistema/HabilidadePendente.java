package sistema;

import entidades.Aliado;
import entidades.Habilidade;

public class HabilidadePendente {
    private Aliado aliado;
    private Habilidade habilidade;

    public HabilidadePendente(Aliado aliado, Habilidade habilidade) {
        this.aliado = aliado;
        this.habilidade = habilidade;
    }

    public Aliado getAliado() {
        return aliado;
    }

    public Habilidade getHabilidade() {
        return habilidade;
    }
}
