package genetics.api.mutation;

import java.util.Collection;

import genetics.api.alleles.IAllele;
import genetics.api.alleles.IAlleleSpecies;
import genetics.api.root.IIndividualRoot;

/**
 * Individuals can be seeded either as hive drops or as mutation results.
 */
public interface IMutation {

	/**
	 * @return {@link IIndividualRoot} this mutation is associated with.
	 */
	IIndividualRoot getRoot();

	/**
	 * @return first of the alleles implementing IAlleleSpecies required for this mutation.
	 */
	IAlleleSpecies getFirstParent();

	/**
	 * @return second of the alleles implementing IAlleleSpecies required for this mutation.
	 */
	IAlleleSpecies getSecondParent();

	/**
	 * @return the allele implementing IAlleleSpecies the resulted of this mutation.
	 */
	IAlleleSpecies getResultingSpecies();

	/**
	 * @return Array of {@link IAllele} representing the full default genome of the mutated side.
	 * <p>
	 * Make sure to return a proper array for the species class. Returning an allele of the wrong type will cause cast errors on runtime.
	 */
	IAllele[] getTemplate();

	/**
	 * @return Unmodified base chance for mutation to fire.
	 */
	float getBaseChance();

	/**
	 * @return Collection of localized, human-readable strings describing special mutation conditions, if any.
	 */
	Collection<String> getSpecialConditions();

	/**
	 * @return true if the passed allele is one of the alleles participating in this mutation.
	 */
	boolean isPartner(IAlleleSpecies allele);

	/**
	 * @return the other allele which was not passed as argument.
	 */
	IAllele getPartner(IAlleleSpecies allele);

	/**
	 * @return true if the mutation should not be displayed in a gui that displays all mutations.
	 */
	boolean isSecret();
}
