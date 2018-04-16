package genetics.api.alleles;

/**
 * @param <V> the type of value that this allele contains.
 */
public interface IAlleleValue<V> extends IAllele {
	/**
	 * @return the value that this allele contains.
	 */
	V getValue();
}
