package genetics.api.gene;

import java.util.Optional;

import genetics.api.IGeneticApiInstance;
import genetics.api.IGeneticPlugin;
import genetics.api.root.IRootManager;

/**
 * The {@link IGeneFactory} offers several functions for creating and retrieving several gene related things.
 * <p>
 * The IGeneticRegistry instance is passed to your genetic plugin in {@link IGeneticPlugin#registerGenes(IGeneFactory)}}.
 * Later you can get the instance from {@link IGeneticApiInstance#getAlleleRegistry()}.
 */
public interface IGeneFactory {
	/**
	 * Creates or gets a {@link IGeneBuilder} with the given name.
	 * Later before {@link IGeneticPlugin#onFinishRegistration(IRootManager, IGeneticApiInstance)} is passed to your genetic plugin the gene
	 * will be build and registered.
	 *
	 * @param name the name of the gene
	 */
	IGeneBuilder addGene(String name);

	/**
	 * Gets an gene builder.
	 *
	 * @param name The name of the gene builder to retrieve.
	 * @return A optional that contains the IGeneBuilder if found, a empty optional otherwise.
	 */
	Optional<IGeneBuilder> getGene(String name);
}
