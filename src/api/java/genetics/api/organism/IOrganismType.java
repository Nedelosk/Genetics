package genetics.api.organism;

import genetics.api.definition.IIndividualDefinitionBuilder;
import genetics.api.individual.IIndividual;

/**
 * Genetic types, implemented by enums and compared with ==
 * <p>
 * You have to register your {@link IOrganismType} together with a {@link IOrganismHandler} at the
 * {@link IIndividualDefinitionBuilder} that handles the {@link IIndividual} that this type belongs to.
 */
public interface IOrganismType {

	String getName();
}
