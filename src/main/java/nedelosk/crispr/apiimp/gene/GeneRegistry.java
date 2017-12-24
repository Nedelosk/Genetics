package nedelosk.crispr.apiimp.gene;

import java.util.HashMap;

import nedelosk.crispr.api.IGeneticDefinition;
import nedelosk.crispr.api.IGeneticDefinitionBuilder;
import nedelosk.crispr.api.gene.IGene;
import nedelosk.crispr.api.gene.IGeneBuilder;
import nedelosk.crispr.api.gene.IGeneKey;
import nedelosk.crispr.api.gene.IGeneRegistry;
import nedelosk.crispr.api.gene.IKaryotype;
import nedelosk.crispr.api.gene.IKaryotypeBuilder;
import nedelosk.crispr.api.individual.IIndividual;
import nedelosk.crispr.api.individual.IIndividualFactory;
import nedelosk.crispr.apiimp.GeneticDefinitionBuilder;
import nedelosk.crispr.apiimp.KaryotypeBuilder;

public class GeneRegistry implements IGeneRegistry {
	private final HashMap<IGeneKey, IGene> genes = new HashMap<>();
	private final HashMap<String, IGeneticDefinition> definitions = new HashMap<>();

	@Override
	public <V> IGeneBuilder<V> createGene(Class<? extends V> valueClass) {
		return new GeneBuilder<>(valueClass);
	}

	@Override
	public void registerGene(IGene gene, IGeneKey... keys) {
		for (IGeneKey key : keys) {
			genes.put(key, gene);
		}
	}

	@Override
	public IKaryotypeBuilder createKaryotype() {
		return new KaryotypeBuilder();
	}

	@Override
	public <I extends IIndividual> IGeneticDefinitionBuilder<I> createDefinition(String name, IKaryotype karyotype, IIndividualFactory<I> factory) {
		return new GeneticDefinitionBuilder(name, karyotype, factory);
	}

	@Override
	public void registerDefinition(IGeneticDefinition definition) {
		definitions.put(definition.getName(), definition);
	}
}
