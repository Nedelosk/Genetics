package genetics.api.individual;

import genetics.api.definition.IGeneticRoot;

/**
 * A wrapper around a genome with methods that allow to get quick access to the value of an allele or to the allele
 * itself.
 *
 * The goal of this interface is to make it easier for other mods to create there own {@link IIndividual} and
 * that they not have to use the internal {@link IGenome} class.
 *
 * <p>
 * You can get an instance of a genome wrapper from the {@link IGeneticRoot} of a species with
 * {@link IGeneticRoot#createWrapper(IGenome)}.
 */
public interface IGenomeWrapper {
	IGenome getGenome();

}
