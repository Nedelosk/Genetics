package nedelosk.crispr.api;

import nedelosk.crispr.api.gene.IGeneRegistry;

public interface IGeneticPlugin {

	default void registerGenes(IGeneRegistry registry, IGeneticFactory factory) {

	}

	default void register(IGeneticRegistry registry) {
	}
}
