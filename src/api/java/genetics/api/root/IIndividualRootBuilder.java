package genetics.api.root;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import genetics.api.IGeneticPlugin;
import genetics.api.alleles.IAllele;
import genetics.api.alleles.IAlleleTemplate;
import genetics.api.gene.IGeneFactory;
import genetics.api.gene.IKaryotype;
import genetics.api.individual.IIndividual;
import genetics.api.organism.IOrganism;
import genetics.api.organism.IOrganismHandler;
import genetics.api.organism.IOrganismType;
import genetics.api.organism.IOrganismTypes;
import genetics.api.root.translator.IBlockTranslator;
import genetics.api.root.translator.IIndividualTranslator;
import genetics.api.root.translator.IItemTranslator;

/**
 * The IIndividualRootBuilder offers several functions to register templates, types or something similar that can be
 * later retrieved from the {@link IIndividualRoot}.
 * <p>
 * After every {@link IGeneticPlugin} received {@link IGeneticPlugin#registerGenes(IGeneFactory)} all
 * {@link IIndividualRootBuilder}s will be build automatically to {@link IIndividualRoot}s. You can get the instance
 * of you root from {@link IRootRegistry#getRoot(String)} after it was created or you can use {@link #getDefinition()}.
 * <p>
 * You can create a instance of this with {@link IRootManager#createRoot(String, IKaryotype, IIndividualRootFactory)}.
 *
 * @param <I> The type of the individual that the root describes.
 */
public interface IIndividualRootBuilder<I extends IIndividual> {

	/**
	 * Registers a {@link IOrganismType} for the {@link IIndividual} of the root.
	 *
	 * @param type    The organism type itself.
	 * @param handler The organism handler that handles the creation of the {@link IIndividual} and the {@link ItemStack}
	 *                that contains the {@link IOrganism}.
	 */
	IIndividualRootBuilder<I> registerType(IOrganismType type, IOrganismHandler<I> handler);

	/**
	 * Registers a translator that translates a {@link IBlockState} into a  {@link IIndividual} or an {@link ItemStack}
	 * that contains an {@link IOrganism}.
	 *
	 * @param translatorKey The key of the translator the block of {@link IBlockState} that you want to translate
	 *                      with the translator.
	 * @param translator    A translator that should be used to translate the data.
	 */
	IIndividualRootBuilder<I> registerTranslator(Block translatorKey, IBlockTranslator<I> translator);

	/**
	 * Registers a translator that translates an {@link ItemStack} that does not contain an {@link IOrganism} into a
	 * {@link IIndividual} or another {@link ItemStack} that contains an {@link IOrganism}.
	 *
	 * @param translatorKey The key of the translator it is the item of the {@link ItemStack} that you want to translate
	 *                      with the translator.
	 * @param translator    A translator that should be used to translate the data.
	 */
	IIndividualRootBuilder<I> registerTranslator(Item translatorKey, IItemTranslator<I> translator);

	/**
	 * Registers a allele template using the UID of the first allele as identifier.
	 */
	IIndividualRootBuilder<I> registerTemplate(IAllele[] template);

	/**
	 * Registers a allele template using the UID of the first allele as identifier.
	 */
	IIndividualRootBuilder<I> registerTemplate(IAlleleTemplate template);

	/**
	 * Sets a factory that crates the {@link IIndividualTranslator} object.
	 */
	IIndividualRootBuilder<I> setTranslator(BiFunction<Map<Item, IItemTranslator<I>>, Map<Block, IBlockTranslator<I>>, IIndividualTranslator<I>> translatorFactory);

	/**
	 * ets a factory that crates {@link IOrganismTypes} object.
	 */
	IIndividualRootBuilder<I> setTypes(Function<Map<IOrganismType, IOrganismHandler<I>>, IOrganismTypes<I>> typesFactory);

	/**
	 * The karyotype that was used to create this builder.
	 */
	IKaryotype getKaryotype();

	/**
	 * Returns an optional that contains the created root object.
	 * <p>
	 * Returns an empty optional if the root was not built yet.
	 *
	 * @return An optional that contains the root object of this builder if it was already built, otherwise an empty
	 * optional.
	 */
	<R extends IIndividualRoot<I>> IRootDefinition<R> getDefinition();
}
