package genetics.api.definition;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;

import genetics.api.individual.IIndividual;

/**
 * A optional that describes a {@link IIndividualDefinition}.
 * <p>
 * You can get the optional of an {@link IIndividualDefinition} by calling
 * {@link IDefinitionRegistry#getDefinition(String)} or {@link IIndividualDefinitionBuilder#getOptional()} at
 * the definition builder of the definition.
 *
 * @param <I> @param <I> The type of the individual that the definition describes.
 * @param <R> @param <R> The type of the root of the individual.
 */
public interface IOptionalDefinition<I extends IIndividual, R extends IIndividualRoot<I>> {
	Optional<IIndividualDefinition<I, R>> maybeDefinition();

	/**
	 * Returns the described definition of this optional.
	 *
	 * @return The described definition of this optional.
	 * @throws NoSuchElementException if the definition is null.
	 */
	IIndividualDefinition get();

	/**
	 * Return {@code true} if there is a definition present, otherwise {@code false}.
	 *
	 * @return {@code true} if there is a definition present, otherwise {@code false}
	 */
	boolean isPresent();

	void ifPresent(Consumer<IIndividualDefinition<I, R>> consumer);
}
