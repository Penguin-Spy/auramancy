package dev.penguinspy.auramancy.items;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.Settings;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class AuramancyItems {
	public static final Item WOODEN_WAND_ITEM = new WandItem(new QuiltItemSettings()
			.maxCount(1)
			.rarity(Rarity.UNCOMMON)
			.recipeSelfRemainder());

	public static final Block ENCHANTED_STONE = new Block(QuiltBlockSettings.create()
			.drops(new Identifier("auramancy:blocks/enchanted_stone"))
			.strength(1.5f, 9));

	public static void register(ModContainer mod) {
		String mod_id = mod.metadata().id();

		// register items and block-items with their ID
		Item[] items = {
				item(new Identifier(mod_id, "wooden_wand"), WOODEN_WAND_ITEM),
				blockItem(new Identifier(mod_id, "enchanted_stone"), ENCHANTED_STONE, new QuiltItemSettings())
		};

		// and add them to the creative menu tab
		ItemGroup group = FabricItemGroup.builder()
				.icon(() -> new ItemStack(WOODEN_WAND_ITEM))
				.name(Text.translatable("itemGroup.auramancy"))
				.entries((context, entries) -> {
					for (Item item : items) {
						entries.addItem(item);
					}
				}).build();
		Registry.register(Registries.ITEM_GROUP, new Identifier(mod_id, "item_group"), group);
	}

	private static Item item(Identifier id, Item item) {
		Registry.register(Registries.ITEM, id, item);
		return item;
	}

	private static Item blockItem(Identifier id, Block block, Settings itemSettings) {
		BlockItem blockItem = new BlockItem(block, itemSettings);
		Registry.register(Registries.ITEM, id, blockItem);
		Registry.register(Registries.BLOCK, id, block);
		return blockItem;
	}
}
