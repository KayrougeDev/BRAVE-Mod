package fr.kayrouge.brave.datagen;

import fr.kayrouge.brave.datagen.provider.BRAVEAdvancementProvider;
import fr.kayrouge.brave.datagen.provider.BRAVEModelProvider;
import fr.kayrouge.brave.datagen.provider.lang.BRAVEEnglishLangProvider;
import fr.kayrouge.brave.datagen.provider.lang.BRAVEFrenchLangProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import org.jspecify.annotations.NonNull;

public class BRAVEDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(@NonNull FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(BRAVEAdvancementProvider::new);

		pack.addProvider(BRAVEModelProvider::new);

		// Translation
		pack.addProvider(BRAVEEnglishLangProvider::new);
		pack.addProvider(BRAVEFrenchLangProvider::new);
	}
}
