package nedelosk.crispr.apiimp.gene;

import com.google.common.collect.ImmutableSet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import nedelosk.crispr.api.IGeneticDefinition;
import nedelosk.crispr.api.alleles.IAlleleRegistry;
import nedelosk.crispr.api.gene.IGene;
import nedelosk.crispr.api.gene.IGeneKey;
import nedelosk.crispr.api.gene.IGeneRegistry;
import nedelosk.crispr.api.gene.IKaryotypeBuilder;
import nedelosk.crispr.apiimp.GeneticRegistry;
import nedelosk.crispr.apiimp.KaryotypeBuilder;
import nedelosk.crispr.apiimp.alleles.AlleleRegistry;

public class GeneRegistry implements IGeneRegistry {
	private final Set<AlleleRegistry> registries = new HashSet<>();
	private final HashMap<String, IGeneticDefinition> definitions = new HashMap<>();

	@Override
	public <V> IAlleleRegistry<V> createRegistry(Class<? extends V> valueClass, String name) {
		AlleleRegistry<V> registry = new AlleleRegistry<>(valueClass, name);
		registries.add(registry);
		return registry;
	}

	@Override
	public IKaryotypeBuilder createKaryotype() {
		return new KaryotypeBuilder();
	}

	@SuppressWarnings("unchecked")
	public GeneticRegistry createGeneticRegistry() {
		ImmutableSet.Builder<IGene> geneBuilder = new ImmutableSet.Builder<>();
		registries.forEach(r -> geneBuilder.add(r.createGene()));
		Set<IGene> genes = geneBuilder.build();
		GeneticRegistry registry = new GeneticRegistry();
		registries.forEach(r -> {
			Set<IGeneKey> keys = r.getKeys();
			registry.registerGene(r.createGene(), keys.toArray(new IGeneKey[keys.size()]));
		});
		return registry;
	}
}
