package genetics.api.gene;

import genetics.api.definition.IGeneticDefinition;

public interface IGeneType {
	int getIndex();

	IGeneticDefinition getDefinition();
}
