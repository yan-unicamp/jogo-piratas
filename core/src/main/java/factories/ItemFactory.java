package factories;

import entidades.Item;

public class ItemFactory {

    public static Item criarBandagemSimples() {
        return new Item("Bandagem Simples", "Cura sangramentos leves e restaura 15 de vida", 5, 15);
    }

    public static Item criarCarneComOsso() {
        return new Item("Carne com Osso", "A comida favorita do capitao. Restaura 100 de vida", 45, 100);
    }
    
    public static Item criarKitMedico() {
        return new Item("Kit Medico do Chopper", "Cura avancada, restaura 150 de vida", 80, 150);
    }
}
