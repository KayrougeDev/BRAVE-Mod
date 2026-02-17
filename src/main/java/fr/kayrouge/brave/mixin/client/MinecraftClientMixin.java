package fr.kayrouge.brave.mixin.client;

import fr.kayrouge.brave.BRAVE;
import fr.kayrouge.brave.agents.Agents;
import fr.kayrouge.brave.agents.spell.SpellDataManager;
import fr.kayrouge.brave.agents.spell.Spells;
import fr.kayrouge.brave.component.BComponents;
import fr.kayrouge.brave.network.c2s.EquippedSpellUseC2SPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Redirect(method = "handleInputEvents",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/KeyBinding;wasPressed()Z"))
    private boolean interceptKeyBind(KeyBinding keyBinding) {
        MinecraftClient client = (MinecraftClient) (Object) this;
        boolean wasPressed =  keyBinding.wasPressed();

        if (keyBinding == client.options.attackKey && wasPressed) {
            if (BComponents.PLAYER_DATA.get(client.player).getEquippedSpell() == Spells.OMEN_TP) {
                NbtCompound data = new NbtCompound();
                Vec3d tpVec = SpellDataManager.getInstance().getTpVec();
                data.putDouble("tpX", tpVec.x);
                data.putDouble("tpY", tpVec.y);
                data.putDouble("tpZ", tpVec.z);

                ClientPlayNetworking.send(new EquippedSpellUseC2SPayload(Spells.OMEN_TP, data, false));
                return false;
            }
        }

        if (keyBinding == client.options.useKey && wasPressed) {
            if (BComponents.PLAYER_DATA.get(client.player).getEquippedSpell() == Spells.OMEN_TP) {
                ClientPlayNetworking.send(new EquippedSpellUseC2SPayload(Spells.OMEN_TP, new NbtCompound(), true));
                return false;
            }
        }

        return wasPressed;
    }

}
