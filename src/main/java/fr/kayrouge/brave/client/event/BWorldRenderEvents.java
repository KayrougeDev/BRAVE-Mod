package fr.kayrouge.brave.client.event;

import fr.kayrouge.brave.client.render.BRenderers;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;

import java.util.Iterator;
import java.util.Map;

public class BWorldRenderEvents {

    public static void register() {

        WorldRenderEvents.END_MAIN.register((worldRenderContext) -> {
            MinecraftClient client = worldRenderContext.gameRenderer().getClient();
            client.execute(() -> {
                Iterator<Map.Entry<BRenderers.IBRender, Long>> iterator = BRenderers.INSTANCE.getRenderTasks().entrySet().iterator();

                while (iterator.hasNext()) {
                    Map.Entry<BRenderers.IBRender, Long> task = iterator.next();

                    Runnable runnable = task.getKey().render(new BRenderers.RenderContext(worldRenderContext.matrices(), worldRenderContext.consumers()), task.getValue());

                    if (task.getValue() < System.currentTimeMillis()) {
                        iterator.remove();
                        runnable.run();
                    }
                }
            });
        });
    }
}
