package genetics.api.registry;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import genetics.api.GeneticsAPI;
import genetics.api.IGeneticPlugin;
import genetics.api.definition.IOrganismDefinition;
import genetics.api.gene.IGene;
import genetics.api.gene.IGeneType;

/**
 * <p>
 * The IGeneticSystem instance is passed to your genetic plugin in {@link IGeneticPlugin#onFinishRegistration(IGeneticSystem)}}.
 * Later you can get the instance from {@link GeneticsAPI#geneticSystem}.
 */
public interface IGeneticSystem {
	Map<String, IOrganismDefinition> getDefinitions();

	/**
	 * Retrieve the {@link IOrganismDefinition} with the given uid.
	 *
	 * @param uid Unique id for the species class, i.e. "rootBees", "rootTrees", "rootButterflies".
	 * @return a {@link Optional} that contains the {@link IOrganismDefinition} if it exists, a empty optional otherwise.
	 */
	<D extends IOrganismDefinition> Optional<D> getDefinition(String uid);

	/**
	 * @return The gene that is registered for the given gene type.
	 */
	Optional<IGene> getGene(IGeneType type);

	/**
	 * @return A collection with all registered gene types.
	 */
	Collection<IGeneType> getTypes();

	/**
	 * @return A collection with all gene types that the given gene represents.
	 */
	Collection<IGeneType> getTypes(IGene gene);
}
