package nedelosk.genetics.api.translators;

import java.util.Optional;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import nedelosk.genetics.api.individual.IIndividual;

public interface IGeneticTranslator<I extends IIndividual> {
	/**
	 * @param translatorKey The key of the translator, by default it is the item of the {@link ItemStack} that you want
	 *                      to translate with the translator.
	 */
	Optional<IItemTranslator<I>> getTranslator(Item translatorKey);

	/**
	 * @param translatorKey The key of the translator the block of the{@link IBlockState} that you want to translate
	 *                      with the translator.
	 */
	Optional<IBlockTranslator<I>> getTranslator(Block translatorKey);

	/**
	 * Translates {@link IBlockState}s into genetic data.
	 */
	Optional<I> translateMember(IBlockState objectToTranslate);

	/**
	 * Translates {@link ItemStack}s into genetic data.
	 */
	Optional<I> translateMember(ItemStack objectToTranslate);

	/**
	 * Translates a {@link IBlockState}s into genetic data and returns a {@link ItemStack} that contains this data.
	 */
	ItemStack getGeneticEquivalent(IBlockState objectToTranslate);

	/**
	 * Translates {@link ItemStack}s into genetic data and returns a other {@link ItemStack} that contains this data.
	 */
	ItemStack getGeneticEquivalent(ItemStack objectToTranslate);
}
