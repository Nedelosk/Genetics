package genetics.gene;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import genetics.api.gene.IChromosomeType;
import genetics.api.gene.IGene;
import genetics.api.gene.IGeneRegistry;

public class GeneRegistry implements IGeneRegistry {
	private final HashMap<IChromosomeType, IGene> geneByType = new HashMap<>();
	private final Multimap<IGene, IChromosomeType> typesByGene = HashMultimap.create();

	public GeneRegistry(Map<String, Gene.Builder> geneBuilders) {
		geneBuilders.values().forEach(r -> {
			Set<IChromosomeType> types = r.getTypes();
			IGene gene = r.createGene();
			types.forEach(k -> {
				geneByType.put(k, gene);
				typesByGene.put(gene, k);
			});
		});
	}

	@Override
	public Optional<IGene> getGene(IChromosomeType type) {
		return Optional.ofNullable(geneByType.get(type));
	}

	@Override
	public Collection<IChromosomeType> getTypes() {
		return geneByType.keySet();
	}

	@Override
	public Collection<IChromosomeType> getTypes(IGene gene) {
		return typesByGene.get(gene);
	}
}
