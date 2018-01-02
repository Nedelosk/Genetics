package genetics.api.definition;

import genetics.api.gene.IKaryotype;
import genetics.api.individual.IIndividual;
import genetics.api.translators.IGeneticTranslator;

public interface IGeneticDefinition<I extends IIndividual, R extends IGeneticRoot> extends IGeneticTranslator<I>, IGeneticTypes<I>, IKaryotype, ITemplateContainer {

	String getName();

	R root();
}
