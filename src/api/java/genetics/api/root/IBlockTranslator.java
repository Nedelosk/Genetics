package genetics.api.root;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

import genetics.api.individual.IIndividual;

/**
 * Translates blockStates into genetic data.
 * Used by bees and butterflies to convert and pollinate foreign leaf blocks.
 */
public interface IBlockTranslator<I extends IIndividual> {
	@Nullable
	I getIndividualFromObject(IBlockState blockState);

	ItemStack getGeneticEquivalent(IBlockState blockState);
}
