package progressao;

public enum IlhasGenericasEnum {
    ORANGE_TOWN(1),
    ILHA_DA_CABRA(1),
    VILA_SHIMOTSUKI(1),
    ILHA_DOS_ANIMAIS_RAROS(1),
    ILHA_DAWN(1),

    WHISKEY_PEAK(2),
    WATER_7(2),
    BATERILLA(2),
    ILHA_BANARO(2),
    LITTLE_GARDEN(2),
    LONG_RING_LONG_LAND(2),
    JAYA(2),
    SKYPIEA(2),
    IMPEL_DOWN(2),

    ZOU(3),
    ILHA_PIRATA(3),
    LODESTAR(3),
    SABAODY(3),
    ILHA_HOMENS_PEIXE(3),
    PUNK_HAZARD(3),
    EGGHEAD(3),

    BARCO_PIRATA_INIMIGO(0);

    private final int capitulo;

    IlhasGenericasEnum(int capitulo) {
        this.capitulo = capitulo;
    }

    public int getCapitulo() {
        return capitulo;
    }
}
