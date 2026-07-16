package progressao;

public enum IlhaEnum {
    // East Blue
    SHELLS_TOWN(RegiaoIlha.EAST_BLUE),
    ORANGE_TOWN(RegiaoIlha.EAST_BLUE),
    VILA_SYRUP(RegiaoIlha.EAST_BLUE),
    BARATIE(RegiaoIlha.EAST_BLUE),
    ARLONG_PARK(RegiaoIlha.EAST_BLUE),
    LOGUETOWN(RegiaoIlha.EAST_BLUE),

    // Paraíso
    LITTLE_GARDEN(RegiaoIlha.PARAISO),
    ILHA_DRUM(RegiaoIlha.PARAISO),
    ALABASTA(RegiaoIlha.PARAISO),
    JAYA(RegiaoIlha.PARAISO),
    SKYPIEA(RegiaoIlha.PARAISO),
    ENIES_LOBBY(RegiaoIlha.PARAISO),
    THRILLER_BARK(RegiaoIlha.PARAISO),
    SABAODY(RegiaoIlha.PARAISO),
    IMPEL_DOWN(RegiaoIlha.PARAISO),
    MARINEFORD(RegiaoIlha.PARAISO),

    // Novo Mundo
    ILHA_HOMENS_PEIXE(RegiaoIlha.NOVO_MUNDO),
    PUNK_HAZARD(RegiaoIlha.NOVO_MUNDO),
    DRESSROSA(RegiaoIlha.NOVO_MUNDO),
    WHOLE_CAKE(RegiaoIlha.NOVO_MUNDO),
    WANO(RegiaoIlha.NOVO_MUNDO),
    EGGHEAD(RegiaoIlha.NOVO_MUNDO),
    ELBAPH(RegiaoIlha.NOVO_MUNDO);

    private final RegiaoIlha regiao;

    IlhaEnum(RegiaoIlha regiao) {
        this.regiao = regiao;
    }

    public RegiaoIlha getRegiao() {
        return regiao;
    }
}
