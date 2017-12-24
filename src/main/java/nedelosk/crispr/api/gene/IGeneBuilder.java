package nedelosk.crispr.api.gene;

import nedelosk.crispr.api.alleles.Allele;

public interface IGeneBuilder<V> {

	Allele<V> registerAllele(String unlocalizedName, V value, boolean dominant);

	void setDefaultAllele(Allele<V> allele);

	/**
	 * @param geneName The name of the given gene. Used for the localized name and the short localized name.
	 */
	void setName(String geneName);

	IGene register();
}
