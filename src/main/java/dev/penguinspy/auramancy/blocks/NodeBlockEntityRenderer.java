package dev.penguinspy.auramancy.blocks;

import org.quiltmc.loader.api.minecraft.ClientOnly;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockModelRenderer;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.World;

@ClientOnly
public class NodeBlockEntityRenderer implements BlockEntityRenderer<NodeBlockEntity> {
	private final MinecraftClient client;
	private final BlockRenderManager blockRenderManager;
	private final BlockModelRenderer blockModelRenderer;

	// needed by the block renderer, unknown use
	private static final RandomGenerator random = RandomGenerator.createLegacy();

	private static final RenderLayer renderLayer = RenderLayer.getCutout();

	public NodeBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
		this.client = MinecraftClient.getInstance();
		this.blockRenderManager = this.client.getBlockRenderManager();
		this.blockModelRenderer = this.blockRenderManager.getModelRenderer();
	}

	@Override
	public void render(NodeBlockEntity nodeBlockEntity, float tickDelta, MatrixStack matrices,
			VertexConsumerProvider vertexConsumers, int light, int overlay) {
		matrices.push();

		World world = nodeBlockEntity.getWorld();
		BlockState state = nodeBlockEntity.getCachedState();
		BlockPos nodePos = nodeBlockEntity.getPos();

		// offset the time based on the block's position so adjacent nodes move differently
		long renderingSeed = state.getRenderingSeed(nodePos);
		random.setSeed(renderingSeed);
		float time = world.getTime() + tickDelta + (random.nextFloat() * 80.0f);

		// slowly drift back and forth
		double offsetX = Math.sin(time / 16.0) / 10.0;
		double offsetY = Math.sin(time / 12.0) / 6.0;
		double offsetZ = Math.sin(time / 18.0) / 10.0;
		matrices.translate(offsetX + 0.5, offsetY + 0.5, offsetZ + 0.5);

		// face the direction the gamera is facing (like particles), not facing the player within the world (this is intentional)
		matrices.multiply(client.gameRenderer.getCamera().getRotation());

		// offset to the center of the block
		matrices.translate(-0.5, -0.5, -0.5);

		blockModelRenderer.render(world, blockRenderManager.getModel(state), state, nodePos, matrices,
				vertexConsumers.getBuffer(renderLayer), false, random, renderingSeed,
				OverlayTexture.DEFAULT_UV);

		matrices.pop();
	}
}
