package nedelosk.crispr.api.alleles;

import nedelosk.crispr.api.IGeneticPlugin;

public interface IAlleleCategory<V> {

	IAlleleCategory<V> registerAllele(V value, boolean dominant, IAlleleKey... keys);

	/**
	 * Called after {@link IGeneticPlugin#registerAlleles(IAlleleRegistry)} was called.
	 */
	void registerAlleles();

	String getUID();
}
