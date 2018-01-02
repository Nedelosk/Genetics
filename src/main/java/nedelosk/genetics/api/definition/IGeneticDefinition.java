package nedelosk.genetics.api.definition;

import nedelosk.genetics.api.gene.IKaryotype;
import nedelosk.genetics.api.individual.IIndividual;
import nedelosk.genetics.api.translators.IGeneticTranslator;

public interface IGeneticDefinition<I extends IIndividual, R extends IGeneticRoot> extends IGeneticTranslator<I>, IGeneticTypes<I>, IKaryotype, ITemplateContainer {

	String getName();

	R root();
}
