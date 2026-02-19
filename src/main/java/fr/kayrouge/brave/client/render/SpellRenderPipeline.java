package fr.kayrouge.brave.client.render;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.textures.TextureFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import fr.kayrouge.brave.BRAVE;
import lombok.Getter;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.MappableRingBuffer;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BuiltBuffer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.BufferAllocator;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.system.MemoryUtil;

import java.util.OptionalDouble;
import java.util.OptionalInt;

public class SpellRenderPipeline {
    @Getter
    private final static SpellRenderPipeline instance = new SpellRenderPipeline();

    private static final BufferAllocator allocator = new BufferAllocator(1536);
    private BufferBuilder buffer;

    private static final Vector4f COLOR_MODULATOR = new Vector4f(1f, 1f, 1f, 1f);
    private static final Vector3f MODEL_OFFSET = new Vector3f();
    private static final Matrix4f TEXTURE_MATRIX = new Matrix4f();
    private MappableRingBuffer vertexBuffer;

    public void renderTarget(WorldRenderContext context) {
        MatrixStack matrices = context.matrices();
        Vec3d camera = context.worldState().cameraRenderState.pos;

        matrices.push();
        matrices.translate(-camera.x, -camera.y, -camera.z);

        if (buffer == null) {
            buffer = new BufferBuilder(allocator, RenderPipelines.DEBUG_FILLED_BOX.getVertexFormatMode(), RenderPipelines.DEBUG_FILLED_BOX.getVertexFormat());
        }

        renderFilledBox(matrices.peek().copy(), buffer, 0f, 100f, 0f, 1f, 101f, 1f, 0f, 1f, 0f, 0.5f);

        matrices.pop();
    }

    public void renderFilledBox(MatrixStack.Entry positionMatrix, VertexConsumer buffer, float minX, float minY, float minZ, float maxX, float maxY, float maxZ, float red, float green, float blue, float alpha) {
        // Front Face
        buffer.vertex(positionMatrix, minX, minY, maxZ).color(red, green, blue, alpha);
        buffer.vertex(positionMatrix, maxX, minY, maxZ).color(red, green, blue, alpha);
        buffer.vertex(positionMatrix, maxX, maxY, maxZ).color(red, green, blue, alpha);
        buffer.vertex(positionMatrix, minX, maxY, maxZ).color(red, green, blue, alpha);

        // Back face
        buffer.vertex(positionMatrix, maxX, minY, minZ).color(red, green, blue, alpha);
        buffer.vertex(positionMatrix, minX, minY, minZ).color(red, green, blue, alpha);
        buffer.vertex(positionMatrix, minX, maxY, minZ).color(red, green, blue, alpha);
        buffer.vertex(positionMatrix, maxX, maxY, minZ).color(red, green, blue, alpha);

        // Left face
        buffer.vertex(positionMatrix, minX, minY, minZ).color(red, green, blue, alpha);
        buffer.vertex(positionMatrix, minX, minY, maxZ).color(red, green, blue, alpha);
        buffer.vertex(positionMatrix, minX, maxY, maxZ).color(red, green, blue, alpha);
        buffer.vertex(positionMatrix, minX, maxY, minZ).color(red, green, blue, alpha);

        // Right face
        buffer.vertex(positionMatrix, maxX, minY, maxZ).color(red, green, blue, alpha);
        buffer.vertex(positionMatrix, maxX, minY, minZ).color(red, green, blue, alpha);
        buffer.vertex(positionMatrix, maxX, maxY, minZ).color(red, green, blue, alpha);
        buffer.vertex(positionMatrix, maxX, maxY, maxZ).color(red, green, blue, alpha);

        // Top face
        buffer.vertex(positionMatrix, minX, maxY, maxZ).color(red, green, blue, alpha);
        buffer.vertex(positionMatrix, maxX, maxY, maxZ).color(red, green, blue, alpha);
        buffer.vertex(positionMatrix, maxX, maxY, minZ).color(red, green, blue, alpha);
        buffer.vertex(positionMatrix, minX, maxY, minZ).color(red, green, blue, alpha);

        // Bottom face
        buffer.vertex(positionMatrix, minX, minY, minZ).color(red, green, blue, alpha);
        buffer.vertex(positionMatrix, maxX, minY, minZ).color(red, green, blue, alpha);
        buffer.vertex(positionMatrix, maxX, minY, maxZ).color(red, green, blue, alpha);
        buffer.vertex(positionMatrix, minX, minY, maxZ).color(red, green, blue, alpha);
    }

