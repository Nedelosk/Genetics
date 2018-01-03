package genetics.api.individual;

import genetics.api.definition.IGeneticDefinitionBuilder;

/**
 * Genetic types, implemented by enums and compared with ==
 *
 * You have to register your {@link IGeneticType} together with a {@link IGeneticHandler} at the
 * {@link IGeneticDefinitionBuilder} that handles the {@link IIndividual} that this type belongs to.
 */
public interface IGeneticType {
}
