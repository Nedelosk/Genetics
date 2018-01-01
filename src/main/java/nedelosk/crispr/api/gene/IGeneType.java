package nedelosk.crispr.api.gene;

import nedelosk.crispr.api.IGeneticDefinition;

public interface IGeneType {
	int getIndex();

	IGeneticDefinition getDefinition();
}
