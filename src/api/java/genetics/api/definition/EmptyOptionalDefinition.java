package genetics.api.definition;

import java.util.Optional;
import java.util.function.Consumer;

import genetics.api.individual.IIndividual;

/**
 * A empty instance of an {@link IOptionalDefinition}.
 */
public final class EmptyOptionalDefinition implements IOptionalDefinition {
	private static final EmptyOptionalDefinition INSTANCE = new EmptyOptionalDefinition();

	public static <I extends IIndividual, R extends IIndividualRoot<I>> IOptionalDefinition<I, R> empty() {
		@SuppressWarnings("unchecked")
		IOptionalDefinition<I, R> t = (IOptionalDefinition<I, R>) INSTANCE;
		return t;
	}

	private EmptyOptionalDefinition() {
	}

	@Override
	public Optional<IIndividualDefinition> maybeDefinition() {
		return Optional.empty();
	}

	@Override
	public IIndividualDefinition get() {
		throw new NullPointerException();
	}

	@Override
	public boolean isPresent() {
		return false;
	}

	@Override
	public void ifPresent(Consumer consumer) {
		//The optional is empty, so we have nothing to call.
	}
}
