package genetics.api.definition;

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
import genetics.api.alleles.IAlleleTemplateBuilder;
import genetics.api.gene.IKaryotype;
import genetics.api.individual.IGeneticHandler;
import genetics.api.individual.IGeneticType;
import genetics.api.individual.IIndividual;
import genetics.api.registry.IGeneticRegistry;
import genetics.api.registry.IGeneticSystem;
import genetics.api.translators.IBlockTranslator;
import genetics.api.translators.IGeneticTranslator;
import genetics.api.translators.IItemTranslator;

/**
 * The IGeneticDefinitionBuilder offers several functions to register templates, types or something similar that can be
 * later retrieved from the IGeneticDefinition.
 *
 * After every {@link IGeneticPlugin} received {@link IGeneticPlugin#register(IGeneticRegistry)} all
 * {@link IGeneticDefinitionBuilder}s will be build automatically to {@link IGeneticDefinition}s. You can the an instance
 * of you definition from {@link IGeneticSystem#getDefinition(String)} after it was created.
 * <p>
 * You can create a instance of this with {@link IGeneticRegistry#createDefinition(String, IKaryotype, Function)}.
 *
 * @param <I> The type of the individual that the definition describes.
 */
public interface IGeneticDefinitionBuilder<I extends IIndividual> {

	IGeneticDefinitionBuilder<I> registerType(IGeneticType type, IGeneticHandler<I> handler);

	/**
	 * @param translatorKey The key of the translator the block of {@link IBlockState} that you want to translate
	 *                      with the translator.
	 * @param translator    A translator that should be used to translate the data.
	 */
	IGeneticDefinitionBuilder<I> registerTranslator(Block translatorKey, IBlockTranslator<I> translator);

	/**
	 * @param translatorKey The key of the translator it is the item of the {@link ItemStack} that you want to translate
	 *                      with the translator.
	 * @param translator    A translator that should be used to translate the data.
	 */
	IGeneticDefinitionBuilder<I> registerTranslator(Item translatorKey, IItemTranslator<I> translator);

	/**
	 * Registers a allele template using the UID of the first allele as identifier.
	 */
	IGeneticDefinitionBuilder<I> registerTemplate(IAllele[] template);

	/**
	 * Registers a allele template using the passed identifier.
	 */
	//TODO: REMOVE ?
	IGeneticDefinitionBuilder<I> registerTemplate(String identifier, IAllele[] template);

	/**
	 * Registers a allele template using the UID of the first allele as identifier.
	 */
	IGeneticDefinitionBuilder<I> registerTemplate(IAlleleTemplate template);

	/**
	 * Registers a allele template using the passed identifier.
	 */
	//TODO: REMOVE ?
	IGeneticDefinitionBuilder<I> registerTemplate(String identifier, IAlleleTemplate template);

	IGeneticDefinitionBuilder<I> setTranslator(BiFunction<Map<Item, IItemTranslator<I>>, Map<Block, IBlockTranslator<I>>, IGeneticTranslator<I>> translatorFactory);

	IGeneticDefinitionBuilder<I> setTypes(Function<Map<IGeneticType, IGeneticHandler<I>>, IGeneticTypes<I>> typesFactory);

	IGeneticDefinitionBuilder<I> setTemplateFactory(BiFunction<IKaryotype, IAllele[], IAlleleTemplateBuilder> templateFactory);

	IGeneticDefinitionBuilder<I> setTemplateNameFactory(BiFunction<IKaryotype, IAllele[], String> templateNameFactory);
}
