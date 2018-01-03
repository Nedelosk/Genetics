package genetics.api;

import genetics.api.alleles.IAlleleConstant;
import genetics.api.gene.IGeneType;

public interface IRegistryHelper {
	void addSimpleGene(String name, IGeneType geneType, IAlleleConstant[] constants);

}
