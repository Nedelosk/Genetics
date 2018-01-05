package genetics.api;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;

import genetics.api.individual.IOrganism;

/**
 * Translates items into genetic data. Used by the treealyzer and the farm to convert foreign saplings.
 */
public interface IItemTranslator<I extends IOrganism> {
	@Nullable
	I getIndividualFromObject(ItemStack itemStack);

	default ItemStack getGeneticEquivalent(ItemStack itemStack) {
		return ItemStack.EMPTY;
	}
}
