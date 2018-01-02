package genetics.api.registry;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import genetics.api.GeneticsAPI;
import genetics.api.IGeneticPlugin;
import genetics.api.definition.IGeneticDefinition;
import genetics.api.definition.IGeneticRoot;
import genetics.api.gene.IGene;
import genetics.api.gene.IGeneType;
import genetics.api.individual.IIndividual;

/**
 * <p>
 * The IGeneticSystem instance is passed to your genetic plugin in {@link IGeneticPlugin#onFinishRegistration(IGeneticSystem)}}.
 * Later you can get the instance from {@link GeneticsAPI#geneticSystem}.
 */
public interface IGeneticSystem {
	Map<String, IGeneticDefinition> getDefinitions();

	/**
	 * Retrieve the {@link IGeneticDefinition} with the given uid.
	 *
	 * @param uid Unique id for the species class, i.e. "rootBees", "rootTrees", "rootButterflies".
	 * @return a {@link Optional} that contains the {@link IGeneticDefinition} if it exists, a empty optional otherwise.
	 */
	<I extends IIndividual, R extends IGeneticRoot<I, ?>> Optional<IGeneticDefinition<I, R>> getDefinition(String uid);


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
