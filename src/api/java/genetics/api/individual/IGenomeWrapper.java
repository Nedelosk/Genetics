package genetics.api.individual;

import genetics.api.definition.IOrganismRoot;

/**
 * A wrapper around a genome with methods that allow to get quick access to the value of an allele or to the allele
 * itself.
 *
 * The goal of this interface is to make it easier for other mods to create there own {@link IOrganism} and
 * that they not have to use the internal {@link IGenome} class.
 *
 * <p>
 * You can get an instance of a genome wrapper from the {@link IOrganismRoot} of a species with
 * {@link IOrganismRoot#createWrapper(IGenome)}.
 */
public interface IGenomeWrapper {
	IGenome getGenome();

}
