package genetics.api.alleles;

import genetics.api.IRegistryHelper;
import genetics.api.gene.IChromosomeType;
import genetics.api.gene.IGeneBuilder;

/**
 * The IAlleleConstant is a help interface that provides all information that is required to register a allele at the {@link IAlleleRegistry}
 * as well as to create a gene at the {@link IRegistryHelper} using
 * {@link IRegistryHelper#addGene(String, IChromosomeType, IAlleleConstant[])}.
 *
 * @param <V> The type of the value that this constant provides.
 */
public interface IAlleleConstant<V> extends IAlleleData<V> {
	/**
	 * @return True if the key of this constant is the default key of the {@link IGeneBuilder} that is used to
	 * create the gene.
	 */
	boolean isDefault();

	/**
	 * @return The name that is used for the unlocalized name of the allele at the {@link IGeneBuilder} to add the
	 * allele key with {@link IGeneBuilder#addAllele(IAlleleKey, String)}.
	 */
	String getName();
}
