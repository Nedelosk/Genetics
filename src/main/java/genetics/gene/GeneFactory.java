package genetics.gene;

import java.util.HashMap;
import java.util.Optional;

import genetics.api.gene.IGeneBuilder;
import genetics.api.gene.IGeneFactory;

public class GeneFactory implements IGeneFactory {
	private final HashMap<String, Gene.Builder> geneBuilders = new HashMap<>();

	@Override
	public IGeneBuilder addGene(String name) {
		if (geneBuilders.containsKey(name)) {
			return geneBuilders.get(name);
		}
		Gene.Builder registry = new Gene.Builder(name);
		geneBuilders.put(name, registry);
		return registry;
	}

	public Optional<IGeneBuilder> getGene(String name) {
		return Optional.ofNullable(geneBuilders.get(name));
	}

	public GeneRegistry createRegistry() {
		return new GeneRegistry(geneBuilders);
	}
}
