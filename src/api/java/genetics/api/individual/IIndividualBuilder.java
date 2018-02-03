package genetics.api.individual;

import genetics.api.alleles.IAllele;
import genetics.api.alleles.IAlleleKey;
import genetics.api.definition.IIndividualDefinition;
import genetics.api.gene.IGeneType;

/**
 * Can be used to create a modified version of an {@link IIndividual}. At the crate of this builder all genetic
 * information will be copied and the the {@link IIndividual} that was used to crate this builder will not be changed.
 */
public interface IIndividualBuilder<I extends IIndividual> {

	/**
	 * @return The definition of the individual.
	 */
	IIndividualDefinition<I, ?> getDefinition();

	/**
	 * Sets a allele at a position of the chromosome.
	 *
	 * @param allele The allele that should be set at the position.
	 * @param type   The position at the chromosome array.
	 * @param active True if you want to set the active allele, false otherwise.
	 */
	void setAllele(IGeneType type, IAllele<?> allele, boolean active);

	/**
	 * Sets a allele at a position of the chromosome.
	 *
	 * @param key    The key of the allele that should be set at the position.
	 * @param type   The position at the chromosome array.
	 * @param active True if you want to set the active allele, false otherwise.
	 */
	void setAllele(IGeneType type, IAlleleKey key, boolean active);

	/**
	 * @return The {@link IIndividual} that was used to create this builder.
	 */
	I getCreationIndividual();

	/**
	 * @return Creates a individual.
	 */
	I build();
}
