package nedelosk.crispr.apiimp.gene;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import nedelosk.crispr.api.IGeneticDefinition;
import nedelosk.crispr.api.gene.IGeneBuilder;
import nedelosk.crispr.api.gene.IGeneRegistry;
import nedelosk.crispr.api.gene.IGeneType;
import nedelosk.crispr.api.gene.IKaryotypeBuilder;
import nedelosk.crispr.apiimp.GeneticSystem;
import nedelosk.crispr.apiimp.KaryotypeBuilder;
import nedelosk.crispr.apiimp.alleles.GeneBuilder;

public class GeneRegistry implements IGeneRegistry {
	private final Set<GeneBuilder<?>> registries = new HashSet<>();
	private final HashMap<String, IGeneticDefinition> definitions = new HashMap<>();

	@Override
	public <V> IGeneBuilder<V> createRegistry(Class<? extends V> valueClass, String name) {
		GeneBuilder<V> registry = new GeneBuilder<>(valueClass, name);
		registries.add(registry);
		return registry;
	}

	@Override
	public IKaryotypeBuilder createKaryotype(IGeneType templateType) {
		return new KaryotypeBuilder(templateType);
	}

	public GeneticSystem createGeneticRegistry() {
		GeneticSystem registry = new GeneticSystem();
		registries.forEach(r -> {
			Set<IGeneType> types = r.getTypes();
			registry.registerGene(r.createGene(), types.toArray(new IGeneType[types.size()]));
		});
		return registry;
	}
}
