package genetics.plugins.vanilla;

import genetics.api.GeneticPlugin;
import genetics.api.IGeneticPlugin;
import genetics.api.alleles.DefaultAlleles;
import genetics.api.alleles.IAlleleRegistry;

import genetics.Genetics;

@GeneticPlugin(modId = Genetics.MOD_ID)
public class VanillaPlugin implements IGeneticPlugin {
	@Override
	public void registerAlleles(IAlleleRegistry registry) {
		for (DefaultAlleles.Integer integer : DefaultAlleles.Integer.values()) {
			registry.registerAllele("i", integer.ordinal() + "d", integer.ordinal(), true, integer);
		}
		registry.registerAllele("bool", Boolean.toString(true), true, false, DefaultAlleles.Boolean.TRUE);
		registry.registerAllele("bool", Boolean.toString(false), false, false, DefaultAlleles.Boolean.FALSE);
	}
}
