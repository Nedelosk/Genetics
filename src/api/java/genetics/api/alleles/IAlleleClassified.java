package genetics.api.alleles;

import genetics.api.classification.IClassification;
import genetics.api.root.IIndividualRoot;

public interface IAlleleClassified extends IAllele {
	/**
	 * @return the {@link IIndividualRoot} associated with this species.
	 */
	IIndividualRoot getRoot();

	/**
	 * @return Localized short description of this species. (May be null.)
	 */
	String getDescription();

	/**
	 * Binomial name of the species sans genus ("Apis"). Returning "humboldti" will have the bee species flavour name be "Apis humboldti". Feel free to use fun
	 * names or return null.
	 *
	 * @return flavour text (may be null)
	 */
	String getBinomial();

	/**
	 * Authority for the binomial name, e.g. "Sengir" on species of base Forestry.
	 *
	 * @return flavour text (may be null)
	 */
	String getAuthority();

	/**
	 * @return Branch this species is associated with.
	 */
	IClassification getBranch();
}
