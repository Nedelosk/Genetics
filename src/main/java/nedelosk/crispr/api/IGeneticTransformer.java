package nedelosk.crispr.api;

import javax.annotation.Nullable;

import nedelosk.crispr.api.alleles.IAllele;
import nedelosk.crispr.api.individual.IChromosome;
import nedelosk.crispr.api.individual.IGenome;
import nedelosk.crispr.api.individual.IIndividual;

public interface IGeneticTransformer<I extends IIndividual> {
	default I templateAsIndividual(IAllele[] template) {
		return templateAsIndividual(template, null);
	}

	I templateAsIndividual(IAllele[] templateActive, @Nullable IAllele[] templateInactive);

	default IChromosome[] templateAsChromosomes(IAllele[] template) {
		return templateAsChromosomes(template, null);
	}

	IChromosome[] templateAsChromosomes(IAllele[] templateActive, @Nullable IAllele[] templateInactive);

	default IGenome templateAsGenome(IAllele[] template) {
		return templateAsGenome(template, null);
	}

	IGenome templateAsGenome(IAllele[] templateActive, @Nullable IAllele[] templateInactive);
}
