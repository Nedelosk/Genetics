package genetics.api.definition;

import java.util.Optional;

import genetics.api.individual.IGenomeWrapper;
import genetics.api.individual.IIndividual;

public interface IOptionalDefinition<I extends IIndividual, R extends IIndividualRoot<I, IGenomeWrapper>> {
	Optional<IIndividualDefinition<I, R>> maybeDefinition();

	Optional<IIndividualDefinitionBuilder<I, R>> maybeBuilder();

	/**
	 * @return the string based unique identifier of the definition.
	 */
	String getUID();
}
