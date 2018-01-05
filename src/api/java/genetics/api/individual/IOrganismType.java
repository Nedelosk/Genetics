package genetics.api.individual;

import genetics.api.definition.IOrganismDefinitionBuilder;

/**
 * Genetic types, implemented by enums and compared with ==
 *
 * You have to register your {@link IOrganismType} together with a {@link IOrganismHandler} at the
 * {@link IOrganismDefinitionBuilder} that handles the {@link IOrganism} that this type belongs to.
 */
public interface IOrganismType {
}
