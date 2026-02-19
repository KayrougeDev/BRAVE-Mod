package fr.kayrouge.brave.mixin;

import fr.kayrouge.brave.agents.Agent;
import fr.kayrouge.brave.agents.Agents;
import fr.kayrouge.brave.component.BComponents;
import fr.kayrouge.brave.items.BItems;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {

	@Unique
	private static final int minExpositionTick = 20*16;

	@Inject(at = @At("HEAD"), method = "tick")
	private void tick(CallbackInfo info) {
		ServerPlayerEntity INSTANCE = ((ServerPlayerEntity)(Object)this);
		if(BComponents.PLAYER_DATA.get(INSTANCE).getAgent() != Agents.DEFAULT) return;

		int total = 0;
		for(ItemStack stack : INSTANCE.getInventory().getMainStacks()) {
			if(stack.getItem() == BItems.RADIANITE) {
				total += stack.getCount();
			}

		}

		int exposedTime = BComponents.PLAYER_DATA.get(INSTANCE).getExposedTime();

		if(exposedTime >= minExpositionTick) {
			// TODO transform into random agent or display a screen
			Agent agent = Agents.OMEN;
			BComponents.PLAYER_DATA.get(INSTANCE).setAgent(agent);
			BComponents.PLAYER_DATA.get(INSTANCE).setExposedTime(-1);
			INSTANCE.removeStatusEffect(StatusEffects.NAUSEA);
			INSTANCE.removeStatusEffect(StatusEffects.POISON);
			BComponents.PLAYER_DATA.sync(INSTANCE, (buf, player) -> BComponents.PLAYER_DATA.get(INSTANCE).writeSyncPacket(buf, player, true));
			return;
		}

		if(total > 5) {
			INSTANCE.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 20*7, 1, false, false));
		}
		if(total > 20) {
			INSTANCE.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 25, 2, false, false));
			BComponents.PLAYER_DATA.get(INSTANCE).incrementExposedTime();
			BComponents.PLAYER_DATA.sync(INSTANCE);
		}
		else if(exposedTime != 0) {
			BComponents.PLAYER_DATA.get(INSTANCE).setExposedTime(0);
			BComponents.PLAYER_DATA.sync(INSTANCE);
		}
	}
}