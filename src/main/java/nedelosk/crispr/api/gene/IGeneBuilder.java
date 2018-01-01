package nedelosk.crispr.api.gene;

import nedelosk.crispr.api.alleles.IAlleleKey;

public interface IGeneBuilder {

	IGeneBuilder addAllele(IAlleleKey key, String unlocalizedName);

	IGeneBuilder setDefaultAllele(IAlleleKey key);

	IGeneBuilder addType(IGeneType type);
}
