package genetics.api.root;

import java.util.Optional;

import genetics.api.IGeneticApiInstance;
import genetics.api.IGeneticPlugin;
import genetics.api.gene.IKaryotype;
import genetics.api.individual.IIndividual;

public interface IRootManager {

	/**
	 * Creates a {@link IIndividualRootBuilder} with the given parameters.
	 * Later before {@link IGeneticPlugin#onFinishRegistration(IRootManager, IGeneticApiInstance)} is passed to your genetic plugin the
	 * definition will be build and registered.
	 * You can get a instance of the definition with {@link IRootRegistry#getRoot(String)}.
	 *
	 * @param name        The string based unique identifier of this definition.
	 * @param karyotype   The karyotype of individual.
	 * @param rootFactory A function that creates the root of the definition.
	 * @param <I>         The type of the individual that the root that the definition contains defines.
	 * @param <R>         The type of the root that the definition contains.
	 */
	<I extends IIndividual, R extends IIndividualRoot<I>> IIndividualRootBuilder<I, R> createRoot(String name, IKaryotype karyotype, IIndividualRootFactory<I, R> rootFactory);

	/**
	 * Gets an IGeneticDefinitionBuilder
	 *
	 * @param name The string based unique identifier of this definition to retrieve.
	 */
	<I extends IIndividual, R extends IIndividualRoot<I>> Optional<IIndividualRootBuilder<I, R>> getRoot(String name);
}