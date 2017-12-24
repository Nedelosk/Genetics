package nedelosk.crispr.api.gene;

public interface IGeneRegistry {

	/**
	 * @param valueClass Get class which all values of the alleles of this gene must interface
	 */
	<V> IGeneBuilder<V> createGene(Class<? extends V> valueClass);
}
