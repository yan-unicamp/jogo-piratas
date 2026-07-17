package progressao;

public enum IlhaEnum {
    SHELLS_TOWN(RegiaoIlha.EAST_BLUE,
            "Uma ilha com uma base da Marinha, dominada pelo tiranico Capitao Morgan. A justica aqui e tao afiada quanto o seu machado."),
    VILA_SYRUP(RegiaoIlha.EAST_BLUE,
            "Paz e tranquilidade reinam sob uma teia de mentiras. Nesta pacata vila, um plano sombrio se aproxima em silêncio."),
    BARATIE(RegiaoIlha.EAST_BLUE,
            "O restaurante flutuante onde os punhos falam tão alto quanto as panelas. Sob as águas turbulentas, um banquete de batalhas aguarda."),
    ARLONG_PARK(RegiaoIlha.EAST_BLUE,
            "O império dos homens-peixe no East Blue. Lágrimas secaram sob a tirania, e um mapa para a liberdade precisa ser desenhado a sangue."),
    LOGUETOWN(RegiaoIlha.EAST_BLUE,
            "A Cidade do Começo e do Fim. Onde a lenda do Rei dos Piratas nasceu e pereceu, e o destino de novos sonhadores é traçado na fumaça."),

    ILHA_DRUM(RegiaoIlha.PARAISO,
            "Um inverno eterno e um país sem médicos. No topo das montanhas nevadas, um milagre de cerejeiras aguarda por aqueles que têm coração de fogo."),
    ALABASTA(RegiaoIlha.PARAISO,
            "As areias escaldantes de um reino à beira do colapso. Onde a chuva parou de cair e as conspirações dançam sob o sol impiedoso do deserto."),
    ENIES_LOBBY(RegiaoIlha.PARAISO,
            "A ilha judiciária do Governo Mundial. Declarar guerra contra o mundo inteiro é o único caminho para resgatar companheiros perdidos na escuridão."),
    THRILLER_BARK(RegiaoIlha.PARAISO,
            "O navio fantasma gigante preso no nevoeiro. Sombras roubadas e zumbis macabros espreitam na escuridão assombrada."),
    MARINEFORD(RegiaoIlha.PARAISO,
            "A Guerra dos Melhores. O mundo prende a respiração enquanto lendas se chocam em uma batalha que mudará a era dos piratas para sempre."),

    DRESSROSA(RegiaoIlha.NOVO_MUNDO,
            "O país do amor, da paixão e dos brinquedos. Mas por trás dos sorrisos brilhantes, fios manipuladores controlam um reino em agonia."),
    WHOLE_CAKE(RegiaoIlha.NOVO_MUNDO,
            "Um arquipélago doce governado por uma Imperatriz implacável. Festas do chá onde o preço do convite pode ser a própria alma."),
    WANO(RegiaoIlha.NOVO_MUNDO,
            "O país dos samurais fechado para o mundo. Onde flores de cerejeira lamentam sob a tirania de um dragão implacável, esperando o amanhecer."),
    ELBAPH(RegiaoIlha.NOVO_MUNDO,
            "A lendária Terra dos Gigantes. A honra guerreira e a força bruta governam este domínio colossal, a morada dos mais fortes guerreiros do mundo.");

    private final RegiaoIlha regiao;
    private final String descricao;

    IlhaEnum(RegiaoIlha regiao, String descricao) {
        this.regiao = regiao;
        this.descricao = descricao;
    }

    public RegiaoIlha getRegiao() {
        return regiao;
    }

    public String getDescricao() {
        return descricao;
    }
}
