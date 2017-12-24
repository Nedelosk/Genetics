package nedelosk.crispr.api;

import java.util.Collection;
import java.util.Optional;

import nedelosk.crispr.api.gene.IGene;
import nedelosk.crispr.api.gene.IGeneKey;

public interface IGeneticRegistry {
	Collection<IGeneticDefinition> getDefinitions();

	Optional<IGeneticDefinition> getDefinition(String name);

	<V> Optional<IGene<V>> getGene(IGeneKey<V> key);

	Collection<IGeneKey> getKeys();

	<V> Collection<IGeneKey<V>> getKeys(IGene<V> gene);

	IGeneticFactory factory();
}
