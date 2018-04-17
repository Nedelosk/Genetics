package genetics.api.alleles;

/**
 * Contains the {@link IAlleleKey}s of every allele that the genetics mod itself adds.
 */
public class DefaultAlleles {
	/**
	 * Allele keys for alleles that represent a integer from 1 to 10.
	 */
	public enum Integer implements IAlleleKey {
		INT_1,
		INT_2,
		INT_3,
		INT_4,
		INT_5,
		INT_6,
		INT_7,
		INT_8,
		INT_9,
		INT_10
	}

	/**
	 * Allele keys for alleles that represent one of the two boolean values.
	 */
	public enum Boolean implements IAlleleKey {
		TRUE,
		FALSE
	}
}
