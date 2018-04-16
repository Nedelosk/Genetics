package genetics.api.individual;

import net.minecraft.nbt.NBTTagCompound;

import genetics.api.IGeneticFactory;
import genetics.api.alleles.IAllele;
import genetics.api.alleles.IAlleleTemplate;
import genetics.api.gene.IChromosomeType;
import genetics.api.gene.IKaryotype;

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
	 * @return The active allele of the chromosome with the given gene type.
	 */
	IAllele getActiveAllele(IChromosomeType geneType);

	/**
	 * @return The value of the active allele of the chromosome with the given gene type.
	 */
	<V> V getActiveValue(IChromosomeType geneType, Class<? extends V> valueClass);

	/**
	 * @return The inactive allele of the chromosome with the given gene type.
	 */
	IAllele getInactiveAllele(IChromosomeType geneType);

	/**
	 * @return The value of the inactive allele of the chromosome with the given gene type.
	 */
	<V> V getInactiveValue(IChromosomeType geneType, Class<? extends V> valueClass);

	/**
	 * @return The chromosome with the given gene type.
	 */
	IChromosome getChromosome(IChromosomeType geneType);

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
	boolean isPureBred(IChromosomeType geneType);

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
