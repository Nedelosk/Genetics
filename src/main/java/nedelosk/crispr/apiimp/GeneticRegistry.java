package nedelosk.crispr.apiimp;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Function;

import nedelosk.crispr.api.IGeneticDefinition;
import nedelosk.crispr.api.IGeneticDefinitionBuilder;
import nedelosk.crispr.api.IGeneticRegistry;
import nedelosk.crispr.api.IGeneticRoot;
import nedelosk.crispr.api.gene.IGene;
import nedelosk.crispr.api.gene.IGeneKey;
import nedelosk.crispr.api.gene.IKaryotype;
import nedelosk.crispr.api.individual.IIndividual;

public class GeneticRegistry implements IGeneticRegistry {
	private final HashMap<IGeneKey, IGene> geneByKey = new HashMap<>();
	private final Multimap<IGene, IGeneKey> keysByGene = HashMultimap.create();
	private final HashMap<String, IGeneticDefinition> definitions = new HashMap<>();

	@Override
	public Collection<IGeneticDefinition> getDefinitions() {
		return definitions.values();
	}

	@Override
	public <I extends IIndividual> IGeneticDefinitionBuilder<I> createDefinition(String name, IKaryotype karyotype, Function<IGeneticDefinition<I>, IGeneticRoot<I>> rootFactory) {
		return new GeneticDefinitionBuilder(name, karyotype, rootFactory);
	}

	@Override
	public void registerDefinition(IGeneticDefinition definition) {
		definitions.put(definition.getName(), definition);
	}

	@Override
	public Optional<IGeneticDefinition> getDefinition(String name) {
		return Optional.ofNullable(definitions.get(name));
	}

	@Override
	public void registerGene(IGene gene, IGeneKey... keys) {
		Arrays.stream(keys).forEach(k -> {
			geneByKey.put(k, gene);
			keysByGene.put(gene, k);
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public Optional<IGene> getGene(IGeneKey key) {
		return Optional.ofNullable(geneByKey.get(key));
	}

	@Override
	public Collection<IGeneKey> getKeys() {
		return geneByKey.keySet();
	}

	@Override
	public Collection<IGeneKey> getKeys(IGene gene) {
		return keysByGene.get(gene);
	}
}
