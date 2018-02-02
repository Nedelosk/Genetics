package genetics.api.definition;

import java.util.Optional;

import net.minecraft.item.ItemStack;

import genetics.api.alleles.IAllele;
import genetics.api.gene.IKaryotype;
import genetics.api.individual.IIndividual;
import genetics.api.organism.IOrganismType;
import genetics.api.organism.IOrganismTypes;

/**
 * The IGeneticDefinition is wraps every interface like the {@link IIndividualTranslator}, the {@link IKaryotype}, etc.
 * that are important for the handling of the individual. And it provides the custom implementation of the
 * {@link IIndividualRoot} interface that specifies the individual and can be used to create a instance of it.
 *
 * @param <I> @param <I> The type of the individual that the definition describes.
 * @param <R> @param <R> The type of the root of the individual.
 */
public interface IIndividualDefinition<I extends IIndividual, R extends IIndividualRoot<I, ?>> {

	/**
	 * @return The string based unique identifier of this definition.
	 */
	String getUID();

	/**
	 * Gets the root
	 */
	R getRoot();

	Optional<I> createIndividual(String templateIdentifier);

	ItemStack createStack(IAllele allele, IOrganismType type);

	ITemplateContainer getTemplates();

	IKaryotype getKaryotype();

	IIndividualTranslator<I> getTranslator();

	IOrganismTypes<I> getTypes();
}
