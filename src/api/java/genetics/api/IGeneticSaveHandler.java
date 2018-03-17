package genetics.api;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import genetics.api.alleles.IAllele;
import genetics.api.gene.IChromosomeType;
import genetics.api.gene.IKaryotype;
import genetics.api.individual.IChromosome;
import genetics.api.individual.IGenome;

/**
 * This handler provides some functions to save and load {@link IAllele}s, {@link IChromosome}s and {@link IGenome}s.
 * <p>
 * Get the instance from {@link GeneticsAPI#saveHandler}.
 */
public interface IGeneticSaveHandler {

	/**
	 * Writes the given chromosomes to a NBT-Data.
	 *
	 * @param chromosomes The chromosomes that you want to write to the NBT-Data.
	 * @param karyotype   The karyotype of the chromosomes of the genome to that the chromosomes array belongs.
	 * @param tagCompound The NBT-Data to that the data of the chromosomes should be written.
	 */
	NBTTagCompound writeTag(IChromosome[] chromosomes, IKaryotype karyotype, NBTTagCompound tagCompound);

	/**
	 * Loads the chromosomes form the NBt-Data.
	 *
	 * @param karyotype   The karyotype of the chromosomes that the NBT-Data contains.
	 * @param tagCompound The NBT-Data that contain the information of the chromosomes
	 */
	IChromosome[] readTag(IKaryotype karyotype, NBTTagCompound tagCompound);

	/**
	 * Quickly gets the species without loading the whole genome. And without creating absent chromosomes.
	 *
	 * @param genomeNBT The NBT-Data that contains the information about the chromosome
	 * @param geneType  The gene type of the chromosome.
	 * @param active    if the returned allele should be the active one.
	 * @return The active or inactive allele of the chromosome if the chromosome is present.
	 */
	@Nullable
	IAllele getAlleleDirectly(NBTTagCompound genomeNBT, IChromosomeType geneType, boolean active);

	/**
	 * Quickly gets the allele without loading the whole genome. And without creating absent chromosomes.
	 *
	 * @param itemStack The stack that contains the information about the chromosome
	 * @param geneType  The gene type of the chromosome.
	 * @param active    if the returned allele should be the active one.
	 * @return The active or inactive allele of the chromosome if the chromosome is present.
	 */
	@Nullable
	IAllele getAlleleDirectly(ItemStack itemStack, IChromosomeType geneType, boolean active);

	/**
	 * Tries to load the chromosome of the given type and creates it if it is absent.
	 *
	 * @param itemStack The stack that contains the information about the chromosome
	 * @param geneType  The gene type of the chromosome.
	 * @param active    if the returned allele should be the active one.
	 * @return The active or inactive allele of the chromosome.
	 */
	IAllele getAllele(ItemStack itemStack, IChromosomeType geneType, boolean active);

	/**
	 * Tries to load a specific chromosome and creates it if it is absent.
	 *
	 * @param genomeNBT The NBT-Data that contains the information about the chromosome
	 * @param geneType  The gene type of the chromosome.
	 * @return The chromosome.
	 */
	IChromosome getSpecificChromosome(NBTTagCompound genomeNBT, IChromosomeType geneType);

	/**
	 * Tries to load a specific chromosome and creates it if it is absent.
	 *
	 * @param itemStack The stack that contains the information about the chromosome
	 * @param geneType  The gene type of the chromosome.
	 * @return The chromosome.
	 */
	IChromosome getSpecificChromosome(ItemStack itemStack, IChromosomeType geneType);
}
