package genetics.api.individual;

import genetics.api.definition.IGeneticDefinition;

public interface IIndividual {
	IGeneticDefinition getDefinition();

	IGenome getGenome();

	IGenome getMate();

	IIndividual copy();
}
