package genetics.api.alleles;

/**
 * @author Alex Binnie
 * <p>
 * Handler for events that occur in IAlleleRegistry, such as registering alleles, etc. Useful for handling
 * plugin specific behavior (i.e. creating a list of all bee species etc.)
 */
public interface IAlleleHandler {
	void onRegisterAllele(IAllele<?> allele);

	void onAddKeys(IAllele<?> allele, IAlleleKey... keys);
}
