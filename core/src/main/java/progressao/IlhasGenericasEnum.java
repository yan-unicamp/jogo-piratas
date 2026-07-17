package progressao;

public enum IlhasGenericasEnum {
    // East Blue (Capitulo 1)
    ILHA_DA_CABRA(1),
    VILA_SHIMOTSUKI(1),
    ILHA_DOS_ANIMAIS_RAROS(1),
    ILHA_DAWN(1),

    // Paraiso (Capitulo 2)
    WHISKEY_PEAK(2),
    WATER_7(2),
    BATERILLA(2),
    ILHA_BANARO(2),

    // Novo Mundo (Capitulo 3)
    ZOU(3),
    ILHA_PIRATA(3),
    LODESTAR(3),

    // Todos os capitulos
    BARCO_PIRATA_INIMIGO(0);

    private final int capitulo;

    IlhasGenericasEnum(int capitulo) {
        this.capitulo = capitulo;
    }

    public int getCapitulo() {
        return capitulo;
    }
}
