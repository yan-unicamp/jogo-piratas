package progressao;

import sistema.Tripulacao;

public class Loja {

    public enum ItemLoja {
        CARNE_COM_OSSO("Recupera 40 de HP de um aliado", 50),
        ONIGIRI_FRIO("Recupera 80 de HP de um aliado", 120),
        GARRAFA_DE_COLA("Recupera 30 de HP de um aliado", 40),
        SOPA_FRUTOS_DO_MAR("Recupera 60 de HP de toda a equipe", 250),
        RUMBLE_BALL("Aumenta o Ataque em +15% por 3 turnos", 100),
        PIMENTA_ALABASTA("Aumenta o Ataque em +30% por 2 turnos, mas o usuário perde -5 HP no processo", 150),
        SAKE_WANO("Adiciona +15 de Dano Fixo extra no próximo ataque realizado", 80),
        BEBIDA_ENERGETICA("Aumenta o Ataque de toda a equipe em +10% por 3 turnos", 220),
        ESTRELA_DE_FUMAÇA("Cega o inimigo, reduzindo o Dano dele em -20% por 2 turnos", 90),
        GAS_DO_SONO("Causa letargia, reduzindo o Ataque do inimigo em -15% por 3 turnos", 120),
        FIO_DE_TEIA("Prende o inimigo, reduzindo o dano do próximo golpe dele pela metade (-50%)", 180),
        SAL_PURIFICADOR("Remove buffs do inimigo e reduz o Ataque base dele em -10% por 2 turnos", 150),
        CAPA_DA_MARINHA("Aumenta a Defesa de um aliado em +15 por 3 turnos", 100),
        CASCA_TRITÃO("Cria uma barreira que absorve os próximos 50 pontos de dano recebidos", 160),
        ESSENCIA_HAKI("Garante Imunidade (0 de dano) contra o próximo ataque físico recebido por um aliado", 300),
        PO_DE_FERRO("Aumenta a Defesa de toda a equipe em +10 por 3 turnos", 240),
        VIVRE_CARD("Permite fugir automaticamente de 1 batalha comum com 100% de sucesso (não funciona em chefes)",
                150),
        LOG_POSE("Aumenta em 1 o número de ilhas possíveis a se navegar por uma vez", 400);

        private final String nome;
        private final int preco;

        ItemLoja(String nome, int preco) {
            this.nome = nome;
            this.preco = preco;
        }

        public String getNome() {
            return nome;
        }

        public int getPreco() {
            return preco;
        }
    }

    public ItemLoja[] getCatalogo() {
        return ItemLoja.values();
    }

    public void exibirCatalogo() {
    }

    public boolean comprarMelhoria(Tripulacao cliente, ItemLoja item) {
        return cliente.gastarDinheiro(item.getPreco());
    }
}
