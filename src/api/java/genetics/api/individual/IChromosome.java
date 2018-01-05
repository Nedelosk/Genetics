package genetics.api.individual;

import java.util.Random;

import net.minecraft.nbt.NBTTagCompound;

import genetics.api.IGeneticFactory;
import genetics.api.IGeneticSaveHandler;
import genetics.api.alleles.IAllele;
import genetics.api.gene.IGeneType;


/**
 * Contains two alleles. One is active and the other is inactive.
 *
 * The active allele is the active allele either because the allele is dominant or
 * because both alleles are recessive.
 * <p>
 * Implementations other than Genetic's default one are not supported!
 *
 * You can uses {@link IGeneticFactory#createChromosome(IAllele, IAllele, IGeneType)} to create an instance of this.
 *
 * @author SirSengir
 */
public interface IChromosome {
	IGeneType getGeneKey();

	/**
	 * @return The active allele of this chromosome that is used in the most situations.
	 */
	IAllele getActiveAllele();

	/**
	 * @return The inactive allele of this chromosome.
	 */
	IAllele getInactiveAllele();

	/**
	 * Writes the data of this chromosome to the NBT-Data.
	 *
	 * @implNote If possible please use the {@link IGeneticSaveHandler} to write the whole genome instead.
	 */
	NBTTagCompound writeToNBT(NBTTagCompound compound);

	/**
	 * Creates a new chromosome out of the alleles of this chromosome and the other chromosome.
	 *
	 * It always uses one allele from this and one from the other chromosome to create the new chromosome.
	 * It uses {@link Random#nextBoolean()} to decide which of the two alleles of one chromosome it should use.
	 *
	 * @param rand            The instance of random it should uses to figure out which of the two alleles if should
	 *                        use.
	 * @param otherChromosome The other chromosome that this chromosome uses to create the new one.
	 */
	IChromosome inheritChromosome(Random rand, IChromosome otherChromosome);
}
