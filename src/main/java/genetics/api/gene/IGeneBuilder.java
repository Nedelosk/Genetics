package genetics.api.gene;

import genetics.api.alleles.IAlleleKey;

public interface IGeneBuilder {

	IGeneBuilder addAllele(IAlleleKey key, String unlocalizedName);

	IGeneBuilder setDefaultAllele(IAlleleKey key);

	IGeneBuilder addType(IGeneType type);
}
