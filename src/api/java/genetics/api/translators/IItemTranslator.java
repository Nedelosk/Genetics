package genetics.api.translators;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;

import genetics.api.individual.IIndividual;

public interface IItemTranslator<I extends IIndividual> {
	@Nullable
	I getIndividualFromObject(ItemStack itemStack);

	default ItemStack getGeneticEquivalent(ItemStack itemStack) {
		return ItemStack.EMPTY;
	}
}
