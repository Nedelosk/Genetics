package genetics.api.alleles;

/**
 * The IAlleleData is a help interface that provides all information that is required to register a allele at the
 * {@link IAlleleRegistry} using {@link IAlleleRegistry#registerAllele(Object, boolean, IAlleleKey...)}.
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
	 * The key of the allele.
	 */
	IAlleleKey getKey();
}
