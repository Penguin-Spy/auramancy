package dev.penguinspy.auramancy.blocks;

import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class NodeBlock extends Block implements BlockEntityProvider {
	public NodeBlock() {
		// strength makes it unbreakable, noCollision removes collision and causing ambient occlusion, solid prevents replacing it with water/lava
		super(QuiltBlockSettings.create().strength(-1.0f, 3600000.0f).dropsNothing().noCollision().solid().pistonBehavior(PistonBehavior.BLOCK).luminance(15));
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new NodeBlockEntity(pos, state);
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.INVISIBLE;
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return VoxelShapes.empty();
	}

}
