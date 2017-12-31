package nedelosk.crispr.api.gene;

public interface IGeneRegistry {
	/**
	 * @param valueClass Get class which all values of the alleles of this gene must interface
	 * @param name       The name of the given gene. Used for the localized name and the short localized name.
	 */
	<V> IGeneBuilder<V> createRegistry(Class<? extends V> valueClass, String name);

	IKaryotypeBuilder createKaryotype(IGeneType templateType);


}
