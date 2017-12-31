package nedelosk.crispr.apiimp;

import nedelosk.crispr.api.IGeneticTransformer;
import nedelosk.crispr.api.alleles.IAllele;
import nedelosk.crispr.api.individual.IChromosome;
import nedelosk.crispr.api.individual.IGenome;
import nedelosk.crispr.api.individual.IIndividual;

public class GeneticTransformer<I extends IIndividual> implements IGeneticTransformer<I> {

	@Override
	public I templateAsIndividual(IAllele[] template) {
		return null;
	}

	@Override
	public I templateAsIndividual(IAllele[] templateActive, IAllele[] templateInactive) {
		return null;
	}

	@Override
	public IChromosome[] templateAsChromosomes(IAllele[] template) {
		return new IChromosome[0];
	}

	@Override
	public IChromosome[] templateAsChromosomes(IAllele[] templateActive, IAllele[] templateInactive) {
		return new IChromosome[0];
	}

	@Override
	public IGenome templateAsGenome(IAllele[] template) {
		return null;
	}

	@Override
	public IGenome templateAsGenome(IAllele[] templateActive, IAllele[] templateInactive) {
		return null;
	}
}
