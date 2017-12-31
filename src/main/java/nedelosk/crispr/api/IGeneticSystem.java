package nedelosk.crispr.api;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;

import nedelosk.crispr.api.gene.IGene;
import nedelosk.crispr.api.gene.IGeneType;
import nedelosk.crispr.api.gene.IKaryotype;
import nedelosk.crispr.api.individual.IIndividual;

public interface IGeneticSystem {
	Collection<IGeneticDefinition> getDefinitions();

	Optional<IGeneticDefinition> getDefinition(String name);

	<I extends IIndividual> IGeneticDefinitionBuilder<I> createDefinition(String name, IKaryotype karyotype, Function<IGeneticDefinition<I>, IGeneticRoot<I, ?>> rootFactory);

	void registerDefinition(IGeneticDefinition definition);

	void registerGene(IGene gene, IGeneType... types);

	Optional<IGene> getGene(IGeneType type);

	Collection<IGeneType> getTypes();

	Collection<IGeneType> getTypes(IGene gene);
}
