package nedelosk.crispr.api;

import nedelosk.crispr.api.alleles.IAlleleRegistry;
import nedelosk.crispr.api.gene.IGeneRegistry;

public interface IGeneticPlugin {

	default void registerAlleles(IAlleleRegistry registry) {
	}

	default void registerGenes(IGeneRegistry registry, IGeneticFactory factory) {
	}

	default void registerDefinitions(IGeneticSystem system) {
	}
}
