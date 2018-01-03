package genetics.api.registry;

import java.util.Optional;
import java.util.function.Function;

import genetics.api.GeneticsAPI;
import genetics.api.IGeneticPlugin;
import genetics.api.definition.IGeneticDefinition;
import genetics.api.definition.IGeneticDefinitionBuilder;
import genetics.api.definition.IGeneticRoot;
import genetics.api.gene.IGeneBuilder;
import genetics.api.gene.IGeneType;
import genetics.api.gene.IKaryotype;
import genetics.api.gene.IKaryotypeBuilder;
import genetics.api.individual.IIndividual;

/**
 * The {@link IGeneticRegistry} offers several functions for creating and retrieving several gene related things.
 * <p>
 * The IGeneticRegistry instance is passed to your genetic plugin in {@link IGeneticPlugin#register(IGeneticRegistry)}}.
 * Later you can get the instance from {@link GeneticsAPI#geneticRegistry}.
 */
public interface IGeneticRegistry {
	/**
	 * Creates a {@link IGeneBuilder} with the given name.
	 * Later before {@link IGeneticPlugin#onFinishRegistration(IGeneticSystem)} is passed to your genetic plugin the gene
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

	/**
	 * Creates a IKaryotypeBuilder.
	 *
	 * @param templateType The template type of the karyotype
	 * @return A IKaryotypeBuilder that can be used to build {@link IKaryotype}.
	 */
	IKaryotypeBuilder createKaryotype(IGeneType templateType, String identifier);

	/**
	 * Creates a IKaryotype
	 *
	 * @param enumClass A enum that implements {@link IGeneType}.
	 */
	<T extends Enum<T> & IGeneType> IKaryotype createKaryotype(Class<? extends T> enumClass, String identifier);

	/**
	 * Creates a {@link IGeneticDefinitionBuilder} with the given parameters.
	 * Later before {@link IGeneticPlugin#onFinishRegistration(IGeneticSystem)} is passed to your genetic plugin the
	 * definition will be build and registered.
	 * You can get a instance of the definition with {@link IGeneticSystem#getDefinition(String)}.
	 *
	 * @param name        The string based unique identifier of this definition.
	 * @param karyotype   The karyotype of individual.
	 * @param rootFactory A function that creates the root of the definition.
	 * @param <I>         The type of the individual that the root that the definition contains defines.
	 * @param <R>         The type of the root that the definition contains.
	 */
	<I extends IIndividual, R extends IGeneticRoot<I, ?>> IGeneticDefinitionBuilder<I> createDefinition(String name, IKaryotype karyotype, Function<IGeneticDefinition<I, R>, R> rootFactory);

	/**
	 * Gets an IGeneticDefinitionBuilder
	 *
	 * @param name The string based unique identifier of this definition to retrieve.
	 */
	<I extends IIndividual> Optional<IGeneticDefinitionBuilder<I>> getDefinition(String name);
}
