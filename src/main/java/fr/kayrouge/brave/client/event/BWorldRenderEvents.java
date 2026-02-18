package fr.kayrouge.brave.client.event;

import com.mojang.blaze3d.systems.RenderSystem;
import fr.kayrouge.brave.agents.spell.SpellDataManager;
import fr.kayrouge.brave.client.render.SpellRenderPipeline;
import fr.kayrouge.brave.client.render.BRenderers;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.util.math.Vec3d;

import java.util.Iterator;
import java.util.Map;

public class BWorldRenderEvents {

    public static void register() {

        WorldRenderEvents.END_MAIN.register(worldRenderContext -> {

            worldRenderContext.matrices().push();

            Camera camera = MinecraftClient.getInstance().gameRenderer.getCamera();

            final double cameraX = camera.getCameraPos().x;
            final double cameraY = camera.getCameraPos().y;
            final double cameraZ = camera.getCameraPos().z;

            final Vec3d tpVec = SpellDataManager.getInstance().getTpVec();

            final Vec3d targetPos = tpVec.subtract(camera.getCameraPos());

            final float adjustedStartX = (float) targetPos.x;
            final float adjustedStartY = (float) targetPos.y;
            final float adjustedStartZ = (float) targetPos.z;

            VertexConsumer consumer = worldRenderContext.consumers().getBuffer(RenderLayers.debugFilledBox());


            SpellRenderPipeline.getInstance().renderFilledBox(worldRenderContext.matrices().peek().copy(),
                    consumer, adjustedStartX-0.25f, adjustedStartY, adjustedStartZ-0.25f,
                    adjustedStartX+0.25f, adjustedStartY+0.5f, adjustedStartZ+0.25f, 0f, 1f, 0f, 0.7f);

            worldRenderContext.matrices().pop();

            //SpellRenderPipeline.getInstance().renderTarget(worldRenderContext);
            //SpellRenderPipeline.getInstance().finish(MinecraftClient.getInstance(), RenderPipelines.DEBUG_FILLED_BOX);


        });

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
