package fr.kayrouge.brave.client.render;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;

import java.util.HashMap;
import java.util.Map;

public class BRenderers {

    public static final BRenderers INSTANCE = new BRenderers();

    private final Map<IBRender, Long> renderTasks = new HashMap<>();

    public synchronized void addRenderTask(IBRender render, long time) {
        renderTasks.put(render, System.currentTimeMillis()+time);
    }

    public synchronized Map<IBRender, Long> getRenderTasks() {
        return renderTasks;
    }

    @FunctionalInterface
    public interface IBRender {
        Runnable render(RenderContext renderContext, long endTimeMillis);
    }

    public record RenderContext(MatrixStack matrices, VertexConsumerProvider vertexConsumers) {}

}
