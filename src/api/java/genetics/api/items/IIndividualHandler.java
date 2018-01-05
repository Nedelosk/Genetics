package genetics.api.items;

import java.util.Optional;

import net.minecraft.item.ItemStack;

import genetics.api.IGeneticFactory;
import genetics.api.alleles.IAllele;
import genetics.api.definition.IOrganismDefinition;
import genetics.api.definition.IOrganismRoot;
import genetics.api.gene.IGeneType;
import genetics.api.individual.IOrganismType;
import genetics.api.individual.IOrganism;

/**
 * Implement this interface as a capability which should provide the genetic information of an item.
 * <p/>
 * You can use {@link IGeneticFactory#createIndividualHandler(ItemStack, IOrganismType, IOrganismDefinition)} to create an
 * instance of this or you can use your own implementation.
 */
public interface IIndividualHandler<I extends IOrganism> {

	/**
	 * @return Creates the individual out of the nbt of the item.
	 */
	Optional<I> getIndividual();

	/**
	 * @return The root of the individual.
	 */
	IOrganismDefinition<I, IOrganismRoot> getDefinition();

	/**
	 * @return The species type of the individual.
	 */
	IOrganismType getType();

	/**
	 * Quickly gets the allele without loading the whole genome.
	 *
	 * @param type   The chromosome type of the chromosome that contains the allele.
	 * @param active True if the allele should be the active allele of the chromosome, false if not.
	 * @return The allele that is at that position of the genome.
	 */
	IAllele<?> getAlleleDirectly(IGeneType type, boolean active);
}
