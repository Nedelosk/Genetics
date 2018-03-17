package genetics.api;

import genetics.api.alleles.IAlleleConstant;
import genetics.api.alleles.IAlleleKey;
import genetics.api.gene.IChromosomeType;
import genetics.api.gene.IGeneBuilder;

/**
 * The IRegistryHelper offers a functions to create early genes.
 * <p>
 * The only instance of this is passed is passed to your genetic plugin in {@link IGeneticPlugin#registerSimple(IRegistryHelper)}.
 */
public interface IRegistryHelper {
	/**
	 * Creates a allele for every {@link IAlleleConstant} that the given array contains and
	 * creates later a {@link IGeneBuilder} that has the {@link IChromosomeType} of the given type, the given name and
	 * contains every {@link IAlleleKey} of the {@link IAlleleConstant} array.
	 *
	 * @param name      The name of the gene
	 * @param type      The type of the gene
	 * @param constants A array that contains the information that is needed to create the alleles that the gene should
	 *                  contain.
	 */
	void addGene(String name, IChromosomeType type, IAlleleConstant[] constants);

}
