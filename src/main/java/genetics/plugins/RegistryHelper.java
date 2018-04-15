package genetics.plugins;

import java.util.HashSet;
import java.util.Set;

import genetics.api.IRegistryHelper;
import genetics.api.alleles.IAlleleConstant;
import genetics.api.gene.IChromosomeType;
import genetics.api.gene.IGeneBuilder;
import genetics.api.gene.IGeneFactory;
import genetics.api.registry.IAlleleRegistry;

public enum RegistryHelper implements IRegistryHelper {
	INSTANCE;
	private Set<GeneData> genes = new HashSet<>();

	@Override
	public void addGene(String name, IChromosomeType type, IAlleleConstant[] constants) {
		genes.add(new GeneData(name, type, constants));
	}

	void onRegisterAlleles(IAlleleRegistry registry) {
		for (GeneData gene : genes) {
			for (IAlleleConstant data : gene.constants) {
				registry.registerAllele(data);
			}
		}
	}

	void onRegister(IGeneFactory registry) {
		for (GeneData gene : genes) {
			IGeneBuilder geneBuilder = registry.addGene(gene.name).addType(gene.type);
			for (IAlleleConstant constant : gene.constants) {
				if (constant.isDefault()) {
					geneBuilder.setDefaultAllele(constant.getKey());
				}
				geneBuilder.addAllele(constant.getKey(), "allele." + gene.name + "." + constant.getName() + ".name");
			}
		}
	}

	private static class GeneData {
		private final String name;
		private final IChromosomeType type;
		private final IAlleleConstant[] constants;

		private GeneData(String name, IChromosomeType type, IAlleleConstant[] constants) {
			this.name = name;
			this.type = type;
			this.constants = constants;
		}
	}
}
