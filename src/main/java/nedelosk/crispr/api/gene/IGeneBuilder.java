package nedelosk.crispr.api.gene;

import nedelosk.crispr.api.alleles.Allele;

public interface IGeneBuilder<V> {

	Allele<V> registerAllele(String unlocalizedName, V value, boolean dominant);

	void setDefaultAllele(Allele<V> allele);

	IGene<V> register(IGeneKey... keys);
}
