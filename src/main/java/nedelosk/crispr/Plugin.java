package nedelosk.crispr;

import nedelosk.crispr.api.GeneticPlugin;
import nedelosk.crispr.api.IGeneticPlugin;
import nedelosk.crispr.api.alleles.IAlleleRegistry;

@GeneticPlugin
public class Plugin implements IGeneticPlugin {
	@Override
	public void registerAlleles(IAlleleRegistry registry) {
		registry.registerAllele(0, false, "cultivation:fertility");
		//registry.createCategory("integer", (v, d)-> new Allele(v, d, "culti.f"));
		//registry.getCategory("integer").ifPresent((c)->c.registerAllele(0, false));
	}

	public void registerGenes() {

	}

	public void createKaryotype() {

	}

	public void createDefinition() {

	}
}
