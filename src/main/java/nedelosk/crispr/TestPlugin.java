package nedelosk.crispr;

import nedelosk.crispr.api.IGeneticDefinitionBuilder;
import nedelosk.crispr.api.IGeneticFactory;
import nedelosk.crispr.api.IGeneticPlugin;
import nedelosk.crispr.api.gene.IGeneBuilder;
import nedelosk.crispr.api.gene.IGeneKey;
import nedelosk.crispr.api.gene.IGeneRegistry;
import nedelosk.crispr.api.gene.IKaryotype;
import nedelosk.crispr.api.gene.IKaryotypeBuilder;
import nedelosk.crispr.apiimp.GeneticDefinitionBuilder;

public class TestPlugin implements IGeneticPlugin {
	private IGeneKey<Integer> fertility = (IGeneKey<Integer>) () -> 0;
	private IKaryotype karyotype;

	@Override
	public void registerGenes(IGeneRegistry registry, IGeneticFactory factory) {
		IGeneBuilder<Integer> builder = registry.createGene(Integer.class);
		builder.registerAllele("average", 1, true);
		builder.register(fertility);
		IKaryotypeBuilder karyotypeBuilder = registry.createKaryotype();
		karyotypeBuilder.register(fertility);
		karyotype = karyotypeBuilder.build();
	}

	@Override
	public void register(IGeneRegistry registry) {
		IGeneticDefinitionBuilder builder = new GeneticDefinitionBuilder("tree", karyotype, null);
		registry.registerDefinition(builder.register());
	}
}
