package nedelosk.genetics.api.gene;

import nedelosk.genetics.api.alleles.IAlleleKey;

public interface IGeneBuilder {

	IGeneBuilder addAllele(IAlleleKey key, String unlocalizedName);

	IGeneBuilder setDefaultAllele(IAlleleKey key);

	IGeneBuilder addType(IGeneType type);
}
