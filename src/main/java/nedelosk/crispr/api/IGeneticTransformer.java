package nedelosk.crispr.api;

import javax.annotation.Nullable;

import nedelosk.crispr.api.alleles.Allele;
import nedelosk.crispr.api.gene.IChromosome;
import nedelosk.crispr.api.gene.IGenome;
import nedelosk.crispr.api.individual.IIndividual;

public interface IGeneticTransformer<I extends IIndividual> {
	default I templateAsIndividual(Allele[] template) {
		return templateAsIndividual(template, null);
	}

	I templateAsIndividual(Allele[] templateActive, @Nullable Allele[] templateInactive);

	default IChromosome[] templateAsChromosomes(Allele[] template) {
		return templateAsChromosomes(template, null);
	}

	IChromosome[] templateAsChromosomes(Allele[] templateActive, @Nullable Allele[] templateInactive);

	default IGenome templateAsGenome(Allele[] template) {
		return templateAsGenome(template, null);
	}

	IGenome templateAsGenome(Allele[] templateActive, @Nullable Allele[] templateInactive);
}
