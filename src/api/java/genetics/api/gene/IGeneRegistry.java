package genetics.api.gene;

import java.util.Collection;
import java.util.Optional;

public interface IGeneRegistry {
	/**
	 * @return The gene that is registered for the given gene type.
	 */
	Optional<IGene> getGene(IChromosomeType type);

	/**
	 * @return A collection with all registered gene types.
	 */
	Collection<IChromosomeType> getTypes();

	/**
	 * @return A collection with all gene types that the given gene represents.
	 */
	Collection<IChromosomeType> getTypes(IGene gene);
}
