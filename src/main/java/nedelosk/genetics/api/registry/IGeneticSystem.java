package nedelosk.genetics.api.registry;

import java.util.Collection;
import java.util.Optional;

import nedelosk.genetics.api.definition.IGeneticDefinition;
import nedelosk.genetics.api.definition.IGeneticRoot;
import nedelosk.genetics.api.gene.IGene;
import nedelosk.genetics.api.gene.IGeneType;
import nedelosk.genetics.api.individual.IIndividual;

public interface IGeneticSystem {
	Collection<IGeneticDefinition> getDefinitions();

	<I extends IIndividual, R extends IGeneticRoot<I, ?>> Optional<IGeneticDefinition<I, R>> getDefinition(String name);

	void registerDefinition(IGeneticDefinition definition);

	void registerGene(IGene gene, IGeneType... types);

	Optional<IGene> getGene(IGeneType type);

	Collection<IGeneType> getTypes();

	Collection<IGeneType> getTypes(IGene gene);
}
