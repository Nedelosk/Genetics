package nedelosk.crispr;

import nedelosk.crispr.api.CrisprAPI;
import nedelosk.crispr.api.GeneticPlugin;
import nedelosk.crispr.api.IGeneticFactory;
import nedelosk.crispr.api.IGeneticPlugin;
import nedelosk.crispr.api.alleles.IAlleleKey;
import nedelosk.crispr.api.definition.IGeneticDefinition;
import nedelosk.crispr.api.definition.IGeneticDefinitionBuilder;
import nedelosk.crispr.api.gene.IGeneType;
import nedelosk.crispr.api.gene.IKaryotype;
import nedelosk.crispr.api.registry.IAlleleRegistry;
import nedelosk.crispr.api.registry.IGeneticRegistry;
import nedelosk.crispr.api.registry.IGeneticSystem;

@GeneticPlugin
public class Plugin implements IGeneticPlugin {
	private static IGeneticDefinition definition;

	@Override
	public void registerAlleles(IAlleleRegistry registry) {
		registry.registerAllele(0, false, "cultivation:fertility", AlleleKey.FERTILITY_0);
		//registry.createCategory("integer", (v, d)-> new Allele(v, d, "culti.f"));
		//registry.getCategory("integer").ifPresent((c)->c.registerAllele(0, false));
	}

	@Override
	public void registerGenes(IGeneticRegistry registry, IGeneticFactory factory) {
		//karyotype = registry.createKaryotype().
		IKaryotype karyotype = registry.createKaryotype(GeneType.class);
		IGeneticDefinitionBuilder definitionBuilder = registry.createDefinition("plants", karyotype, r -> null);
		registry.addGene("fertility").addAllele(AlleleKey.FERTILITY_0, "fertility.average").setDefaultAllele(AlleleKey.FERTILITY_0).addType(GeneType.FERTILITY);
	}

	@Override
	public void registerDefinitions(IGeneticSystem system) {

	}

	public enum AlleleKey implements IAlleleKey {
		FERTILITY_0
	}

	public enum GeneType implements IGeneType {
		FERTILITY;

		@Override
		public int getIndex() {
			return ordinal();
		}

		@Override
		public IGeneticDefinition getDefinition() {
			return CrisprAPI.geneticSystem.getDefinition("plants").orElse(null);
		}
	}
}
