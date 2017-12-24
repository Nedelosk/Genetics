package nedelosk.crispr.api.gene;

import nedelosk.crispr.api.IGeneticDefinition;
import nedelosk.crispr.api.IGeneticDefinitionBuilder;
import nedelosk.crispr.api.individual.IIndividual;
import nedelosk.crispr.api.individual.IIndividualFactory;

public interface IGeneRegistry {

	/**
	 * @param valueClass Get class which all values of the alleles of this gene must interface
	 */
	<V> IGeneBuilder<V> createGene(Class<? extends V> valueClass);

	void registerGene(IGene gene, IGeneKey... keys);

	IKaryotypeBuilder createKaryotype();

	<I extends IIndividual> IGeneticDefinitionBuilder<I> createDefinition(String name, IKaryotype karyotype, IIndividualFactory<I> factory);

	void registerDefinition(IGeneticDefinition definition);

}
