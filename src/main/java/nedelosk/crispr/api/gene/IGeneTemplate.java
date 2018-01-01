package nedelosk.crispr.api.gene;

import nedelosk.crispr.api.IGeneticDefinition;
import nedelosk.crispr.api.alleles.IAllele;

public interface IGeneTemplate {

	IAllele getAllele();

	IGeneType getType();

	IGeneticDefinition getDescription();
}
