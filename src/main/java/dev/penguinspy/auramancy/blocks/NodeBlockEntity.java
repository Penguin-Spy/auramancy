package dev.penguinspy.auramancy.blocks;

import dev.penguinspy.auramancy.items.AuramancyItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class NodeBlockEntity extends BlockEntity {
	public NodeBlockEntity(BlockPos pos, BlockState state) {
		super(AuramancyItems.NODE_BLOCK_ENTITY, pos, state);
	}
}
