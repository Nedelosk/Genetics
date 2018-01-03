package genetics.api.definition;

import genetics.api.gene.IKaryotype;
import genetics.api.individual.IIndividual;

/**
 * The IGeneticDefinition is wraps every interface like the {@link IGeneticTranslator}, the {@link IKaryotype}, etc.
 * that are important for the handling of the individual. And it provides the custom implementation of the
 * {@link IGeneticRoot} interface that specifies the individual and can be used to create a instance of it.
 *
 * @param <I> @param <I> The type of the individual that the definition describes.
 * @param <R> @param <R> The type of the root of the individual.
 */
public interface IGeneticDefinition<I extends IIndividual, R extends IGeneticRoot> extends IGeneticTranslator<I>, IGeneticTypes<I>, IKaryotype, ITemplateContainer {

	/**
	 * @return The string based unique identifier of this definition.
	 */
	String getUID();

	/**
	 * Gets the root
	 */
	R getRoot();
}
