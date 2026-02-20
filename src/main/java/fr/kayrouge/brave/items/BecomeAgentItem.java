package fr.kayrouge.brave.items;

import fr.kayrouge.brave.BRAVE;
import fr.kayrouge.brave.agents.Agent;
import fr.kayrouge.brave.agents.Agents;
import fr.kayrouge.brave.component.BComponents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.world.World;

import java.util.function.Consumer;

public class BecomeAgentItem extends Item {

    private final Agent agent;

    public static final String TOOLTIP_SHOW_SPELL_TRANSLATION_KEY = Util.createTranslationKey("item", BRAVE.id("become_agent.show_spell"));
    public static final String TOOLTIP_SPELLS_TRANSLATION_KEY = Util.createTranslationKey("item", BRAVE.id("become_agent.spells"));
    public static final String TOOLTIP_AGENT_TRANSLATION_KEY = Util.createTranslationKey("item", BRAVE.id("become_agent.agent"));
    public static final String TOOLTIP_SHOW_DESCRIPTION_TRANSLATION_KEY = Util.createTranslationKey("item", BRAVE.id("become_agent.show_desc"));

    public BecomeAgentItem(Agent agent, Settings settings) {
        super(settings);
        this.agent = agent;
    }

    public Agent getAgent() {
        return agent;
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient()) {
            BComponents.PLAYER_DATA.get(user).setAgent(this.agent, true);
        }
        return ActionResult.CONSUME;
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
