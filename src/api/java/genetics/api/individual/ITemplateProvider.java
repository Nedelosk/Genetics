package genetics.api.individual;

import genetics.api.alleles.IAllele;

/**
 * ITemplateProvider is a helper interface that can be implemented if a class contains immutable genetic information
 * about a purebred {@link IIndividual}.
 */
public interface ITemplateProvider {
	/**
	 * @return The active and inactive template of the individual.
	 */
	IAllele[] getTemplate();

	/**
	 * @return A copy of the genome that the individual contains.
	 */
	IGenome getGenome();

	/**
	 * @return Creates a instance of the {@link IIndividual} that contains the {@link #getGenome()}.
	 */
	IIndividual createIndividual();
}
