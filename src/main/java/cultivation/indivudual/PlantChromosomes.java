package cultivation.indivudual;

import java.util.Locale;

import genetics.api.alleles.IAllele;
import genetics.api.individual.IChromosomeType;
import genetics.api.root.IIndividualRoot;

import cultivation.CultivationPlugin;

public enum PlantChromosomes implements IChromosomeType {
	SPECIES;

	@Override
	public int getIndex() {
		return ordinal();
	}

	@Override
	public IIndividualRoot getRoot() {
		return CultivationPlugin.DEFINITION.get();
	}

	@Override
	public String getName() {
		return name().toLowerCase(Locale.ENGLISH);
	}

	@Override
	public boolean isValid(IAllele allele) {
		return true;
	}
}
