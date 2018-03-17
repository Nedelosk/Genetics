package genetics.individual;

import javax.annotation.Nullable;
import java.util.Optional;

import genetics.api.definition.IIndividualDefinition;
import genetics.api.definition.IIndividualDefinitionBuilder;
import genetics.api.definition.IIndividualRoot;
import genetics.api.definition.IOptionalDefinition;
import genetics.api.individual.IGenomeWrapper;
import genetics.api.individual.IIndividual;

import genetics.definition.IndividualDefinitionBuilder;

public class OptionalDefinition<I extends IIndividual, R extends IIndividualRoot<I, IGenomeWrapper>> implements IOptionalDefinition<I, R> {
	private final String uid;
	@Nullable
	private IIndividualDefinition<I, R> definition;
	@Nullable
	private IndividualDefinitionBuilder<I, R> builder;

	public OptionalDefinition(String uid, IndividualDefinitionBuilder<I, R> builder) {
		this(uid, null, builder);
	}

	public OptionalDefinition(String uid, IIndividualDefinition<I, R> definition) {
		this(uid, definition, null);
	}

	public OptionalDefinition(String uid) {
		this(uid, null, null);
	}

	public OptionalDefinition(String uid, @Nullable IIndividualDefinition<I, R> definition, @Nullable IndividualDefinitionBuilder<I, R> builder) {
		this.uid = uid;
		this.definition = definition;
		this.builder = builder;
	}

	public void build() {
		if (builder != null) {
			definition = builder.create();
			builder = null;
		}
	}

	@Override
	public Optional<IIndividualDefinition<I, R>> maybeDefinition() {
		return Optional.ofNullable(definition);
	}

	@Override
	public Optional<IIndividualDefinitionBuilder<I, R>> maybeBuilder() {
		return Optional.ofNullable(builder);
	}

	@Override
	public String getUID() {
		return uid;
	}
}
