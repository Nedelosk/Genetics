package genetics.api.gene;

import genetics.api.definition.IGeneticDefinition;
import genetics.api.registry.IGeneticSystem;

/**
 * Interface to be implemented by the enums representing the various chromosomes
 */
public interface IGeneType {
	/**
	 * @return The position of a chromosome that has this type at a chromosome array.
	 */
	int getIndex();

	/**
	 * @return The definition that contains this type in a {@link IKaryotype}.
	 * @implNote You can use {@link IGeneticSystem#getDefinition(String)} to get a instance of your definition.
	 */
	IGeneticDefinition getDefinition();

	/**
	 * @return Short identifier.
	 */
	String getName();
}
