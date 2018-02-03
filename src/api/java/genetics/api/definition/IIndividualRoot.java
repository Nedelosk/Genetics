package genetics.api.definition;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;

import genetics.api.alleles.IAllele;
import genetics.api.individual.IGenome;
import genetics.api.individual.IGenomeWrapper;
import genetics.api.individual.IIndividual;

/**
 * The IGeneticRoot offers several functions to create {@link IIndividual}s and to wrap the genome of a
 * {@link IIndividual}.
 *
 * @param <I> The type of the individual that this root provides.
 * @param <W> The type of the wrapper that is used by this root.
 */
public interface IIndividualRoot<I extends IIndividual, W extends IGenomeWrapper> {

	/**
	 * Gets the definition that provides this root.
	 */
	IIndividualDefinition<I, ? extends IIndividualRoot> getDefinition();

	/**
	 * Uses the information that the NBT-Data contains to create a {@link IIndividual}.
	 */
	I create(NBTTagCompound compound);

	/**
	 * Creates a {@link IIndividual} that contains this genome.
	 */
	I create(IGenome genome);

	/**
	 * Creates a {@link IIndividual} that contains the two genome.
	 */
	I create(IGenome genome, IGenome mate);

	/**
	 * Creates a {@link IIndividual} that contains the alleles of the template in a genome.
	 *
	 * @param template The alleles of the genome.
	 */
	default I templateAsIndividual(IAllele[] template) {
		return templateAsIndividual(template, null);
	}

	/**
	 * Creates a {@link IIndividual} that contains the alleles of the two templates in a genome.
	 *
	 * @param templateActive   The active alleles of the genome.
	 * @param templateInactive The inactive alleles of the genome.
	 */
	I templateAsIndividual(IAllele[] templateActive, @Nullable IAllele[] templateInactive);

	/**
	 * A instance of an {@link IIndividual} that is used if a item has lost its generic data.
	 */
	I getDefaultMember();

	/**
	 * Creates a wrapper that can be used to give access to the values of the alleles that the genome contains.
	 */
	W createWrapper(IGenome genome);
}
