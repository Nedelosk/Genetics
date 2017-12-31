package nedelosk.crispr.api;

import nedelosk.crispr.api.gene.IGeneticStat;
import nedelosk.crispr.api.gene.IKaryotype;
import nedelosk.crispr.api.individual.IGenome;
import nedelosk.crispr.api.individual.IIndividual;
import nedelosk.crispr.api.translators.IGeneticTranslator;
import nedelosk.crispr.apiimp.IGeneticTypes;

public interface IGeneticDefinition<I extends IIndividual> extends IGeneticTranslator<I>, IGeneticTransformer<I>, IGeneticTypes<I> {

	String getName();

	I getDefaultMember();

	IGeneticStat createStat(IGenome genome);

	IGeneticRoot root();

	IKaryotype getKaryotype();
}
