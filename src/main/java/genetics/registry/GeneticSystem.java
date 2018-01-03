package genetics.registry;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import genetics.api.definition.IGeneticDefinition;
import genetics.api.definition.IGeneticRoot;
import genetics.api.gene.IGene;
import genetics.api.gene.IGeneType;
import genetics.api.individual.IIndividual;
import genetics.api.registry.IGeneticSystem;

public class GeneticSystem implements IGeneticSystem {
	private final HashMap<IGeneType, IGene> geneByType = new HashMap<>();
	private final Multimap<IGene, IGeneType> typesByGene = HashMultimap.create();
	private final HashMap<String, IGeneticDefinition> definitions = new HashMap<>();

	@Override
	public Map<String, IGeneticDefinition> getDefinitions() {
		return Collections.unmodifiableMap(definitions);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <I extends IIndividual, R extends IGeneticRoot<I, ?>> Optional<IGeneticDefinition<I, R>> getDefinition(String uid) {
		return Optional.ofNullable((IGeneticDefinition<I, R>) definitions.get(uid));
	}

	@Override
	public Optional<IGene> getGene(IGeneType type) {
		return Optional.ofNullable(geneByType.get(type));
	}

	@Override
	public Collection<IGeneType> getTypes() {
		return geneByType.keySet();
	}

	@Override
	public Collection<IGeneType> getTypes(IGene gene) {
		return typesByGene.get(gene);
	}

	void registerDefinition(IGeneticDefinition definition) {
		definitions.put(definition.getUID(), definition);
	}

	void registerGene(IGene gene, IGeneType... types) {
		Arrays.stream(types).forEach(k -> {
			geneByType.put(k, gene);
			typesByGene.put(gene, k);
		});
	}
}