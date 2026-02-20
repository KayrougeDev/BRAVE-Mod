package fr.kayrouge.brave.client.render.screen;

import fr.kayrouge.brave.BRAVE;
import fr.kayrouge.brave.agents.Agent;
import fr.kayrouge.brave.agents.spell.Spell;
import fr.kayrouge.brave.client.BKeyBindings;
import fr.kayrouge.brave.component.BComponents;
import fr.kayrouge.brave.items.BecomeAgentItem;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;
import org.joml.Matrix3x2fStack;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public class BInGameHud {

    public static void register() {

        MinecraftClient client = MinecraftClient.getInstance();

        AtomicReference<Float> animProgress = new AtomicReference<>(0f);
        AtomicLong startTime = new AtomicLong(-1);

        final int animTimeMS = 200;

        HudElementRegistry.attachElementAfter(VanillaHudElements.CHAT, BRAVE.id("agent_info"), (context, tickCounter) -> {

            if(BKeyBindings.DISPLAY_AGENT_INFO.isPressed()) {
                if (animProgress.get() == 0f) {
                    startTime.set(Util.getMeasuringTimeMs());
                    animProgress.set(0.1f);
                }
                else {
                    long diff = Util.getMeasuringTimeMs()-startTime.get();

                    if(diff >= animTimeMS) {
                        animProgress.set(1f);
                    }
                    else {
                        animProgress.set((float)diff/animTimeMS);
                    }

                }
            }
            else {
                startTime.set(-1);
                animProgress.set(0f);
            }

            if (animProgress.get() == 0f) return;

            Agent a = BComponents.PLAYER_DATA.get(client.player).getAgent();

            Matrix3x2fStack stack = context.getMatrices();
            int x = context.getScaledWindowWidth();
            int y = context.getScaledWindowHeight();

            int infoStartX = 0;
            int infoStartY = y/3;

            int zoneX = infoStartX+5;

            int gap = 5;
            int totalGaps = 5 * gap;

            float rectWidth = (float) (x - totalGaps) / 4;

            stack.pushMatrix();

            stack.translate(0, (float) y / 1.25f *(1-animProgress.get()));


            context.fill(infoStartX, infoStartY, x, y, 0x99222222);
            context.fill(infoStartX, infoStartY, x, infoStartY + 1, 0xDDDDDDDD);


            displaySpellInfo(context, a.getFirstSpell(), zoneX, infoStartY+5, y/2-10, rectWidth, 0);

            displaySpellInfo(context, a.getSecondSpell(), zoneX, infoStartY+5, y/2-10, rectWidth, 1);

            displaySpellInfo(context, a.getThirdSpell(), zoneX, infoStartY+5, y/2-10, rectWidth, 2);

            displaySpellInfo(context, a.getUltimate(), zoneX, infoStartY+5, y/2-10, rectWidth, 3);

            stack.popMatrix();



        });

        HudElementRegistry.addFirst(BRAVE.id("equipped_spell"), (context, tickCounter) -> {
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

    private static void displaySpellInfo(DrawContext context, Spell spell, int zoneX, int infoStartY, int ySize, float rectWidth, int repetition) {
        context.getMatrices().pushMatrix();

        float addition = (rectWidth + 5)*repetition;

        context.getMatrices().translate(zoneX + addition, infoStartY);

        //context.fill(0, 0, (int) rectWidth, ySize, -1);

        MutableText spellTitle = Text.translatable(spell.getTranslationKey()).formatted(Formatting.BOLD);

        if (repetition == 3) {
            spellTitle.formatted(Formatting.GOLD);
        }

        context.drawText(MinecraftClient.getInstance().textRenderer,
                spellTitle,
                0, 0, -1, false
                );

        MutableText spellDesc = Text.translatable(spell.getDescriptionTranslationKey());

        context.drawWrappedText(MinecraftClient.getInstance().textRenderer,
                spellDesc,
                0, MinecraftClient.getInstance().textRenderer.fontHeight+3, (int)rectWidth, -1, false
        );

        context.getMatrices().popMatrix();
    }


}
