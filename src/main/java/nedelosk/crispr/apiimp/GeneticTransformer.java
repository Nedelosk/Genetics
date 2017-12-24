package nedelosk.crispr.apiimp;

import nedelosk.crispr.api.IGeneticTransformer;
import nedelosk.crispr.api.alleles.Allele;
import nedelosk.crispr.api.gene.IChromosome;
import nedelosk.crispr.api.gene.IGenome;
import nedelosk.crispr.api.individual.IGeneticIndividual;

public class GeneticTransformer<I extends IGeneticIndividual> implements IGeneticTransformer<I> {

	@Override
	public I templateAsIndividual(Allele[] template) {
		return null;
	}

	@Override
	public I templateAsIndividual(Allele[] templateActive, Allele[] templateInactive) {
		return null;
	}

	@Override
	public IChromosome[] templateAsChromosomes(Allele[] template) {
		return new IChromosome[0];
	}

	@Override
	public IChromosome[] templateAsChromosomes(Allele[] templateActive, Allele[] templateInactive) {
		return new IChromosome[0];
	}

	@Override
	public IGenome templateAsGenome(Allele[] template) {
		return null;
	}

	@Override
	public IGenome templateAsGenome(Allele[] templateActive, Allele[] templateInactive) {
		return null;
	}
}
