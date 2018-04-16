package genetics.api.root;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * A optional that describes a {@link IIndividualRoot}.
 * <p>
 * You can get the optional of an {@link IIndividualRoot} by calling
 * {@link IRootRegistry#getRoot(String)} or {@link IIndividualRootBuilder#getOptional()} at
 * the definition builder of the definition.
 *
 * @param <R> @param <R> The type of the root of the individual.
 */
public interface IOptionalRoot<R extends IIndividualRoot> {
	Optional<R> maybeDefinition();

	/**
	 * Returns the described definition of this optional.
	 *
	 * @return The described definition of this optional.
	 * @throws NoSuchElementException if the definition is null.
	 */
	R get();

	/**
	 * Return {@code true} if there is a definition present, otherwise {@code false}.
	 *
	 * @return {@code true} if there is a definition present, otherwise {@code false}
	 */
	boolean isPresent();

	void ifPresent(Consumer<R> consumer);
}
