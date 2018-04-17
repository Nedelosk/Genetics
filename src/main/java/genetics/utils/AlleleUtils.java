package genetics.utils;

import javax.annotation.Nullable;

import genetics.api.alleles.IAllele;
import genetics.api.alleles.IAlleleValue;

public class AlleleUtils {

	private AlleleUtils() {
	}

	/**
	 * @return The value of the allele if the allele is an instance of {@link IAlleleValue} and not null.
	 * Otherwise it returns the given fallback object.
	 */
	@Nullable
	public static <V> V getAlleleValue(IAllele allele, Class<? extends V> valueClass, @Nullable V fallback) {
		if (!(allele instanceof IAlleleValue)) {
			return fallback;
		}
		IAlleleValue alleleValue = (IAlleleValue) allele;
		Object value = alleleValue.getValue();
		if (valueClass.isInstance(valueClass)) {
			return valueClass.cast(value);
		}
		return fallback;
	}
}
