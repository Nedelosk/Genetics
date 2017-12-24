package nedelosk.crispr.api;

import nedelosk.crispr.api.gene.IGeneticStat;
import nedelosk.crispr.api.gene.IGenome;
import nedelosk.crispr.api.gene.IKaryotype;
import nedelosk.crispr.api.individual.IIndividual;
import nedelosk.crispr.api.individual.IIndividualFactory;
import nedelosk.crispr.api.translators.IGeneticTranslator;
import nedelosk.crispr.apiimp.IGeneticTypes;

public interface IGeneticDefinition<I extends IIndividual> {

	String getName();

	I getDefaultMember();

	IGeneticStat createStat(IGenome genome);

	IIndividualFactory factory();

	IKaryotype karyotype();

	IGeneticTypes<I> types();

	IGeneticTransformer<I> transformer();

	IGeneticTranslator<I> translator();
}