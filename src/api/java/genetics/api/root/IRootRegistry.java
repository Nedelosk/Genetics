package genetics.api.root;

import java.util.Map;

import genetics.api.IGeneticApiInstance;
import genetics.api.IGeneticPlugin;

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
	 * @return a {@link IOptionalRoot} that contains the {@link IIndividualRoot} if it exists, a empty optional otherwise.
	 */
	<R extends IIndividualRoot> IOptionalRoot<R> getRoot(String uid);
}
