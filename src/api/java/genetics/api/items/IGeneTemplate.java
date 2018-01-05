package genetics.api.items;

import javax.annotation.Nullable;
import java.util.Optional;

import genetics.api.IGeneticFactory;
import genetics.api.alleles.IAllele;
import genetics.api.definition.IOrganismDefinition;
import genetics.api.gene.IGeneType;
import genetics.api.individual.IGenome;

/**
 * The IGeneTemplate is a interface that can be implemented as a capability if a item should represent a allele at a
 * specific {@link IGeneType} at the {@link IGenome} of a individual that is described by a specific
 * {@link IOrganismDefinition}.
 * For example the templates of Gendustry and the Gentics Mod of Binnie Mods.
 * <p>
 * All returned values of this interface are only empty if the template is empty.
 * <p>
 * You can use {@link IGeneticFactory#createGeneTemplate()}  to create an
 * instance of this or you can use your own implementation.
 */
public interface IGeneTemplate {

	/**
	 * @retur The allele that this template contains.
	 */
	Optional<IAllele<?>> getAllele();

	/**
	 * @return The gene type at that the chromosome of the allele is positioned at the chromosome array.
	 */
	Optional<IGeneType> getType();

	/**
	 * @return The genetic definition that describes the definition to that the {@link IGeneType} belongs to.
	 */
	Optional<IOrganismDefinition> getDescription();

	/**
	 * Sets the information of this template.
	 */
	void setAllele(@Nullable IAllele<?> allele, @Nullable IGeneType type);
}
