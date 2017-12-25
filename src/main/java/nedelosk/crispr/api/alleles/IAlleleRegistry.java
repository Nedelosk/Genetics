package nedelosk.crispr.api.alleles;

import nedelosk.crispr.api.gene.IGeneKey;

public interface IAlleleRegistry<V> {

	IAlleleRegistry<V> addAllele(IAlleleKey key, String unlocalizedName, V value, boolean dominant);

	default IAlleleRegistry<V> addAllele(IAlleleData<V> data) {
		return addAllele(data.getKey(), data.getName(), data.getValue(), data.isDominant());
	}

	IAlleleRegistry<V> setDefaultAllele(IAlleleKey key);

	IAlleleRegistry<V> addKey(IGeneKey key);
}
