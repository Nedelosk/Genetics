package genetics.api.root;

import java.util.Optional;
import java.util.function.Consumer;

import genetics.api.individual.IIndividual;

/**
 * A empty instance of an {@link IOptionalRoot}.
 */
public final class EmptyOptionalRoot implements IOptionalRoot {
	private static final EmptyOptionalRoot INSTANCE = new EmptyOptionalRoot();

	public static <I extends IIndividual, R extends IIndividualRoot<I>> IOptionalRoot<R> empty() {
		@SuppressWarnings("unchecked")
		IOptionalRoot<R> t = (IOptionalRoot<R>) INSTANCE;
		return t;
	}

	private EmptyOptionalRoot() {
	}

	@Override
	public Optional<IIndividualRoot> maybeDefinition() {
		return Optional.empty();
	}

	@Override
	public IIndividualRoot get() {
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
