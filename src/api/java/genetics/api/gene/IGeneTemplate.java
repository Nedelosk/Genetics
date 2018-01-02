package genetics.api.gene;

import genetics.api.alleles.IAllele;
import genetics.api.definition.IGeneticDefinition;

public interface IGeneTemplate {

	IAllele getAllele();

	IGeneType getType();

	IGeneticDefinition getDescription();
}
