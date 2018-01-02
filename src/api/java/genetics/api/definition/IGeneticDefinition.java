package genetics.api.definition;

import genetics.api.gene.IKaryotype;
import genetics.api.individual.IIndividual;
import genetics.api.translators.IGeneticTranslator;

public interface IGeneticDefinition<I extends IIndividual, R extends IGeneticRoot> extends IGeneticTranslator<I>, IGeneticTypes<I>, IKaryotype, ITemplateContainer {

	/**
	 * @return The string based unique identifier of this definition.
	 */
	String getUID();

	R root();
}
