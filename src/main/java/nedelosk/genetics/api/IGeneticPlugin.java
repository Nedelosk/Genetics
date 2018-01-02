package nedelosk.genetics.api;

import nedelosk.genetics.api.registry.IAlleleRegistry;
import nedelosk.genetics.api.registry.IGeneticRegistry;
import nedelosk.genetics.api.registry.IGeneticSystem;

public interface IGeneticPlugin {

	default void registerAlleles(IAlleleRegistry registry) {
	}

	default void registerGenes(IGeneticRegistry registry, IGeneticFactory factory) {
	}

	default void registerDefinitions(IGeneticSystem system) {
	}
}
