package genetics.api.root;

import java.util.Map;
import java.util.Optional;

import genetics.api.IGeneticApiInstance;
import genetics.api.IGeneticPlugin;
import genetics.api.individual.IIndividual;

/**
 * <p>
 * The IGeneticSystem instance is passed to your genetic plugin in {@link IGeneticPlugin#onFinishRegistration(IRootManager, IGeneticApiInstance)}}.
 * Later you can get the instance from {@link IGeneticApiInstance#getRootRegistry()}.
 */
public interface IRootRegistry {
	Map<String, IOptionalRoot> getDefinitions();

	/**
	 * Retrieve the {@link IIndividualRoot} with the given uid.
	 *
	 * @param uid Unique id for the species class, i.e. "rootBees", "rootTrees", "rootButterflies".
	 * @return a {@link Optional} that contains the {@link IIndividualRoot} if it exists, a empty optional otherwise.
	 */
	<I extends IIndividual, R extends IIndividualRoot<I>> IOptionalRoot<R> getRoot(String uid);
}
