package genetics.api.alleles;

import genetics.api.IRegistryHelper;
import genetics.api.individual.IChromosomeType;

/**
 * The IAlleleConstant is a help interface that provides all information that is required to register a allele at the {@link IAlleleRegistry}
 * as well as to create a gene at the {@link IRegistryHelper} using
 * {@link IRegistryHelper#addAlleles(IChromosomeType, IAlleleConstant[])}.
 *
 * @param <V> The type of the value that this constant provides.
 */
public interface IAlleleConstant<V> extends IAlleleData<V> {
	/**
	 * @return True if the key of this constant is the default key of the {@link IGeneBuilder} that is used to
	 * create the gene.
	 */
	boolean isDefault();
}
