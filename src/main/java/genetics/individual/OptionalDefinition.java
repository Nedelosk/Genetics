package genetics.individual;

import javax.annotation.Nullable;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;

import genetics.api.definition.IIndividualDefinition;
import genetics.api.definition.IIndividualRoot;
import genetics.api.definition.IOptionalDefinition;
import genetics.api.individual.IIndividual;

public class OptionalDefinition<I extends IIndividual, R extends IIndividualRoot<I>> implements IOptionalDefinition<I, R> {
	@Nullable
	private IIndividualDefinition<I, R> definition = null;

	public void setDefinition(@Nullable IIndividualDefinition<I, R> definition) {
		this.definition = definition;
	}

	@Override
	public Optional<IIndividualDefinition<I, R>> maybeDefinition() {
		return Optional.ofNullable(definition);
	}

	@Override
	public IIndividualDefinition get() {
		if (definition == null) {
			throw new NoSuchElementException("No value present");
		}
		return definition;
	}

	@Override
	public boolean isPresent() {
		return definition != null;
	}

	@Override
	public void ifPresent(Consumer<IIndividualDefinition<I, R>> consumer) {
		if (definition != null) {
			consumer.accept(definition);
		}
	}
}
