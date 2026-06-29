package progressao;

import sistema.Tripulacao;
import entidades.Aliado;

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
        ESTRELA_DE_FUMACA("Cega o inimigo, reduzindo o Dano dele em -20% por 2 turnos", 90),
        GAS_DO_SONO("Causa letargia, reduzindo o Ataque do inimigo em -15% por 3 turnos", 120),
        FIO_DE_TEIA("Prende o inimigo, reduzindo o dano do próximo golpe dele pela metade (-50%)", 180),
        SAL_PURIFICADOR("Remove buffs do inimigo e reduz o Ataque base dele em -10% por 2 turnos", 150),
        CAPA_DA_MARINHA("Aumenta a Defesa de um aliado em +15 por 3 turnos", 100),
        CASCA_TRITAO("Cria uma barreira que absorve os próximos 50 pontos de dano recebidos", 160),
        ESSENCIA_HAKI("Garante Imunidade (0 de dano) contra o próximo ataque físico recebido por um aliado", 300),
        PO_DE_FERRO("Aumenta a Defesa de toda a equipe em +10 por 3 turnos", 240),
        VIVRE_CARD("Permite fugir automaticamente de 1 batalha comum com 100% de sucesso (não funciona em chefes)", 150),
        LOG_POSE("Aumenta em 1 o número de ilhas possíveis a se navegar por uma vez", 400);

        private final String descricao;
        private final int preco;

        ItemLoja(String descricao, int preco) {
            this.descricao = descricao;
            this.preco = preco;
        }

        public String getDescricao() { return descricao; }
        public int getPreco() { return preco; }

        /** @deprecated Use getDescricao() — renomeado para clareza. */
        @Deprecated
        public String getNome() { return descricao; }
    }

    /** @return array com todos os itens disponíveis na loja. Usado pela TelaLoja. */
    public ItemLoja[] getCatalogo() {
        return ItemLoja.values();
    }

    /**
     * Tenta realizar a compra de um item para um aliado alvo.
     * Debita o dinheiro da tripulação e aplica o efeito no alvo.
     *
     * @param cliente   A tripulação que está comprando.
     * @param item      O item selecionado.
     * @param alvo      O aliado que receberá o efeito (null para itens de equipe).
     * @return true se a compra foi realizada com sucesso; false se saldo insuficiente.
     */
    public boolean comprarMelhoria(Tripulacao cliente, ItemLoja item, Aliado alvo) {
        if (!cliente.gastarDinheiro(item.getPreco())) {
            return false;
        }
        aplicarEfeito(item, alvo, cliente);
        return true;
    }

    /**
     * Mantido por compatibilidade — compra sem alvo específico (itens de equipe).
     */
    public boolean comprarMelhoria(Tripulacao cliente, ItemLoja item) {
        return comprarMelhoria(cliente, item, null);
    }

    /**
     * Aplica o efeito de um item a um alvo ou à equipe inteira.
     * Itens de cura/defesa individual: exigem alvo != null.
     * Itens de equipe: operam sobre a tripulacao.
     *
     * NOTA: Efeitos que dependem de curar() e receberDano() ficarão completos
     * quando o Membro 1 implementar esses métodos em Personagem (Fase 2).
     */
    public void aplicarEfeito(ItemLoja item, Aliado alvo, Tripulacao tripulacao) {
        switch (item) {
            // --- Cura individual ---
            case CARNE_COM_OSSO:
                if (alvo != null) alvo.curar(40);
                break;
            case GARRAFA_DE_COLA:
                if (alvo != null) alvo.curar(30);
                break;
            case ONIGIRI_FRIO:
                if (alvo != null) alvo.curar(80);
                break;

            // --- Cura de equipe ---
            case SOPA_FRUTOS_DO_MAR:
                if (tripulacao != null) {
                    for (Aliado a : tripulacao.getAliadosVivos()) {
                        a.curar(60);
                    }
                }
                break;

            // --- Buffs de ataque, defesa, debuffs de inimigo, itens especiais ---
            // TODO Fase 3+: Implementar quando o sistema de buffs/turnos estiver pronto
            // (depende de GerenciadorDeBatalha e do sistema de modificadores temporários)
            case RUMBLE_BALL:
            case PIMENTA_ALABASTA:
            case SAKE_WANO:
            case BEBIDA_ENERGETICA:
            case ESTRELA_DE_FUMACA:
            case GAS_DO_SONO:
            case FIO_DE_TEIA:
            case SAL_PURIFICADOR:
            case CAPA_DA_MARINHA:
            case CASCA_TRITAO:
            case ESSENCIA_HAKI:
            case PO_DE_FERRO:
            case VIVRE_CARD:
            case LOG_POSE:
                // Estrutura pronta para receber implementação futura
                break;

            default:
                break;
        }
    }

    /**
     * Versão de debug — útil durante desenvolvimento.
     * Em produção, a TelaLoja usará getCatalogo() diretamente.
     */
    public void exibirCatalogo() {
        ItemLoja[] itens = getCatalogo();
        System.out.println("=== CATÁLOGO DA LOJA ===");
        for (int i = 0; i < itens.length; i++) {
            System.out.printf("[%2d] %-20s | %s | %d ouro%n",
                    i, itens[i].name(), itens[i].getDescricao(), itens[i].getPreco());
        }
        System.out.println("========================");
    }
}
