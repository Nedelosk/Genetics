package nedelosk.crispr.api.gene;

import java.util.function.Function;

import nedelosk.crispr.api.IGeneticDefinition;
import nedelosk.crispr.api.IGeneticDefinitionBuilder;
import nedelosk.crispr.api.IGeneticRoot;
import nedelosk.crispr.api.individual.IIndividual;

public interface IGeneRegistry {

	/**
	 * @param valueClass Get class which all values of the alleles of this gene must interface
	 * @param name The name of the given gene. Used for the localized name and the short localized name.
	 */
	<V> IGeneBuilder<V> createGene(Class<? extends V> valueClass, String name);

	void registerGene(IGene gene, IGeneKey... keys);

	IKaryotypeBuilder createKaryotype();

	<I extends IIndividual> IGeneticDefinitionBuilder<I> createDefinition(String name, IKaryotype karyotype, Function<IGeneticDefinition<I>, IGeneticRoot<I>> rootFactory);

	void registerDefinition(IGeneticDefinition definition);

}
