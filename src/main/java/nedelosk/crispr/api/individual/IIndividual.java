package nedelosk.crispr.api.individual;

import nedelosk.crispr.api.definition.IGeneticDefinition;

public interface IIndividual {
	IGeneticDefinition getDefinition();

	IGenome getGenome();

	IIndividual copy();
}
