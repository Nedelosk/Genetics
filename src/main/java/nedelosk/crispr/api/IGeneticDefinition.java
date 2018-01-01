package nedelosk.crispr.api;

import nedelosk.crispr.api.gene.IKaryotype;
import nedelosk.crispr.api.individual.IIndividual;
import nedelosk.crispr.api.translators.IGeneticTranslator;
import nedelosk.crispr.apiimp.IGeneticTypes;

public interface IGeneticDefinition<I extends IIndividual, R extends IGeneticRoot> extends IGeneticTranslator<I>, IGeneticTypes<I>, IKaryotype, ITemplateContainer {

	String getName();

	R root();
}
