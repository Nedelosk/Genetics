package genetics.api.alleles;

import org.apache.commons.lang3.text.WordUtils;

/**
 * Alleles that have a category with several values inherit from this class.
 * For example, temperature tolerances or speeds are categories with several values.
 * <p>
 * This class helps localization by allowing specific names like
 * forestry.allele.speed.fast
 * and can fall back on generic names like
 * forestry.allele.fast
 */
public class AlleleCategorized<V> extends AlleleValue<V> {

	public AlleleCategorized(String modId, String category, String valueName, V value, boolean dominant) {
		super(getUnlocalizedName(modId, category, valueName), dominant, value);
		setRegistryName(createRegistryName(modId, category, valueName));
	}

	private static String createRegistryName(String modId, String category, String valueName) {
		return modId + ':' + category + WordUtils.capitalize(valueName);
	}

	private static String getUnlocalizedName(String modId, String category, String valueName) {
		String customName = modId + '.' + "allele." + category + '.' + valueName;
		if (net.minecraft.util.text.translation.I18n.canTranslate(customName)) {
			return customName;
		} else {
			return modId + '.' + "allele." + valueName;
		}
	}
}
