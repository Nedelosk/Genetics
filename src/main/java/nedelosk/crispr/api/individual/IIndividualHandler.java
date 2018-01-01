package nedelosk.crispr.api.individual;

import javax.annotation.Nullable;
import java.util.Optional;

import net.minecraft.item.ItemStack;

import nedelosk.crispr.api.IGeneticDefinition;
import nedelosk.crispr.api.IGeneticFactory;
import nedelosk.crispr.api.IGeneticRoot;
import nedelosk.crispr.api.alleles.IAllele;
import nedelosk.crispr.api.gene.IGeneType;

/**
 * Implement this interface as a capability which should provide the genetic information of an item.
 * <p/>
 * You can use {@link IGeneticFactory#createIndividualHandler(ItemStack, IGeneticType, IGeneticDefinition)} to create an
 * instance of this or you can use your own implementation.
 */
public interface IIndividualHandler<I extends IIndividual> {

	/**
	 * @return Creates the individual out of the nbt of the item.
	 */
	@Nullable
	Optional<I> getIndividual();

	/**
	 * @return The root of the individual.
	 */
	IGeneticDefinition<I, IGeneticRoot> getDefinition();

	/**
	 * @return The species type of the individual.
	 */
	IGeneticType getType();

	/**
	 * Quickly gets the allele without loading the whole genome.
	 *
	 * @param type   The chromosome type of the chromosome that contains the allele.
	 * @param active True if the allele should be the active allele of the chromosome, false if not.
	 * @return The allele that is at that position of the genome.
	 */
	IAllele<?> getAlleleDirectly(IGeneType type, boolean active);
}
