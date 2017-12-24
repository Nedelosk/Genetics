package nedelosk.crispr.api.individual;

import nedelosk.crispr.api.IGeneticDefinition;
import nedelosk.crispr.api.gene.IGenome;

public interface IIndividual {
	IGeneticDefinition getDefinition();

	IGenome getGenome();

	IIndividual copy();
}
