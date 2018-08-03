package genetics.api.root;

import java.util.Optional;
import java.util.function.Function;

import genetics.api.IGeneticApiInstance;
import genetics.api.IGeneticPlugin;
import genetics.api.alleles.IAlleleTemplate;
import genetics.api.alleles.IAlleleTemplateBuilder;
import genetics.api.individual.IChromosomeType;
import genetics.api.individual.IIndividual;

public interface IRootManager {

	/**
	 * Creates a {@link IIndividualRootBuilder} with the given parameters.
	 * Later before {@link IGeneticPlugin#onFinishRegistration(IRootManager, IGeneticApiInstance)} is passed to your genetic plugin the
	 * definition will be build and registered.
	 * You can get a instance of the definition with {@link IGeneticApiInstance#getRoot(String)}.
	 *
	 * @param <I>         The type of the individual that the root that the definition contains defines.
	 * @param karyotype   The karyotype of individual.
	 * @param rootFactory A function that creates the root of the definition.
	 */
	<I extends IIndividual> IIndividualRootBuilder<I> createRoot(String uid, IChromosomeType speciesType, Function<IAlleleTemplateBuilder, IAlleleTemplate> defaultTemplate, IIndividualRootFactory<I, IIndividualRoot<I>> rootFactory);

	<I extends IIndividual, T extends Enum<T> & IChromosomeType> IIndividualRootBuilder<I> createRoot(String uid, Class<? extends T> enumClass, Function<IAlleleTemplateBuilder, IAlleleTemplate> defaultTemplate, IIndividualRootFactory<I, IIndividualRoot<I>> rootFactory);

	/**
	 * Gets an IGeneticDefinitionBuilder
	 *
	 * @param name The string based unique identifier of this definition to retrieve.
	 */
	<I extends IIndividual> Optional<IIndividualRootBuilder<I>> getRoot(String name);
}
