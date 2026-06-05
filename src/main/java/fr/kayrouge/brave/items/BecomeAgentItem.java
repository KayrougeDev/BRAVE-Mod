package fr.kayrouge.brave.items;

import fr.kayrouge.brave.BRAVE;
import fr.kayrouge.brave.agents.Agent;
import fr.kayrouge.brave.agents.Agents;
import fr.kayrouge.brave.component.BComponents;
import net.minecraft.util.Util;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.function.Consumer;

public class BecomeAgentItem extends Item {

    private final Agent agent;

    public static final String TOOLTIP_SHOW_SPELL_TRANSLATION_KEY = Util.makeDescriptionId("item", BRAVE.id("become_agent.show_spell"));
    public static final String TOOLTIP_SPELLS_TRANSLATION_KEY = Util.makeDescriptionId("item", BRAVE.id("become_agent.spells"));
    public static final String TOOLTIP_AGENT_TRANSLATION_KEY = Util.makeDescriptionId("item", BRAVE.id("become_agent.agent"));
    public static final String TOOLTIP_SHOW_DESCRIPTION_TRANSLATION_KEY = Util.makeDescriptionId("item", BRAVE.id("become_agent.show_desc"));

    public BecomeAgentItem(Agent agent, Properties settings) {
        super(settings);
        this.agent = agent;
    }

    public Agent getAgent() {
        return agent;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if(!level.isClientSide()) {
            BComponents.PLAYER_DATA.get(player).setAgent(this.agent, true);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
        MutableText agentText = Text.translatable(TOOLTIP_AGENT_TRANSLATION_KEY).formatted(Formatting.GRAY);
        agentText.append(Text.literal(this.agent.getDisplayName()).formatted(Formatting.GOLD));

        textConsumer.accept(agentText);

        if(this.agent == Agents.DEFAULT) return;

        if(MinecraftClient.getInstance().isShiftPressed()) {
            MutableText spellText = Text.translatable(TOOLTIP_SPELLS_TRANSLATION_KEY).formatted(Formatting.GRAY);
            textConsumer.accept(spellText);

            textConsumer.accept(Text.literal("-").formatted(Formatting.GRAY).append(Text.translatable(this.agent.getFirstSpell().getTranslationKey()).formatted(Formatting.AQUA)));
            textConsumer.accept(Text.literal("-").formatted(Formatting.GRAY).append(Text.translatable(this.agent.getSecondSpell().getTranslationKey()).formatted(Formatting.AQUA)));
            textConsumer.accept(Text.literal("-").formatted(Formatting.GRAY).append(Text.translatable(this.agent.getThirdSpell().getTranslationKey()).formatted(Formatting.AQUA)));

            textConsumer.accept(Text.literal("-").formatted(Formatting.GRAY).append(Text.translatable(this.agent.getUltimate().getTranslationKey()).formatted(Formatting.BLUE)));
        }
        else {
            textConsumer.accept(Text.translatable(TOOLTIP_SHOW_SPELL_TRANSLATION_KEY).formatted(Formatting.GRAY, Formatting.ITALIC));
        }

    }
}
