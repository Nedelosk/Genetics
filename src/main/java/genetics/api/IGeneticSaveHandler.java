package genetics.api;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import genetics.api.alleles.IAllele;
import genetics.api.gene.IGeneType;
import genetics.api.gene.IKaryotype;
import genetics.api.individual.IChromosome;

public interface IGeneticSaveHandler {

	NBTTagCompound writeTag(IChromosome[] chromosomes, IKaryotype karyotype, NBTTagCompound tagCompound);

	IChromosome[] readTag(IKaryotype karyotype, NBTTagCompound tagCompound);

	@Nullable
	IAllele<?> getAlleleDirectly(NBTTagCompound genomeNBT, IGeneType geneType, boolean active);

	IChromosome getSpecificChromosome(NBTTagCompound genomeNBT, IGeneType geneType);

	/**
	 * Quickly gets the species without loading the whole genome. And without creating absent chromosomes.
	 */
	@Nullable
	IAllele getAlleleDirectly(IGeneType geneType, boolean active, ItemStack itemStack);

	IAllele getAllele(ItemStack itemStack, IGeneType geneType, boolean active);

	/**
	 * Tries to load a specific chromosome and creates it if it is absent.
	 */
	IChromosome getSpecificChromosome(ItemStack itemStack, IGeneType geneType);
}
