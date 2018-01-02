package genetics.api.alleles;

public interface IAlleleHandler {
	void onRegisterAllele(IAllele<?> allele);

	void onAddKeys(IAllele<?> allele, IAlleleKey... keys);
}
