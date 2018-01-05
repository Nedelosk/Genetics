package genetics.api.definition;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import genetics.api.IBlockTranslator;
import genetics.api.IGeneticPlugin;
import genetics.api.IItemTranslator;
import genetics.api.alleles.IAllele;
import genetics.api.alleles.IAlleleTemplate;
import genetics.api.gene.IKaryotype;
import genetics.api.individual.IOrganism;
import genetics.api.individual.IOrganismHandler;
import genetics.api.individual.IOrganismType;
import genetics.api.registry.IGeneticRegistry;
import genetics.api.registry.IGeneticSystem;

/**
 * The IGeneticDefinitionBuilder offers several functions to register templates, types or something similar that can be
 * later retrieved from the IGeneticDefinition.
 *
 * After every {@link IGeneticPlugin} received {@link IGeneticPlugin#register(IGeneticRegistry)} all
 * {@link IOrganismDefinitionBuilder}s will be build automatically to {@link IOrganismDefinition}s. You can the instance
 * of you definition from {@link IGeneticSystem#getDefinition(String)} after it was created.
 * <p>
 * You can create a instance of this with {@link IGeneticRegistry#createDefinition(String, IKaryotype, Function)}.
 *
 * @param <I> The type of the individual that the definition describes.
 */
public interface IOrganismDefinitionBuilder<I extends IOrganism> {

	IOrganismDefinitionBuilder<I> registerType(IOrganismType type, IOrganismHandler<I> handler);

	/**
	 * @param translatorKey The key of the translator the block of {@link IBlockState} that you want to translate
	 *                      with the translator.
	 * @param translator    A translator that should be used to translate the data.
	 */
	IOrganismDefinitionBuilder<I> registerTranslator(Block translatorKey, IBlockTranslator<I> translator);

	/**
	 * @param translatorKey The key of the translator it is the item of the {@link ItemStack} that you want to translate
	 *                      with the translator.
	 * @param translator    A translator that should be used to translate the data.
	 */
	IOrganismDefinitionBuilder<I> registerTranslator(Item translatorKey, IItemTranslator<I> translator);

	/**
	 * Registers a allele template using the UID of the first allele as identifier.
	 */
	IOrganismDefinitionBuilder<I> registerTemplate(IAllele[] template);

	/**
	 * Registers a allele template using the passed identifier.
	 */
	//TODO: REMOVE ?
	IOrganismDefinitionBuilder<I> registerTemplate(String identifier, IAllele[] template);

	/**
	 * Registers a allele template using the UID of the first allele as identifier.
	 */
	IOrganismDefinitionBuilder<I> registerTemplate(IAlleleTemplate template);

	/**
	 * Registers a allele template using the passed identifier.
	 */
	//TODO: REMOVE ?
	IOrganismDefinitionBuilder<I> registerTemplate(String identifier, IAlleleTemplate template);

	IOrganismDefinitionBuilder<I> setTranslator(BiFunction<Map<Item, IItemTranslator<I>>, Map<Block, IBlockTranslator<I>>, IGeneticTranslator<I>> translatorFactory);

	IOrganismDefinitionBuilder<I> setTypes(Function<Map<IOrganismType, IOrganismHandler<I>>, IOrganismTypes<I>> typesFactory);

	/**
	 * The karyotype that was used to create this builder.
	 */
	IKaryotype getKaryotype();

	/**
	 * The default template
	 */
	IAlleleTemplate getDefaultTemplate();
}
