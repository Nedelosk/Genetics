package nedelosk.crispr;

import nedelosk.crispr.api.GeneticPlugin;
import nedelosk.crispr.api.IGeneticDefinitionBuilder;
import nedelosk.crispr.api.IGeneticFactory;
import nedelosk.crispr.api.IGeneticPlugin;
import nedelosk.crispr.api.IGeneticRegistry;
import nedelosk.crispr.api.alleles.IAlleleKey;
import nedelosk.crispr.api.alleles.IAlleleRegistry;
import nedelosk.crispr.api.gene.IGeneKey;
import nedelosk.crispr.api.gene.IGeneRegistry;
import nedelosk.crispr.api.gene.IKaryotype;
import nedelosk.crispr.api.gene.IKaryotypeBuilder;

@GeneticPlugin
public class TestPlugin implements IGeneticPlugin {
	private IGeneKey<Integer> fertility = (IGeneKey<Integer>) () -> 0;
	private final IAlleleKey average = new IAlleleKey() {
	};
	private IKaryotype karyotype;

	@Override
	public void registerGenes(IGeneRegistry registry, IGeneticFactory factory) {
		IAlleleRegistry<Integer> builder = registry.createRegistry(Integer.class, "fertility");
		builder.addKey(fertility);
		builder.addAllele(average, "average", 1, true);
		IKaryotypeBuilder karyotypeBuilder = registry.createKaryotype();
		karyotypeBuilder.register(fertility);
		karyotype = karyotypeBuilder.build();
	}

	@Override
	public void register(IGeneticRegistry registry) {
		IGeneticDefinitionBuilder builder = registry.createDefinition("tree", karyotype, null);
		registry.registerDefinition(builder.build());
	}
}
