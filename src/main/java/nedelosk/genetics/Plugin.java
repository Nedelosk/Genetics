package nedelosk.genetics;

import nedelosk.genetics.api.GeneticPlugin;
import nedelosk.genetics.api.GeneticsAPI;
import nedelosk.genetics.api.IGeneticFactory;
import nedelosk.genetics.api.IGeneticPlugin;
import nedelosk.genetics.api.alleles.IAlleleKey;
import nedelosk.genetics.api.definition.IGeneticDefinition;
import nedelosk.genetics.api.definition.IGeneticDefinitionBuilder;
import nedelosk.genetics.api.gene.IGeneType;
import nedelosk.genetics.api.gene.IKaryotype;
import nedelosk.genetics.api.registry.IAlleleRegistry;
import nedelosk.genetics.api.registry.IGeneticRegistry;
import nedelosk.genetics.api.registry.IGeneticSystem;

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
			return GeneticsAPI.geneticSystem.getDefinition("plants").orElse(null);
		}
	}
}
