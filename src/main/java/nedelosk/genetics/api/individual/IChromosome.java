package nedelosk.genetics.api.individual;

import java.util.Random;

import net.minecraft.nbt.NBTTagCompound;

import nedelosk.genetics.api.alleles.IAllele;
import nedelosk.genetics.api.gene.IGeneType;

public interface IChromosome<V> {
	IGeneType getGeneKey();

	IAllele<V> getActiveAllele();

	IAllele<V> getInactiveAllele();

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
	IChromosome inheritChromosome(Random rand, IChromosome<V> otherChromosome);
}
