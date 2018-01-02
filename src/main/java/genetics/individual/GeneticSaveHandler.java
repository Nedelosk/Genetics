package genetics.individual;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import genetics.Log;
import genetics.api.GeneticsAPI;
import genetics.api.IGeneticSaveHandler;
import genetics.api.alleles.IAllele;
import genetics.api.alleles.IAlleleTemplate;
import genetics.api.definition.ITemplateContainer;
import genetics.api.gene.IGene;
import genetics.api.gene.IGeneType;
import genetics.api.gene.IKaryotype;
import genetics.api.individual.IChromosome;
import genetics.api.individual.IGenome;

public enum GeneticSaveHandler implements IGeneticSaveHandler {
	INSTANCE;
	public static final String GENOME_TAG = "Genome";
	private static SaveFormat writeFormat = SaveFormat.UID;

	public static void setWriteFormat(SaveFormat writeFormat) {
		GeneticSaveHandler.writeFormat = writeFormat;
	}

	public NBTTagCompound writeTag(IChromosome[] chromosomes, IKaryotype karyotype, NBTTagCompound tagCompound) {
		return writeFormat.writeTag(chromosomes, karyotype, tagCompound);
	}

	public IChromosome[] readTag(IKaryotype karyotype, NBTTagCompound tagCompound) {
		SaveFormat format = getFormat(tagCompound);
		return format.readTag(karyotype, tagCompound);
	}

	public SaveFormat getFormat(NBTTagCompound tagCompound) {
		for (SaveFormat format : SaveFormat.values()) {
			if (format.canLoad(tagCompound)) {
				return format;
			}
		}
		return SaveFormat.UID;
	}

	@Nullable
	public IAllele<?> getAlleleDirectly(NBTTagCompound genomeNBT, IGeneType geneType, boolean active) {
		SaveFormat format = getFormat(genomeNBT);
		return format.getAlleleDirectly(genomeNBT, geneType, active);
	}

	public IChromosome getSpecificChromosome(NBTTagCompound genomeNBT, IGeneType geneType) {
		SaveFormat format = getFormat(genomeNBT);
		return format.getSpecificChromosome(genomeNBT, geneType);
	}

	// NBT RETRIEVAL

	/**
	 * Quickly gets the species without loading the whole genome. And without creating absent chromosomes.
	 */
	@Nullable
	public IAllele getAlleleDirectly(IGeneType geneType, boolean active, ItemStack itemStack) {
		NBTTagCompound nbtTagCompound = itemStack.getTagCompound();
		if (nbtTagCompound == null) {
			return null;
		}

		NBTTagCompound genomeNBT = nbtTagCompound.getCompoundTag(GENOME_TAG);
		if (genomeNBT.hasNoTags()) {
			return null;
		}
		IAllele allele = getAlleleDirectly(genomeNBT, geneType, active);
		IGene gene = GeneticsAPI.geneticSystem.getGene(geneType).orElse(null);
		if (gene == null || allele == null || !gene.isValidAllele(allele)) {
			return null;
		}
		return allele;
	}

	public IAllele getAllele(ItemStack itemStack, IGeneType geneType, boolean active) {
		IChromosome chromosome = getSpecificChromosome(itemStack, geneType);
		return active ? chromosome.getActiveAllele() : chromosome.getInactiveAllele();
	}

	/**
	 * Tries to load a specific chromosome and creates it if it is absent.
	 */
	public IChromosome getSpecificChromosome(ItemStack itemStack, IGeneType geneType) {
		NBTTagCompound nbtTagCompound = itemStack.getTagCompound();
		if (nbtTagCompound == null) {
			nbtTagCompound = new NBTTagCompound();
			itemStack.setTagCompound(nbtTagCompound);
		}
		NBTTagCompound genomeNBT = nbtTagCompound.getCompoundTag(GENOME_TAG);

		if (genomeNBT.hasNoTags()) {
			Log.error("Got a genetic item with no genome, setting it to a default value.");
			genomeNBT = new NBTTagCompound();

			ITemplateContainer container = geneType.getDefinition();
			IAlleleTemplate defaultTemplate = container.getDefaultTemplate();
			IGenome genome = defaultTemplate.toGenome(null);
			genome.writeToNBT(genomeNBT);
			nbtTagCompound.setTag(GENOME_TAG, genomeNBT);
		}

		return getSpecificChromosome(genomeNBT, geneType);
	}
}
