package genetics;

import genetics.api.GeneticPlugin;
import genetics.api.GeneticsAPI;
import genetics.api.IGeneticFactory;
import genetics.api.IGeneticPlugin;
import genetics.api.alleles.IAlleleKey;
import genetics.api.definition.IGeneticDefinition;
import genetics.api.definition.IGeneticDefinitionBuilder;
import genetics.api.gene.IGeneType;
import genetics.api.gene.IKaryotype;
import genetics.api.registry.IAlleleRegistry;
import genetics.api.registry.IGeneticRegistry;
import genetics.api.registry.IGeneticSystem;

@GeneticPlugin
public class Plugin implements IGeneticPlugin {
	private static IGeneticDefinition definition;

	@Override
	public void registerAlleles(IAlleleRegistry registry) {
		registry.registerAllele(0, false, "cultivation:fertility", AlleleKey.FERTILITY_0);
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
