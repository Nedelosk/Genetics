package nedelosk.crispr.api.gene;

import nedelosk.crispr.api.IGeneticDefinition;
import nedelosk.crispr.api.alleles.Allele;

public interface IGeneTemplate {

	Allele getAllele();

	IGeneType getType();

	IGeneticDefinition<?> getDescription();
}
