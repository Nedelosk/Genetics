package genetics.api.alleles;

import genetics.api.registry.IAlleleRegistry;

/**
 * The IAlleleData is a help interface that provides all information that is required to register a allele at the
 * {@link IAlleleRegistry} using {@link IAlleleRegistry#registerAllele(Object, boolean, IAlleleKey...)}.
 *
 * @param <V> The type of the value that this constant provides.
 */
public interface IAlleleData<V> {

	V getValue();

	boolean isDominant();

	IAlleleKey getKey();
}
