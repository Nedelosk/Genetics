package nedelosk.crispr.api.gene;

import nedelosk.crispr.api.alleles.IAlleleKey;
import nedelosk.crispr.api.alleles.IAlleleNameFormatter;

public interface IGeneBuilder<V> {

	IGeneBuilder<V> addAllele(IAlleleKey key);

	IGeneBuilder<V> setDefaultAllele(IAlleleKey key);

	IGeneBuilder<V> setNameFormatter(IAlleleNameFormatter<V> formatter);

	IGeneBuilder<V> addType(IGeneType type);
}
