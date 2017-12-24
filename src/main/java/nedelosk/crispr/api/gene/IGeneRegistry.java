package nedelosk.crispr.api.gene;

import nedelosk.crispr.api.IGeneticDefinition;
import nedelosk.crispr.api.IGeneticDefinitionBuilder;

public interface IGeneRegistry {

	/**
	 * @param valueClass Get class which all values of the alleles of this gene must interface
	 */
	<V> IGeneBuilder<V> createGene(Class<? extends V> valueClass);

	void registerGene(IGene gene, IGeneKey... keys);

	IKaryotypeBuilder createKaryotype();

	IGeneticDefinitionBuilder createDefinition(String name, IKaryotype karyotype);

	void registerDefinition(IGeneticDefinition definition);

}
