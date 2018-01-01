package nedelosk.crispr.api.gene;

import nedelosk.crispr.api.definition.IGeneticDefinition;

public interface IGeneType {
	int getIndex();

	IGeneticDefinition getDefinition();
}
