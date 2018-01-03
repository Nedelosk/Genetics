package genetics.api.items;

import java.util.Optional;

import genetics.api.IGeneticFactory;
import genetics.api.alleles.IAllele;
import genetics.api.alleles.IAlleleKey;
import genetics.api.definition.IGeneticDefinition;
import genetics.api.gene.IGeneType;

/**
 * Implement this interface as a capability which should provide the genetic information of an item.
 * <p/>
 * You can use {@link IGeneticFactory#createGeneTemplate(IAlleleKey, IGeneType, IGeneticDefinition)}  to create an
 * instance of this or you can use your own implementation.
 */
public interface IGeneTemplate {

	Optional<IAllele<?>> getAllele();

	IGeneType getType();

	IGeneticDefinition getDescription();
}
