package genetics.api.alleles;

/**
 * Handler for events that occur in IAlleleRegistry, such as registering alleles, etc. Useful for handling
 * plugin specific behavior (i.e. creating a list of all bee species etc.)
 */
public interface IAlleleHandler {
	/**
	 * Called after a allele was registered.
	 *
	 * @param allele The registered allele.
	 */
	void onRegisterAllele(IAllele<?> allele);

	/**
	 * Called after keys were added to a allele.
	 *
	 * @param allele The allele to that the keys were added.
	 * @param keys   The added keys.
	 */
	void onAddKeys(IAllele<?> allele, IAlleleKey... keys);
}
