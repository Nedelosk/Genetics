package genetics.plugins;

import java.util.HashSet;
import java.util.Set;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;

import genetics.api.IRegistryHelper;
import genetics.api.alleles.IAllele;
import genetics.api.alleles.IAlleleConstant;
import genetics.api.alleles.IAlleleRegistry;
import genetics.api.individual.IChromosomeType;

public enum RegistryHelper implements IRegistryHelper {
	INSTANCE;
	private Set<AlleleData> alleleData = new HashSet<>();

	@Override
	public void addAlleles(IAlleleConstant[] constants, IChromosomeType... types) {
		alleleData.add(new AlleleData(PluginManager.getActiveContainer(), constants, types));
	}

	void onRegisterAlleles(IAlleleRegistry registry) {
		Loader loader = Loader.instance();
		for (AlleleData gene : alleleData) {
			loader.setActiveModContainer(gene.container);
			PluginManager.setActiveContainer(gene.container);
			for (int i = 0;i < gene.constants.length;i++) {
				IAlleleConstant data = gene.constants[i];
				gene.alleles[i] = registry.registerAllele(data, gene.types);
			}
		}
		loader.setActiveModContainer(null);
		PluginManager.setActiveContainer(null);
	}

	private static class AlleleData {
		private final ModContainer container;
		private final IChromosomeType[] types;
		private final IAlleleConstant[] constants;
		private final IAllele[] alleles;

		private AlleleData(ModContainer container, IAlleleConstant[] constants, IChromosomeType[] types) {
			this.container = container;
			this.types = types;
			this.constants = constants;
			this.alleles = new IAllele[constants.length];
		}
	}
}
