package fr.kayrouge.brave.mixin;

import fr.kayrouge.brave.items.BItems;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {

	@Inject(at = @At("HEAD"), method = "tick")
	private void init(CallbackInfo info) {
		ServerPlayerEntity INSTANCE = ((ServerPlayerEntity)(Object)this);

		int i = INSTANCE.getInventory().getSlotWithStack(new ItemStack(BItems.RADIANITE));
		if(i < 0 || i > 36) return;
		INSTANCE.sendMessage(Text.literal("RADIANITE: "+INSTANCE.getInventory().getStack(i).getCount()));
	}
}