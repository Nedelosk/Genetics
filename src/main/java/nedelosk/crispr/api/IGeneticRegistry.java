package nedelosk.crispr.api;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;

import nedelosk.crispr.api.gene.IGene;
import nedelosk.crispr.api.gene.IGeneKey;
import nedelosk.crispr.api.gene.IKaryotype;
import nedelosk.crispr.api.individual.IIndividual;

public interface IGeneticRegistry {
	Collection<IGeneticDefinition> getDefinitions();

	Optional<IGeneticDefinition> getDefinition(String name);

	<I extends IIndividual> IGeneticDefinitionBuilder<I> createDefinition(String name, IKaryotype karyotype, Function<IGeneticDefinition<I>, IGeneticRoot<I>> rootFactory);

	void registerDefinition(IGeneticDefinition definition);

	void registerGene(IGene gene, IGeneKey... keys);

	<V> Optional<IGene<V>> getGene(IGeneKey<V> key);

	Collection<IGeneKey> getKeys();

	Collection<IGeneKey> getKeys(IGene gene);
}
