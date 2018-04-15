package genetics.api.definition;

import java.util.Map;
import java.util.Optional;

import genetics.api.IGeneticApiInstance;
import genetics.api.IGeneticPlugin;
import genetics.api.individual.IIndividual;

/**
 * <p>
 * The IGeneticSystem instance is passed to your genetic plugin in {@link IGeneticPlugin#onFinishRegistration(IDefinitionFactory, IGeneticApiInstance)}}.
 * Later you can get the instance from {@link IGeneticApiInstance#getDefinitionRegistry()}.
 */
public interface IDefinitionRegistry {
	Map<String, IOptionalDefinition> getDefinitions();

	/**
	 * Retrieve the {@link IIndividualDefinition} with the given uid.
	 *
	 * @param uid Unique id for the species class, i.e. "rootBees", "rootTrees", "rootButterflies".
	 * @return a {@link Optional} that contains the {@link IIndividualDefinition} if it exists, a empty optional otherwise.
	 */
	<I extends IIndividual, R extends IIndividualRoot<I>> IOptionalDefinition<I, R> getDefinition(String uid);
}
