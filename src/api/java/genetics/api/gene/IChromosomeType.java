package genetics.api.gene;

import genetics.api.definition.IDefinitionRegistry;
import genetics.api.definition.IIndividualDefinition;

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
	 * @implNote You can use {@link IDefinitionRegistry#getDefinition(String)} to get a instance of your definition.
	 */
	IIndividualDefinition getDefinition();

	/**
	 * @return Short identifier.
	 */
	String getName();
}
