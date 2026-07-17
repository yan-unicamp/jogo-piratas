package progressao;

public enum IlhaEnum {
    // East Blue
    SHELLS_TOWN(RegiaoIlha.EAST_BLUE, "A brisa salgada carrega o peso da opressão. Uma cidade pacata, ofuscada pela sombra de um machado tirânico. A sua jornada começa aqui."),
    ORANGE_TOWN(RegiaoIlha.EAST_BLUE, "Uma cidade antes próspera, agora refém de piratas e palhaços que espalham terror. O riso aqui soa como um aviso mortal."),
    VILA_SYRUP(RegiaoIlha.EAST_BLUE, "Paz e tranquilidade reinam sob uma teia de mentiras. Nesta pacata vila, um plano sombrio se aproxima em silêncio."),
    BARATIE(RegiaoIlha.EAST_BLUE, "O restaurante flutuante onde os punhos falam tão alto quanto as panelas. Sob as águas turbulentas, um banquete de batalhas aguarda."),
    ARLONG_PARK(RegiaoIlha.EAST_BLUE, "O império dos homens-peixe no East Blue. Lágrimas secaram sob a tirania, e um mapa para a liberdade precisa ser desenhado a sangue."),
    LOGUETOWN(RegiaoIlha.EAST_BLUE, "A Cidade do Começo e do Fim. Onde a lenda do Rei dos Piratas nasceu e pereceu, e o destino de novos sonhadores é traçado na fumaça."),

    // Paraiso
    WHISKY_PEAK(RegiaoIlha.PARAISO, "A ilha que recebe piratas com festa e vinho, mas que esconde facas e caçadores sob a luz da lua. O paraíso é apenas uma ilusão."),
    LITTLE_GARDEN(RegiaoIlha.PARAISO, "O tempo parou nesta ilha pré-histórica. Dinossauros vagam e gigantes duelam em nome de uma honra que transcende séculos."),
    ILHA_DRUM(RegiaoIlha.PARAISO, "Um inverno eterno e um país sem médicos. No topo das montanhas nevadas, um milagre de cerejeiras aguarda por aqueles que têm coração de fogo."),
    ALABASTA(RegiaoIlha.PARAISO, "As areias escaldantes de um reino à beira do colapso. Onde a chuva parou de cair e as conspirações dançam sob o sol impiedoso do deserto."),
    JAYA(RegiaoIlha.PARAISO, "A cidade sem lei onde sonhos são ridicularizados. Mas nas sombras de Mock Town, pistas apontam para mistérios inexplorados."),
    SKYPIEA(RegiaoIlha.PARAISO, "A terra sagrada além das nuvens. Onde a terra é um tesouro e um falso deus governa com relâmpagos e medo. Ouça o sino de ouro tocar."),
    ENIES_LOBBY(RegiaoIlha.PARAISO, "A ilha judiciária do Governo Mundial. Declarar guerra contra o mundo inteiro é o único caminho para resgatar companheiros perdidos na escuridão."),
    THRILLER_BARK(RegiaoIlha.PARAISO, "O navio fantasma gigante preso no nevoeiro. Sombras roubadas e zumbis macabros espreitam na escuridão assombrada."),
    SABAODY(RegiaoIlha.PARAISO, "Um arquipélago feito de bolhas gigantes, onde sonhos encontram a realidade cruel. Supernovas se reúnem antes de mergulhar no abismo."),
    IMPEL_DOWN(RegiaoIlha.PARAISO, "A grande prisão submarina. Um mergulho infernal em busca de salvação, onde cada andar é um pesadelo pior que o anterior."),
    MARINEFORD(RegiaoIlha.PARAISO, "A Guerra dos Melhores. O mundo prende a respiração enquanto lendas se chocam em uma batalha que mudará a era dos piratas para sempre."),

    // Novo Mundo
    ILHA_HOMENS_PEIXE(RegiaoIlha.NOVO_MUNDO, "O paraíso submarino a dez mil metros de profundidade. Uma terra dividida pelo preconceito, onde o ódio do passado ameaça o futuro."),
    PUNK_HAZARD(RegiaoIlha.NOVO_MUNDO, "Uma ilha dividida entre fogo escaldante e gelo cortante. O laboratório de atrocidades onde alianças perigosas são forjadas."),
    DRESSROSA(RegiaoIlha.NOVO_MUNDO, "O país do amor, da paixão e dos brinquedos. Mas por trás dos sorrisos brilhantes, fios manipuladores controlam um reino em agonia."),
    WHOLE_CAKE(RegiaoIlha.NOVO_MUNDO, "Um arquipélago doce governado por uma Imperatriz implacável. Festas do chá onde o preço do convite pode ser a própria alma."),
    WANO(RegiaoIlha.NOVO_MUNDO, "O país dos samurais fechado para o mundo. Onde flores de cerejeira lamentam sob a tirania de um dragão implacável, esperando o amanhecer."),
    EGGHEAD(RegiaoIlha.NOVO_MUNDO, "A Ilha do Futuro. Um vislumbre de 500 anos à frente, onde a ciência avançada colide com os mistérios obscuros do Século Perdido."),
    ELBAPH(RegiaoIlha.NOVO_MUNDO, "A lendária Terra dos Gigantes. A honra guerreira e a força bruta governam este domínio colossal, a morada dos mais fortes guerreiros do mundo.");

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
