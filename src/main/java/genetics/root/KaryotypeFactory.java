package genetics.root;

import genetics.api.gene.IChromosomeType;
import genetics.api.gene.IKaryotype;
import genetics.api.gene.IKaryotypeBuilder;
import genetics.api.gene.IKaryotypeFactory;

import genetics.gene.Karyotype;

public class KaryotypeFactory implements IKaryotypeFactory {

	@Override
	public IKaryotypeBuilder createKaryotype(IChromosomeType templateType, String identifier) {
		return new Karyotype.Builder(templateType, identifier);
	}

	@Override
	public <T extends Enum<T> & IChromosomeType> IKaryotype createKaryotype(Class<? extends T> enumClass, String identifier) {
		T[] types = enumClass.getEnumConstants();
		if (types.length <= 0) {
			throw new IllegalArgumentException("The given enum class must contain at least one enum constant.");
		}
		IKaryotypeBuilder builder = new Karyotype.Builder(types[0], identifier);
		for (int i = 1; i < types.length; i++) {
			IChromosomeType type = types[i];
			builder.add(type);
		}
		return builder.build();
	}
}
