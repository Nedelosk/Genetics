package nedelosk.crispr.api;

import nedelosk.crispr.api.gene.IGeneticStat;
import nedelosk.crispr.api.gene.IGenome;
import nedelosk.crispr.api.gene.IKaryotype;
import nedelosk.crispr.api.individual.IGeneticIndividual;
import nedelosk.crispr.api.translators.IGeneticTranslator;
import nedelosk.crispr.apiimp.IGeneticTypes;

public interface IGeneticDefinition<I extends IGeneticIndividual> {

	String getName();

	I getDefaultMember();

	IGeneticStat createStat(IGenome genome);

	IKaryotype karyotype();

	IGeneticTypes<I> types();

	IGeneticTransformer<I> transformer();

	IGeneticTranslator<I> translator();
}
