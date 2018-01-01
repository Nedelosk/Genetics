package nedelosk.crispr.api;

import nedelosk.crispr.api.gene.IGeneticStat;
import nedelosk.crispr.api.gene.IKaryotype;
import nedelosk.crispr.api.individual.IGenome;
import nedelosk.crispr.api.individual.IIndividual;
import nedelosk.crispr.api.translators.IGeneticTranslator;
import nedelosk.crispr.apiimp.IGeneticTypes;

public interface IGeneticDefinition<I extends IIndividual, R extends IGeneticRoot> extends IGeneticTranslator<I>, IGeneticTransformer<I>, IGeneticTypes<I>, IKaryotype, ITemplateContainer {

	String getName();

	I getDefaultMember();

	IGeneticStat createStat(IGenome genome);

	R root();
}
