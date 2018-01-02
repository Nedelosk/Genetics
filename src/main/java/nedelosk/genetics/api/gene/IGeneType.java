package nedelosk.genetics.api.gene;

import nedelosk.genetics.api.definition.IGeneticDefinition;

public interface IGeneType {
	int getIndex();

	IGeneticDefinition getDefinition();
}
