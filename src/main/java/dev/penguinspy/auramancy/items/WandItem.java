package dev.penguinspy.auramancy.items;

import dev.penguinspy.auramancy.Auramancy;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WandItem extends Item {
	public WandItem(Settings settings) {
		super(settings);
	}

	// determines if the player can start using the item
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		Auramancy.LOGGER.info("used wand");
		player.setCurrentHand(hand);
		ItemStack stack = player.getStackInHand(hand);
		return TypedActionResult.consume(stack);
		// or TypedActionResult.fail(stack) if the player can't use the wand
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		Auramancy.LOGGER.info("used wand on block");
		World world = context.getWorld();
		BlockPos pos = context.getBlockPos();
		BlockState state = world.getBlockState(pos);
		if (state.isOf(Blocks.STONE)) {
			world.setBlockState(pos, AuramancyItems.ENCHANTED_STONE.getDefaultState());
			context.getPlayer().playSound(SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.PLAYERS, 1f, 1f);
			return ActionResult.SUCCESS;
		}
		return ActionResult.PASS;
	}

	@Override
	public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
		int useTicks = this.getMaxUseTime(stack) - remainingUseTicks;
		if (useTicks > 20) {
			if (user instanceof PlayerEntity player) {
				player.playSound(SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.PLAYERS, 1f, 1f);
				player.incrementStat(Stats.USED.getOrCreateStat(this));
			}
			Auramancy.LOGGER.info("used wand long enough!");
		}
	}

	@Override
	public int getMaxUseTime(ItemStack stack) {
		return 72000; // an hour
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.BOW;
	}
}
