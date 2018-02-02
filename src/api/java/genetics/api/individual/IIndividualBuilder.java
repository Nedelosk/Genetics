package genetics.api.individual;

import genetics.api.alleles.IAllele;
import genetics.api.alleles.IAlleleKey;
import genetics.api.definition.IIndividualDefinition;
import genetics.api.gene.IGeneType;

public interface IIndividualBuilder<I extends IIndividual> {

	IIndividualDefinition<I, ?> getDefinition();

	void setAllele(IGeneType type, IAllele<?> allele, boolean active);

	void setAllele(IGeneType type, IAlleleKey key, boolean active);

	I build();
}
