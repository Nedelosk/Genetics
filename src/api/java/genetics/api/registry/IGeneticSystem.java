package genetics.api.registry;

import java.util.Collection;
import java.util.Optional;

import genetics.api.definition.IGeneticDefinition;
import genetics.api.definition.IGeneticRoot;
import genetics.api.gene.IGene;
import genetics.api.gene.IGeneType;
import genetics.api.individual.IIndividual;

public interface IGeneticSystem {
	Collection<IGeneticDefinition> getDefinitions();

	<I extends IIndividual, R extends IGeneticRoot<I, ?>> Optional<IGeneticDefinition<I, R>> getDefinition(String name);

	void registerDefinition(IGeneticDefinition definition);

	void registerGene(IGene gene, IGeneType... types);

	Optional<IGene> getGene(IGeneType type);

	Collection<IGeneType> getTypes();

	Collection<IGeneType> getTypes(IGene gene);
}
