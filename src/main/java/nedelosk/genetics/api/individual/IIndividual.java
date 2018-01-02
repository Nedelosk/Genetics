package nedelosk.genetics.api.individual;

import nedelosk.genetics.api.definition.IGeneticDefinition;

public interface IIndividual {
	IGeneticDefinition getDefinition();

	IGenome getGenome();

	IIndividual copy();
}
