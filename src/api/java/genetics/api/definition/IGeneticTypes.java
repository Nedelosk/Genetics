package genetics.api.definition;

import java.util.Collection;
import java.util.Optional;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.common.capabilities.Capability;

import genetics.api.individual.IGeneticHandler;
import genetics.api.individual.IGeneticType;
import genetics.api.individual.IIndividual;
import genetics.api.items.IIndividualHandler;

/**
 * The IGeneticTypes offers several functions to retrieving genetic information from an {@link ItemStack}.
 * For every item that should contain genetic information you have to provide a {@link IIndividualHandler} that can be
 * retrieved with {@link ItemStack#getCapability(Capability, EnumFacing)} and you have to register a {@link IGeneticType}
 * and a {@link IGeneticHandler} for this type at the {@link IGeneticDefinitionBuilder} that handles the individual.
 *
 * @param <I> The type of {@link IIndividual} that all items are containing.
 */
public interface IGeneticTypes<I extends IIndividual> {

	/**
	 * Creates a stack that has the item of the given type an the genetic information of the given individual with the
	 * help of the {@link IGeneticHandler} that was registered for the given type.
	 * {@link IGeneticHandler#createStack(IIndividual)}
	 *
	 * @param individual The individual that contains the genetic information
	 * @param type       The type in tha the individual
	 */
	ItemStack createStack(I individual, IGeneticType type);

	/**
	 * Creates a individual with the genetic information that the NBT-Data of the stack contains with the
	 * help of the {@link IGeneticHandler} that was registered for the given type.
	 * {@link IGeneticHandler#createIndividual(ItemStack)}
	 *
	 * @return A empty optional if no {@link IGeneticType} was registered for the item of this stack.
	 */
	Optional<I> createIndividual(ItemStack itemStack);

	/**
	 * Gets the type of the item that the given stack contains
	 *
	 * @return A empty optional if no {@link IGeneticType} was registered for the item of this stack.
	 */
	Optional<IGeneticType> getType(ItemStack itemStack);

	/**
	 * Gets the handler that handles the {@link ItemStack}s of the given genetic type.
	 *
	 * @return A empty optional if the given {@link IGeneticType} was not registered in the
	 * {@link IGeneticDefinitionBuilder}.
	 */
	Optional<IGeneticHandler<I>> getHandler(IGeneticType type);

	/**
	 * All types that were registered at the {@link IGeneticDefinitionBuilder}.
	 */
	Collection<IGeneticType> getTypes();

	/**
	 * All handlers that were registered at the {@link IGeneticDefinitionBuilder}.
	 */
	Collection<IGeneticHandler<I>> getHandlers();
}
