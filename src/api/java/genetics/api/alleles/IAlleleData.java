package genetics.api.alleles;

import genetics.api.individual.IChromosomeType;

/**
 * The IAlleleData is a help interface that provides all information that is required to register a allele at the
 * {@link IAlleleRegistry} using {@link IAlleleRegistry#registerAllele(IAlleleData, IChromosomeType...)}.
 * <p>
 * If you implement this interface on an enum you can register every enum value with
 * {@link IAlleleRegistry#registerAlleles(IAlleleData[], IChromosomeType...)}.
 *
 * @param <V> The type of the value that this constant provides.
 */
public interface IAlleleData<V> {

	/**
	 * @return The value of the allele.
	 */
	V getValue();

	/**
	 * @return The dominance of the allele.
	 */
	boolean isDominant();

	/**
	 * @return The category of the allele is used for custom localisation and the registration name of the allele.
	 */
	String getCategory();

	/**
	 * @return The name that is used for the unlocalized name and the registration name of the allele.
	 */
	String getName();
}
