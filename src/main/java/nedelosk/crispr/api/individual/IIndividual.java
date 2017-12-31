package nedelosk.crispr.api.individual;

import nedelosk.crispr.api.IGeneticDefinition;

public interface IIndividual {
	IGeneticDefinition getDefinition();

	IGenome getGenome();

	IIndividual copy();
}
