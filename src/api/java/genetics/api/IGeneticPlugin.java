package genetics.api;

import genetics.api.registry.IAlleleRegistry;
import genetics.api.registry.IGeneticRegistry;
import genetics.api.registry.IGeneticSystem;

public interface IGeneticPlugin {

	default void registerAlleles(IAlleleRegistry registry) {
		//Default Implementation
	}

	default void registerGenes(IGeneticRegistry registry, IGeneticFactory factory) {
		//Default Implementation
	}

	default void registerDefinitions(IGeneticSystem system) {
		//Default Implementation
	}
}
