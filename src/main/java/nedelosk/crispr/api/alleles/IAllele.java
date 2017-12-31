package nedelosk.crispr.api.alleles;

import net.minecraftforge.registries.IForgeRegistryEntry;

public interface IAllele<V> extends IForgeRegistryEntry<IAllele<?>> {

	V getValue();

	/**
	 * @return true if the allele is dominant, false otherwise.
	 */
	boolean isDominant();

	/**
	 * @return Localized short, human-readable identifier used in tooltips and beealyzer.
	 * @apiNote This can't be named "getName" or it can conflict during obfuscation. https://github.com/md-5/SpecialSource/issues/12
	 */
	String getLocalizedName();

	/**
	 * @return The unlocalized identifier
	 */
	String getUnlocalizedName();
}
