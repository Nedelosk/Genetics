package cultivation.indivudual;

import javax.annotation.Nullable;
import java.util.Locale;

import genetics.api.individual.IChromosomeType;
import genetics.api.root.IIndividualRoot;

import cultivation.CultivationPlugin;

public enum PlantChromosomes implements IChromosomeType {
	SPECIES(null);

	@Nullable
	private final Class<?> valueClass;

	PlantChromosomes(@Nullable Class<?> valueClass) {
		this.valueClass = valueClass;
	}

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

	@Nullable
	@Override
	public Class<?> getValueClass() {
		return valueClass;
	}
}
