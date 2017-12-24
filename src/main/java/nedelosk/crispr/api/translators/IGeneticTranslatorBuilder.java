package nedelosk.crispr.api.translators;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import nedelosk.crispr.api.individual.IIndividual;

public interface IGeneticTranslatorBuilder<I extends IIndividual> {
	/**
	 * @param translatorKey The key of the translator the block of {@link IBlockState} that you want to translate
	 *                         with the translator.
	 * @param translator A translator that should be used to translate the data.
	 */
	void registerTranslator(Block translatorKey, IBlockTranslator<I> translator);

	/**
	 * @param translatorKey The key of the translator it is the item of the {@link ItemStack} that you want to translate
	 *                      with the translator.
	 * @param translator A translator that should be used to translate the data.
	 */
	void registerTranslator(Item translatorKey, IItemTranslator<I> translator);

	IGeneticTranslator<I> build();

}