    public void finish(MinecraftClient client, @SuppressWarnings("SameParameterValue") RenderPipeline pipeline) {
        // Build the buffer
        BuiltBuffer builtBuffer = buffer.end();
        BuiltBuffer.DrawParameters drawParameters = builtBuffer.getDrawParameters();
        VertexFormat format = drawParameters.format();

        GpuBuffer vertices = upload(drawParameters, format, builtBuffer);

        draw(client, pipeline, builtBuffer, drawParameters, vertices, format);

        // Rotate the vertex buffer so we are less likely to use buffers that the GPU is using
        vertexBuffer.rotate();
        buffer = null;
    }

    private GpuBuffer upload(BuiltBuffer.DrawParameters drawParameters, VertexFormat format, BuiltBuffer builtBuffer) {
        // Calculate the size needed for the vertex buffer
        int vertexBufferSize = drawParameters.vertexCount() * format.getVertexSize();

        // Initialize or resize the vertex buffer as needed
        if (vertexBuffer == null || vertexBuffer.size() < vertexBufferSize) {
            if (vertexBuffer != null) {
                vertexBuffer.close();
            }

            vertexBuffer = new MappableRingBuffer(() -> BRAVE.MOD_ID + " example render pipeline", GpuBuffer.USAGE_VERTEX | GpuBuffer.USAGE_MAP_WRITE, vertexBufferSize);
        }

        // Copy vertex data into the vertex buffer
        CommandEncoder commandEncoder = RenderSystem.getDevice().createCommandEncoder();

        try (GpuBuffer.MappedView mappedView = commandEncoder.mapBuffer(vertexBuffer.getBlocking().slice(0, builtBuffer.getBuffer().remaining()), false, true)) {
            MemoryUtil.memCopy(builtBuffer.getBuffer(), mappedView.data());
        }

        return vertexBuffer.getBlocking();
    }

    private static void draw(MinecraftClient client, RenderPipeline pipeline, BuiltBuffer builtBuffer, BuiltBuffer.DrawParameters drawParameters, GpuBuffer vertices, VertexFormat format) {
        GpuBuffer indices;
        VertexFormat.IndexType indexType;

        if (pipeline.getVertexFormatMode() == VertexFormat.DrawMode.QUADS) {
            // Sort the quads if there is translucency
            builtBuffer.sortQuads(allocator, RenderSystem.getProjectionType().getVertexSorter());
            // Upload the index buffer
            indices = pipeline.getVertexFormat().uploadImmediateIndexBuffer(builtBuffer.getBuffer());
            indexType = builtBuffer.getDrawParameters().indexType();
        } else {
            // Use the general shape index buffer for non-quad draw modes
            RenderSystem.ShapeIndexBuffer shapeIndexBuffer = RenderSystem.getSequentialBuffer(pipeline.getVertexFormatMode());
            indices = shapeIndexBuffer.getIndexBuffer(drawParameters.indexCount());
            indexType = shapeIndexBuffer.getIndexType();
        }

        // Actually execute the draw
        GpuBufferSlice dynamicTransforms = RenderSystem.getDynamicUniforms()
                .write(RenderSystem.getModelViewMatrix(), COLOR_MODULATOR, MODEL_OFFSET, TEXTURE_MATRIX);

        GpuTextureView textureView = RenderSystem.getDevice().createTextureView(RenderSystem.getDevice().createTexture("filedcube", 0, TextureFormat.RGBA8, 1, 1, 1, 1));
        GpuTextureView depthView = RenderSystem.getDevice().createTextureView(RenderSystem.getDevice().createTexture("filedcubedepth", 0, TextureFormat.DEPTH32, 1, 1, 1, 1));



        try (RenderPass renderPass = RenderSystem.getDevice()
                .createCommandEncoder()
                .createRenderPass(() -> BRAVE.MOD_ID + " example render pipeline rendering", textureView, OptionalInt.empty(), depthView, OptionalDouble.empty())) {
            renderPass.setPipeline(pipeline);

            RenderSystem.bindDefaultUniforms(renderPass);
            renderPass.setUniform("DynamicTransforms", dynamicTransforms);

            // Bind texture if applicable:
            // Sampler0 is used for texture inputs in vertices
            // renderPass.bindTexture("Sampler0", textureSetup.texure0(), textureSetup.sampler0());

            renderPass.setVertexBuffer(0, vertices);
            renderPass.setIndexBuffer(indices, indexType);

            // The base vertex is the starting index when we copied the data into the vertex buffer divided by vertex size
            //noinspection ConstantValue
            renderPass.drawIndexed(0 / format.getVertexSize(), 0, drawParameters.indexCount(), 1);
        }

        builtBuffer.close();
    }

    public void close() {
        allocator.close();

        if (vertexBuffer != null) {
            vertexBuffer.close();
            vertexBuffer = null;
        }
    }

}
