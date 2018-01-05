package genetics.api.individual;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * The IGeneticHandler handles the genetic information of an stack whose item represents a specific {@link IOrganismType}.
 */
public interface IOrganismHandler<I extends IOrganism> {

	/**
	 * Gets the item that every stack that contains the genetic information of a individual with this time must have.
	 */
	Item getItem();

	/**
	 * Creates a stack that contains the genetic information of the given individual in the NBT-Data.
	 *
	 * @param individual The individual that contains the genetic information
	 */
	ItemStack createStack(I individual);

	/**
	 * Creates a individual with the genetic information that the NBT-Data of the stack contains.
	 */
	I createIndividual(ItemStack itemStack);
}
