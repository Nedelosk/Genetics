package nedelosk.crispr.api.translators;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

import nedelosk.crispr.api.individual.IIndividual;

public interface IBlockTranslator<I extends IIndividual> {
	@Nullable
	I getIndividualFromObject(IBlockState blockState);

	ItemStack getGeneticEquivalent(IBlockState blockState);
}
