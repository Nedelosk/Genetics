package genetics.api.alleles;

import genetics.api.individual.IChromosomeType;

/**
 * The IAlleleData is a help interface that provides all information that is required to register a allele at the
 * {@link IAlleleRegistry} using {@link IAlleleRegistry#registerAllele(String, String, Object, boolean, IChromosomeType...)}.
 *
 * @param <V> The type of the value that this constant provides.
 */
public interface IAlleleData<V> {

	/**
	 * The value of the allele.
	 */
	V getValue();

	/**
	 * The dominance of the allele.
	 */
	boolean isDominant();

	/**
	 * @return Zhe category of the allele is used for custom localisation and the registration name of the allele.
	 */
	String getCategory();

	/**
	 * @return The name that is used for the unlocalized name and the registration name of the allele.
	 */
	String getName();
}
