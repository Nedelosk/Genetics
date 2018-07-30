package cultivation.indivudual;

import java.util.Locale;

import genetics.api.organism.IOrganismType;

public enum PlantType implements IOrganismType {
	SEED, PLANT;

	private final String name;

	PlantType() {
		this.name = name().toLowerCase(Locale.ENGLISH);
	}

	@Override
	public String getName() {
		return name;
	}
}
