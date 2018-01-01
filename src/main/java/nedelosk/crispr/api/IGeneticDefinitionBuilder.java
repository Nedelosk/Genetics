package nedelosk.crispr.api;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import nedelosk.crispr.api.gene.IGeneticStat;
import nedelosk.crispr.api.individual.IGeneticHandler;
import nedelosk.crispr.api.individual.IGeneticType;
import nedelosk.crispr.api.individual.IGenome;
import nedelosk.crispr.api.individual.IIndividual;
import nedelosk.crispr.api.translators.IBlockTranslator;
import nedelosk.crispr.api.translators.IGeneticTranslator;
import nedelosk.crispr.api.translators.IItemTranslator;
import nedelosk.crispr.apiimp.IGeneticTypes;

public interface IGeneticDefinitionBuilder<I extends IIndividual, R extends IGeneticRoot<I, ?>> {

	IGeneticDefinitionBuilder<I, R> registerType(IGeneticType type, IGeneticHandler<I> handler);

	/**
	 * @param translatorKey The key of the translator the block of {@link IBlockState} that you want to translate
	 *                      with the translator.
	 * @param translator    A translator that should be used to translate the data.
	 */
	IGeneticDefinitionBuilder<I, R> registerTranslator(Block translatorKey, IBlockTranslator<I> translator);

	/**
	 * @param translatorKey The key of the translator it is the item of the {@link ItemStack} that you want to translate
	 *                      with the translator.
	 * @param translator    A translator that should be used to translate the data.
	 */
	IGeneticDefinitionBuilder<I, R> registerTranslator(Item translatorKey, IItemTranslator<I> translator);

	IGeneticDefinitionBuilder<I, R> setTransformer(Supplier<IGeneticTransformer<I>> transformerFactory);

	IGeneticDefinitionBuilder<I, R> setTranslator(BiFunction<Map<Item, IItemTranslator<I>>, Map<Block, IBlockTranslator<I>>, IGeneticTranslator<I>> translatorFactory);

	IGeneticDefinitionBuilder<I, R> setTypes(Function<Map<IGeneticType, IGeneticHandler<I>>, IGeneticTypes<I>> typesFactory);

	IGeneticDefinitionBuilder<I, R> setStat(Function<IGenome, IGeneticStat> statFactory);

	IGeneticDefinition<I, R> build();
}
