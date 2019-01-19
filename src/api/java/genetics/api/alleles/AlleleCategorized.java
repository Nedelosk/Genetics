package genetics.api.alleles;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;


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

	private static ResourceLocation createRegistryName(String modId, String category, String valueName) {
		return new ResourceLocation(modId, category + "_" + valueName);
	}

	private static String getUnlocalizedName(String modId, String category, String valueName) {
		String customName = modId + '.' + "allele." + category + '.' + valueName;
		if (I18n.hasKey(customName)) {
			return customName;
		} else {
			return modId + '.' + "allele." + valueName;
		}
	}
}
