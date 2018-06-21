package genetics.api.individual;

import net.minecraft.nbt.NBTTagCompound;

import genetics.api.IGeneticFactory;
import genetics.api.alleles.IAllele;
import genetics.api.alleles.IAlleleTemplate;

/**
 * Holds the {@link IChromosome}s which comprise the traits of a given individual.
 * <p>
 * You can create one with {@link IGeneticFactory#createGenome(IKaryotype, IChromosome[])} or
 * {@link IAlleleTemplate#toGenome()}.
 */
public interface IGenome {

	/**
	 * @return A array with all chromosomes of this genome.
	 */
	IChromosome[] getChromosomes();

	/**
	 * @return The active allele of the chromosome with the given type.
	 */
	IAllele getActiveAllele(IChromosomeType chromosomeType);

	/**
	 * @return The value of the active allele of the chromosome with the given type.
	 */
	<V> V getActiveValue(IChromosomeType chromosomeType, Class<? extends V> valueClass);

	/**
	 * @return The inactive allele of the chromosome with the given type.
	 */
	IAllele getInactiveAllele(IChromosomeType chromosomeType);

	/**
	 * @return The value of the inactive allele of the chromosome with the given type.
	 */
	<V> V getInactiveValue(IChromosomeType chromosomeType, Class<? extends V> valueClass);

	/**
	 * @return The chromosome with the given type.
	 */
	IChromosome getChromosome(IChromosomeType chromosomeType);

	/**
	 * @return A array that contains all active alleles of this genome.
	 */
	IAllele[] getActiveAlleles();

	/**
	 * @return A array that contains all inactive alleles of this genome.
	 */
	IAllele[] getInactiveAlleles();

	/**
	 * @return true if the given other IGenome has the amount of chromosomes and their alleles are identical.
	 */
	boolean isGeneticEqual(IGenome other);

	/**
	 * @return true if this chromosome has the same active and inactive allele.
	 */
	boolean isPureBred(IChromosomeType chromosomeType);

	/**
	 * @return The karyotype of this genome. It defines the positions of the chromosomes in the array and the length
	 * of it.
	 */
	IKaryotype getKaryotype();

	/**
	 * Writes the data of this genome to the NBT-Data.
	 * <p>
	 * You can read the NBT-Data with {@link IGeneticFactory#createGenome(IKaryotype, IChromosome[])}.
	 */
	NBTTagCompound writeToNBT(NBTTagCompound compound);
}
