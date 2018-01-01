package nedelosk.crispr;

import nedelosk.crispr.api.GeneticPlugin;
import nedelosk.crispr.api.IGeneticFactory;
import nedelosk.crispr.api.IGeneticPlugin;
import nedelosk.crispr.api.IGeneticSystem;
import nedelosk.crispr.api.ITemplateContainer;
import nedelosk.crispr.api.ITemplateRegistry;
import nedelosk.crispr.api.alleles.IAlleleKey;
import nedelosk.crispr.api.alleles.IAlleleRegistry;
import nedelosk.crispr.api.gene.IGeneRegistry;
import nedelosk.crispr.api.gene.IGeneType;
import nedelosk.crispr.api.gene.IKaryotype;

@GeneticPlugin
public class Plugin implements IGeneticPlugin {
	public enum AlleleKey implements IAlleleKey {
		FERTILITY_0
	}

	private static ITemplateContainer container;

	public enum GeneType implements IGeneType {
		FERTILITY;

		@Override
		public int getIndex() {
			return ordinal();
		}

		@Override
		public ITemplateContainer getContainer() {
			return container;
		}
	}

	@Override
	public void registerAlleles(IAlleleRegistry registry) {
		registry.registerAllele(0, false, "cultivation:fertility", AlleleKey.FERTILITY_0);
		//registry.createCategory("integer", (v, d)-> new Allele(v, d, "culti.f"));
		//registry.getCategory("integer").ifPresent((c)->c.registerAllele(0, false));
	}

	@Override
	public void registerGenes(IGeneRegistry registry, IGeneticFactory factory) {
		//karyotype = registry.createKaryotype().
		IKaryotype karyotype = registry.createKaryotype(GeneType.class);
		ITemplateRegistry templateRegistry = registry.createRegistry(karyotype);
		registry.addGene("fertility").addAllele(AlleleKey.FERTILITY_0, "fertility.average").setDefaultAllele(AlleleKey.FERTILITY_0).addType(GeneType.FERTILITY);
	}

	@Override
	public void registerDefinitions(IGeneticSystem system) {

	}
}
