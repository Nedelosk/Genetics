package genetics.individual;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;

import genetics.api.alleles.IAllele;
import genetics.api.gene.IChromosomeType;
import genetics.api.gene.IKaryotype;
import genetics.api.individual.IChromosome;

import genetics.utils.SimpleByteBuf;

public enum SaveFormat {
	//Used before forge fires the FMLLoadCompleteEvent.
	UID {
		@Override
		public NBTTagCompound writeTag(IChromosome[] chromosomes, IKaryotype karyotype, NBTTagCompound tagCompound) {
			NBTTagList tagList = new NBTTagList();
			for (int i = 0; i < chromosomes.length; i++) {
				if (chromosomes[i] != null) {
					NBTTagCompound chromosomeTag = new NBTTagCompound();
					chromosomeTag.setByte(SLOT_TAG, (byte) i);
					chromosomes[i].writeToNBT(chromosomeTag);
					tagList.appendTag(chromosomeTag);
				}
			}
			tagCompound.setTag(CHROMOSOMES_TAG, tagList);
			return tagCompound;
		}

		@Override
		IChromosome[] readTag(IKaryotype karyotype, NBTTagCompound tagCompound) {
			IChromosomeType[] geneTypes = karyotype.getChromosomeTypes();
			NBTTagList chromosomesNBT = tagCompound.getTagList(CHROMOSOMES_TAG, 10);
			IChromosome[] chromosomes = new IChromosome[geneTypes.length];
			ResourceLocation primaryTemplateIdentifier = null;
			ResourceLocation secondaryTemplateIdentifier = null;

			for (int i = 0; i < chromosomesNBT.tagCount(); i++) {
				NBTTagCompound chromosomeNBT = chromosomesNBT.getCompoundTagAt(i);
				byte chromosomeOrdinal = chromosomeNBT.getByte(SLOT_TAG);

				if (chromosomeOrdinal >= 0 && chromosomeOrdinal < chromosomes.length) {
					IChromosomeType geneType = geneTypes[chromosomeOrdinal];
					Chromosome chromosome = Chromosome.create(primaryTemplateIdentifier, secondaryTemplateIdentifier, geneType, chromosomeNBT);
					chromosomes[chromosomeOrdinal] = chromosome;

					if (geneType.equals(karyotype.getTemplateType())) {
						primaryTemplateIdentifier = chromosome.getActiveAllele().getRegistryName();
						secondaryTemplateIdentifier = chromosome.getInactiveAllele().getRegistryName();
					}
				}
			}
			return chromosomes;
		}

		@Nullable
		@Override
		IAllele getAlleleDirectly(NBTTagCompound genomeNBT, IChromosomeType geneType, boolean active) {
			NBTTagList tagList = genomeNBT.getTagList(CHROMOSOMES_TAG, 10);
			if (tagList.hasNoTags()) {
				return null;
			}
			NBTTagCompound chromosomeTag = tagList.getCompoundTagAt(geneType.getIndex());
			if (chromosomeTag.hasNoTags()) {
				return null;
			}
			return (active ? Chromosome.getActiveAllele(chromosomeTag) : Chromosome.getInactiveAllele(chromosomeTag)).orElse(null);
		}

		@Override
		public IChromosome getSpecificChromosome(NBTTagCompound genomeNBT, IChromosomeType chromosomeType) {
			IChromosome[] chromosomes = readTag(chromosomeType.getDefinition().getKaryotype(), genomeNBT);
			return chromosomes[chromosomeType.getIndex()];
		}

		@Override
		public boolean canLoad(NBTTagCompound tagCompound) {
			return tagCompound.hasKey(CHROMOSOMES_TAG) && tagCompound.hasKey(VERSION_TAG);
		}
	},
	//Used for backward compatibility because before Forestry 5.8 the first allele was not always the active allele.
	UUID_DEPRECATED {
		@Override
		public NBTTagCompound writeTag(IChromosome[] chromosomes, IKaryotype karyotype, NBTTagCompound tagCompound) {
			throw new UnsupportedOperationException();
		}

		@SuppressWarnings("deprecation")
		@Override
		IChromosome[] readTag(IKaryotype karyotype, NBTTagCompound tagCompound) {
			IChromosomeType[] geneTypes = karyotype.getChromosomeTypes();
			NBTTagList chromosomesNBT = tagCompound.getTagList(CHROMOSOMES_TAG, 10);
			IChromosome[] chromosomes = new IChromosome[geneTypes.length];
			ResourceLocation primaryTemplateIdentifier = null;
			ResourceLocation secondaryTemplateIdentifier = null;

			for (int i = 0; i < chromosomesNBT.tagCount(); i++) {
				NBTTagCompound chromosomeNBT = chromosomesNBT.getCompoundTagAt(i);
				byte chromosomeOrdinal = chromosomeNBT.getByte(SLOT_TAG);

				if (chromosomeOrdinal >= 0 && chromosomeOrdinal < chromosomes.length) {
					IChromosomeType geneType = geneTypes[chromosomeOrdinal];
					Chromosome chromosome = Chromosome.create(primaryTemplateIdentifier, secondaryTemplateIdentifier, geneType, chromosomeNBT);
					chromosomes[chromosomeOrdinal] = chromosome;

					if (geneType == karyotype.getTemplateType()) {
						primaryTemplateIdentifier = chromosome.getActiveAllele().getRegistryName();
						secondaryTemplateIdentifier = chromosome.getInactiveAllele().getRegistryName();
					}
				}
			}
			return chromosomes;
		}

		@Nullable
		@Override
		IAllele getAlleleDirectly(NBTTagCompound genomeNBT, IChromosomeType geneType, boolean active) {
			NBTTagList tagList = genomeNBT.getTagList(CHROMOSOMES_TAG, 10);
			if (tagList.hasNoTags()) {
				return null;
			}
			NBTTagCompound chromosomeTag = tagList.getCompoundTagAt(geneType.getIndex());
			if (chromosomeTag.hasNoTags()) {
				return null;
			}
			IChromosome chromosome = Chromosome.create(null, null, geneType, chromosomeTag);
			return active ? chromosome.getActiveAllele() : chromosome.getInactiveAllele();
		}

		@Override
		public IChromosome getSpecificChromosome(NBTTagCompound genomeNBT, IChromosomeType geneType) {
			IChromosome[] chromosomes = readTag(geneType.getDefinition().getKaryotype(), genomeNBT);
			return chromosomes[geneType.getIndex()];
		}

		@Override
		public boolean canLoad(NBTTagCompound tagCompound) {
			return tagCompound.hasKey(CHROMOSOMES_TAG);
		}
	},
	//Used to save the chromosomes as compact as possible
	BINARY {
		private static final String DATA_TAG = "data";

		@Override
		NBTTagCompound writeTag(IChromosome[] chromosomes, IKaryotype karyotype, NBTTagCompound tagCompound) {
			SimpleByteBuf byteBuf = new SimpleByteBuf();
			byteBuf.writeChromosomes(chromosomes, karyotype);
			tagCompound.setByteArray(DATA_TAG, byteBuf.toByteArray());
			tagCompound.setInteger(VERSION_TAG, VERSION);

			return tagCompound;
		}

		@Override
		IChromosome[] readTag(IKaryotype karyotype, NBTTagCompound tagCompound) {
			byte[] data = tagCompound.getByteArray(DATA_TAG);
			SimpleByteBuf simpleByteBuf = new SimpleByteBuf(data);
			return simpleByteBuf.readChromosomes(karyotype);
		}

		@Nullable
		@Override
		IAllele getAlleleDirectly(NBTTagCompound genomeNBT, IChromosomeType geneType, boolean active) {
			byte[] data = genomeNBT.getByteArray(DATA_TAG);
			SimpleByteBuf simpleByteBuf = new SimpleByteBuf(data);
			ChromosomeInfo chromosomeInfo = simpleByteBuf.readChromosome(geneType);
			IChromosome chromosome = chromosomeInfo.chromosome;
			if (chromosome == null) {
				return null;
			}
			return active ? chromosome.getActiveAllele() : chromosome.getInactiveAllele();
		}

		@Override
		public IChromosome getSpecificChromosome(NBTTagCompound genomeNBT, IChromosomeType geneType) {
			byte[] data = genomeNBT.getByteArray(DATA_TAG);
			SimpleByteBuf simpleByteBuf = new SimpleByteBuf(data);
			ChromosomeInfo chromosomeInfo = simpleByteBuf.readChromosome(geneType);
			if (chromosomeInfo.chromosome == null) {
				//Fix the broken NBT
				return fixData(genomeNBT, chromosomeInfo);
			}
			return chromosomeInfo.chromosome;
		}

		private IChromosome fixData(NBTTagCompound genomeNBT, ChromosomeInfo missingChromosome) {
			IChromosomeType geneType = missingChromosome.chromosomeType;
			IKaryotype karyotype = geneType.getDefinition().getKaryotype();
			IChromosome[] chromosomes = readTag(karyotype, genomeNBT);
			IChromosome chromosome = Chromosome.create(missingChromosome.activeSpeciesUid, missingChromosome.inactiveSpeciesUid, geneType, null, null);
			chromosomes[geneType.getIndex()] = chromosome;
			writeTag(chromosomes, karyotype, genomeNBT);
			return chromosome;
		}

		@Override
		public boolean canLoad(NBTTagCompound tagCompound) {
			return tagCompound.hasKey(DATA_TAG);
		}
	};

	private static final String VERSION_TAG = "version";
	private static final String SLOT_TAG = "Slot";
	private static final int VERSION = 1;
	private static final String CHROMOSOMES_TAG = "Chromosomes";

	abstract NBTTagCompound writeTag(IChromosome[] chromosomes, IKaryotype karyotype, NBTTagCompound tagCompound);

	abstract IChromosome[] readTag(IKaryotype karyotype, NBTTagCompound tagCompound);

	@Nullable
	abstract IAllele getAlleleDirectly(NBTTagCompound genomeNBT, IChromosomeType geneKey, boolean active);

	abstract IChromosome getSpecificChromosome(NBTTagCompound genomeNBT, IChromosomeType geneKey);

	abstract boolean canLoad(NBTTagCompound tagCompound);
}
