package nedelosk.crispr.utils;

import javax.annotation.Nullable;
import java.util.Arrays;

import net.minecraft.util.ResourceLocation;

import nedelosk.crispr.Crispr;
import nedelosk.crispr.api.alleles.IAllele;
import nedelosk.crispr.api.gene.IGeneType;
import nedelosk.crispr.api.gene.IKaryotype;
import nedelosk.crispr.api.individual.IChromosome;
import nedelosk.crispr.apiimp.AlleleRegistry;
import nedelosk.crispr.apiimp.individual.Chromosome;
import nedelosk.crispr.apiimp.individual.ChromosomeInfo;

/**
 * A byte buffer that can be used to encode and decode chromosomes and alleles into a byte array.
 */
public class SimpleByteBuf {

	/**
	 * Using 22 as a default capacity because that is the default size of an encoded tree genome.
	 */
	private static final int DEFAULT_INITIAL_CAPACITY = 22;

	private byte[] data;
	/**
	 * The current index at that the buffer writes or reads the next byte.
	 */
	private int index;

	/**
	 * Creates a byte buffer with a specific initial capacity.
	 *
	 * @param initialCapacity The initial capacity that the internal array should have.
	 */
	public SimpleByteBuf(int initialCapacity) {
		this.data = new byte[initialCapacity];
	}

	/**
	 * Creates a byte buffer with the default initial capacity.
	 */
	public SimpleByteBuf() {
		this(DEFAULT_INITIAL_CAPACITY);
	}

	/**
	 * Creates a byte buffer that uses the give byte array.
	 */
	public SimpleByteBuf(byte[] data) {
		this.data = data;
	}

	/**
	 * @return A array with all data that this byte buffer contains.
	 */
	public byte[] toByteArray() {
		return data;
	}

	/**
	 * Write the internal id of the allele to the byte array as a varint.
	 */
	public void writeAllele(IAllele allele) {
		AlleleRegistry registry = Crispr.alleleRegistry;
		int id = registry.getId(allele);
		if (id < 0) {
			writeVarInt(0);
			return;
		}
		writeVarInt(id);
	}

	/**
	 * Read a allele from the byte array using the internal id of the allele written to the array as a varint.
	 */
	@Nullable
	public IAllele readAllele() {
		AlleleRegistry registry = Crispr.alleleRegistry;
		int id = readVarInt();
		return registry.getAllele(id);
	}

	/**
	 * Writes the chromosomes to the internal byte array.
	 *
	 * @param chromosomes The chromosomes that should be written.
	 * @param karyotype   The species root of the genome that contains the chromosomes.
	 */
	public void writeChromosomes(IChromosome[] chromosomes, IKaryotype karyotype) {
		for (IGeneType type : karyotype.getGeneTypes()) {
			int index = type.getIndex();
			if (index >= chromosomes.length) {
				continue;
			}
			IChromosome chromosome = chromosomes[index];
			writeAllele(chromosome.getActiveAllele());
			writeAllele(chromosome.getInactiveAllele());
		}
	}

	/**
	 * Reads chromosomes from the byte array.
	 *
	 * @param karyotype The species root of the genome that contains the chromosomes.
	 * @return The chromosome that were read.
	 */
	public IChromosome[] readChromosomes(IKaryotype karyotype) {
		IGeneType[] types = karyotype.getGeneTypes();
		IChromosome[] chromosomes = new IChromosome[types.length];

		ResourceLocation primaryTemplateIdentifier = null;
		ResourceLocation secondaryTemplateIdentifier = null;

		for (IGeneType type : types) {
			int index = type.getIndex();

			Chromosome chromosome = readChromosome(type, primaryTemplateIdentifier, secondaryTemplateIdentifier);
			chromosomes[index] = chromosome;

			if (type == karyotype.getTemplateType()) {
				primaryTemplateIdentifier = chromosome.getActiveAllele().getRegistryName();
				secondaryTemplateIdentifier = chromosome.getInactiveAllele().getRegistryName();
			}
		}
		return chromosomes;
	}

	private Chromosome readChromosome(IGeneType type, ChromosomeInfo info) {
		return readChromosome(type, info.activeSpeciesUid, info.inactiveSpeciesUid);
	}

	private Chromosome readChromosome(IGeneType type, @Nullable ResourceLocation activeSpeciesUid, @Nullable ResourceLocation inactiveSpeciesUid) {
		IAllele firstAllele = readAllele();
		IAllele secondAllele = readAllele();
		return Chromosome.create(activeSpeciesUid, inactiveSpeciesUid, type, firstAllele, secondAllele);
	}

	/**
	 * Reads a specific chromosome from the byte array without creating the whole chromosome array.
	 */
	public ChromosomeInfo readChromosome(IGeneType geneType) {
		IKaryotype karyotype = geneType.getKaryotype();
		IGeneType[] keys = karyotype.getGeneTypes();
		ChromosomeInfo info = new ChromosomeInfo(geneType);

		for (IGeneType key : keys) {
			if (key == geneType) {
				return info.setChromosome(readChromosome(key, info));
			} else if (key == karyotype.getTemplateType()) {
				Chromosome chromosome = readChromosome(key, info);

				info.setSpeciesInfo(chromosome.getActiveAllele().getRegistryName(), chromosome.getInactiveAllele().getRegistryName());
			} else {
				readVarInt();
				readVarInt();
			}
		}
		return info;
	}

	/**
	 * Checks if the byte array has a specific length. If the array is to short, it creates a new array with the length
	 * and copies the content from the old array into the new array.
	 */
	private void ensureCapacity(int capacity) {
		if (capacity - data.length > 0) {
			data = Arrays.copyOf(data, data.length + 1);
		}
	}

	/**
	 * Write a byte to the array
	 */
	private void writeByte(int input) {
		if (input > 255) {
			throw new IllegalArgumentException();
		}
		ensureCapacity(index + 1);
		data[index++] = (byte) input;
	}

	/**
	 * Read a byte from the array.
	 */
	private byte readByte() {
		if (index < 0 || index >= data.length) {
			return -1;
		}
		return data[index++];
	}

	/**
	 * Read a varint from the byte array.
	 */
	public int readVarInt() {
		int output = 0;
		int byteCount = 0;
		while (true) {
			byte b0 = readByte();
			output |= (b0 & 127) << byteCount++ * 7;

			if (byteCount > 5) {
				throw new RuntimeException("VarInt too big");
			}

			if ((b0 & 128) != 128) {
				break;
			}
		}
		return output;
	}

	/**
	 * Write a varint to the array.
	 */
	public void writeVarInt(int input) {
		while ((input & -128) != 0) {
			writeByte((byte) (input & 127 | 128));
			input >>>= 7;
		}

		writeByte((byte) input);
	}
}
