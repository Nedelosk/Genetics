package genetics.api.gene;

import genetics.api.root.IIndividualRoot;
import genetics.api.root.IRootRegistry;

/**
 * Interface to be implemented by the enums representing the various chromosomes
 */
public interface IChromosomeType {
	/**
	 * @return The position of a chromosome that has this type at a chromosome array.
	 */
	int getIndex();

	/**
	 * @return The definition that contains this type in a {@link IKaryotype}.
	 * @implNote You can use {@link IRootRegistry#getRoot(String)} to get a instance of your definition.
	 */
	IIndividualRoot getRoot();

	/**
	 * @return Short identifier.
	 */
	String getName();
}
