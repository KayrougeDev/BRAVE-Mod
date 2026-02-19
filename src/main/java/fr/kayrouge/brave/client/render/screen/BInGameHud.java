package fr.kayrouge.brave.client.render.screen;

import fr.kayrouge.brave.BRAVE;
import fr.kayrouge.brave.agents.Agent;
import fr.kayrouge.brave.agents.spell.Spell;
import fr.kayrouge.brave.component.BComponents;
import fr.kayrouge.brave.items.BecomeAgentItem;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.awt.*;

public class BInGameHud {

    public static void register() {
        HudElementRegistry.addFirst(BRAVE.id("equipped_spell"), (context, tickCounter) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            Spell s = BComponents.PLAYER_DATA.get(client.player).getEquippedSpell();
            Agent a = BComponents.PLAYER_DATA.get(client.player).getAgent();
            MutableText text = Text.literal("Equipped spell: ");
            text.append(Text.translatable(s.getTranslationKey()));

            float windowY = (float) context.getScaledWindowHeight();

            context.getMatrices().pushMatrix();

            context.getMatrices().translate((float) 2,  windowY-2-client.textRenderer.fontHeight*0.75f);

            context.getMatrices().scale(0.75f);

            context.drawTextWithShadow(client.textRenderer, text, 0, 0, -1);

            context.drawTextWithShadow(client.textRenderer,
                    Text.translatable(BecomeAgentItem.TOOLTIP_AGENT_TRANSLATION_KEY).append(a.getDisplayName()),
                    0, -(int)(client.textRenderer.fontHeight*0.75f)-5, -1);

            context.getMatrices().popMatrix();
        });
    }

}
