package nedelosk.genetics.api.gene;

import nedelosk.genetics.api.alleles.IAllele;
import nedelosk.genetics.api.definition.IGeneticDefinition;

public interface IGeneTemplate {

	IAllele getAllele();

	IGeneType getType();

	IGeneticDefinition getDescription();
}
