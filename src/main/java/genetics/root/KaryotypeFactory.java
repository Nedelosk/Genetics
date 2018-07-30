package genetics.root;

import java.util.function.Function;

import genetics.api.alleles.IAlleleTemplate;
import genetics.api.alleles.IAlleleTemplateBuilder;
import genetics.api.individual.IChromosomeType;
import genetics.api.individual.IKaryotype;
import genetics.api.individual.IKaryotypeBuilder;
import genetics.api.individual.IKaryotypeFactory;

import genetics.individual.Karyotype;

public class KaryotypeFactory implements IKaryotypeFactory {

	@Override
	public IKaryotypeBuilder createKaryotype(String uid, IChromosomeType templateType, Function<IAlleleTemplateBuilder, IAlleleTemplate> speciesType) {
		return new Karyotype.Builder(uid, templateType, speciesType);
	}

	@Override
	public <T extends Enum<T> & IChromosomeType> IKaryotype createKaryotype(String uid, Class<? extends T> enumClass, Function<IAlleleTemplateBuilder, IAlleleTemplate> defaultTemplate) {
		T[] types = enumClass.getEnumConstants();
		if (types.length <= 0) {
			throw new IllegalArgumentException("The given enum class must contain at least one enum constant.");
		}
		IKaryotypeBuilder builder = new Karyotype.Builder(uid, types[0], defaultTemplate);
		for (int i = 1; i < types.length; i++) {
			IChromosomeType type = types[i];
			builder.add(type);
		}
		return builder.build();
	}
}
