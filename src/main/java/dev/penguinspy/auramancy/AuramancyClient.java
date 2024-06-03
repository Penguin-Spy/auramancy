package dev.penguinspy.auramancy;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

import dev.penguinspy.auramancy.blocks.NodeBlockEntityRenderer;
import dev.penguinspy.auramancy.items.AuramancyItems;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

@ClientOnly
public class AuramancyClient implements ClientModInitializer {

	@Override
	public void onInitializeClient(ModContainer mod) {
		BlockEntityRendererFactories.register(AuramancyItems.NODE_BLOCK_ENTITY, NodeBlockEntityRenderer::new);
	}
}
