package nedelosk.crispr.api;

import nedelosk.crispr.api.registry.IAlleleRegistry;
import nedelosk.crispr.api.registry.IGeneticRegistry;
import nedelosk.crispr.api.registry.IGeneticSystem;

public interface IGeneticPlugin {

	default void registerAlleles(IAlleleRegistry registry) {
	}

	default void registerGenes(IGeneticRegistry registry, IGeneticFactory factory) {
	}

	default void registerDefinitions(IGeneticSystem system) {
	}
}
