package genetics.plugins;

import java.util.HashSet;
import java.util.Set;

import genetics.api.IRegistryHelper;
import genetics.api.alleles.IAlleleConstant;
import genetics.api.gene.IGeneBuilder;
import genetics.api.gene.IGeneType;
import genetics.api.registry.IAlleleRegistry;
import genetics.api.registry.IGeneticRegistry;

public enum RegistryHelper implements IRegistryHelper {
	INSTANCE;
	private Set<SimpleGene> genes = new HashSet<>();

	@Override
	public void addSimpleGene(String name, IGeneType geneType, IAlleleConstant[] constants) {
		genes.add(new SimpleGene(name, geneType, constants));
	}

	void onRegisterAlleles(IAlleleRegistry registry) {
		for (SimpleGene gene : genes) {
			for (IAlleleConstant data : gene.constants) {
				registry.registerAllele(data, data.getKey());
			}
		}
	}

	void onRegister(IGeneticRegistry registry) {
		for (SimpleGene gene : genes) {
			IGeneBuilder geneBuilder = registry.addGene(gene.name).addType(gene.geneType);
			for (IAlleleConstant constant : gene.constants) {
				if (constant.isDefault()) {
					geneBuilder.setDefaultAllele(constant.getKey());
				}
				geneBuilder.addAllele(constant.getKey(), "allele." + gene.name + "." + constant.getName() + ".name");
			}
		}
	}

	private static class SimpleGene {
		private final String name;
		private final IGeneType geneType;
		private final IAlleleConstant[] constants;

		private SimpleGene(String name, IGeneType geneType, IAlleleConstant[] constants) {
			this.name = name;
			this.geneType = geneType;
			this.constants = constants;
		}
	}
}
