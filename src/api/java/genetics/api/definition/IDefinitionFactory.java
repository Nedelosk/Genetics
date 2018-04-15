package genetics.api.definition;

import java.util.Optional;
import java.util.function.Function;

import genetics.api.IGeneticApiInstance;
import genetics.api.IGeneticPlugin;
import genetics.api.gene.IKaryotype;
import genetics.api.individual.IIndividual;

public interface IDefinitionFactory {

	/**
	 * Creates a {@link IIndividualDefinitionBuilder} with the given parameters.
	 * Later before {@link IGeneticPlugin#onFinishRegistration(IDefinitionFactory, IGeneticApiInstance)} is passed to your genetic plugin the
	 * definition will be build and registered.
	 * You can get a instance of the definition with {@link IDefinitionRegistry#getDefinition(String)}.
	 *
	 * @param name        The string based unique identifier of this definition.
	 * @param karyotype   The karyotype of individual.
	 * @param rootFactory A function that creates the root of the definition.
	 * @param <I>         The type of the individual that the root that the definition contains defines.
	 * @param <R>         The type of the root that the definition contains.
	 */
	<I extends IIndividual, R extends IIndividualRoot<I>> IIndividualDefinitionBuilder<I, R> createDefinition(String name, IKaryotype karyotype, Function<IIndividualDefinition<I, R>, R> rootFactory);

	/**
	 * Gets an IGeneticDefinitionBuilder
	 *
	 * @param name The string based unique identifier of this definition to retrieve.
	 */
	<I extends IIndividual, R extends IIndividualRoot<I>> Optional<IIndividualDefinitionBuilder<I, R>> getDefinition(String name);
}
