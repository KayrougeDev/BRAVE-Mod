package fr.kayrouge.brave.datagen.provider;

import fr.kayrouge.brave.BRegistries;
import fr.kayrouge.brave.items.BItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Models;
import org.jspecify.annotations.NonNull;

public class BRAVEModelProvider extends FabricModelProvider {

    public BRAVEModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(@NonNull BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(@NonNull ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(BItems.ICON, Models.GENERATED);
        itemModelGenerator.register(BItems.ICON_AGENT, Models.GENERATED);
        itemModelGenerator.register(BItems.RADIANITE, Models.GENERATED);

        BRegistries.AGENTS.stream().forEach(agent -> itemModelGenerator.register(agent.getItem(), Models.GENERATED));
    }
}
