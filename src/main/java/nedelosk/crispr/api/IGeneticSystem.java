package nedelosk.crispr.api;

import java.util.Collection;
import java.util.Optional;

import nedelosk.crispr.api.gene.IGene;
import nedelosk.crispr.api.gene.IGeneType;
import nedelosk.crispr.api.individual.IIndividual;

public interface IGeneticSystem {
	Collection<IGeneticDefinition> getDefinitions();

	<I extends IIndividual, R extends IGeneticRoot<I, ?>> Optional<IGeneticDefinition<I, R>> getDefinition(String name);

	void registerDefinition(IGeneticDefinition definition);

	void registerGene(IGene gene, IGeneType... types);

	Optional<IGene> getGene(IGeneType type);

	Collection<IGeneType> getTypes();

	Collection<IGeneType> getTypes(IGene gene);
}
