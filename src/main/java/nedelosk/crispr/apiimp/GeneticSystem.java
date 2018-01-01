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
import nedelosk.crispr.api.IGeneticRoot;
import nedelosk.crispr.api.IGeneticSystem;
import nedelosk.crispr.api.ITemplateContainer;
import nedelosk.crispr.api.gene.IGene;
import nedelosk.crispr.api.gene.IGeneType;
import nedelosk.crispr.api.gene.IKaryotype;
import nedelosk.crispr.api.individual.IIndividual;

public class GeneticSystem implements IGeneticSystem {
	private final HashMap<IGeneType, IGene> geneByType = new HashMap<>();
	private final Multimap<IGene, IGeneType> typesByGene = HashMultimap.create();
	private final HashMap<String, IGeneticDefinition> definitions = new HashMap<>();
	private final HashMap<IKaryotype, ITemplateContainer> containers = new HashMap<>();

	@Override
	public Collection<IGeneticDefinition> getDefinitions() {
		return definitions.values();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <I extends IIndividual, R extends IGeneticRoot<I, ?>> IGeneticDefinitionBuilder<I, R> createDefinition(String name, ITemplateContainer templateContainer, Function<IGeneticDefinition<I, R>, R> rootFactory) {
		return new GeneticDefinitionBuilder(name, templateContainer, rootFactory);
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
	public void registerGene(IGene gene, IGeneType... types) {
		Arrays.stream(types).forEach(k -> {
			geneByType.put(k, gene);
			typesByGene.put(gene, k);
		});
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

	@Override
	public void registerTemplates(ITemplateContainer container) {
		containers.put(container.getKaryotype(), container);
	}

	@Override
	public Optional<ITemplateContainer> getTemplates(IKaryotype karyotype) {
		return Optional.ofNullable(containers.get(karyotype));
	}
}
