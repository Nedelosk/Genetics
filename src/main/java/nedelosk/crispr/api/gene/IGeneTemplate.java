package nedelosk.crispr.api.gene;

import nedelosk.crispr.api.alleles.IAllele;
import nedelosk.crispr.api.definition.IGeneticDefinition;

public interface IGeneTemplate {

	IAllele getAllele();

	IGeneType getType();

	IGeneticDefinition getDescription();
}
