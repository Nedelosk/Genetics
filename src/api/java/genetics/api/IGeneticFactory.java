package genetics.api;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import genetics.api.alleles.IAllele;
import genetics.api.alleles.IAlleleTemplateBuilder;
import genetics.api.definition.IGeneticDefinition;
import genetics.api.definition.IGeneticRoot;
import genetics.api.gene.IGeneType;
import genetics.api.gene.IKaryotype;
import genetics.api.individual.IChromosome;
import genetics.api.individual.IGeneticType;
import genetics.api.individual.IGenome;
import genetics.api.individual.IIndividual;
import genetics.api.individual.IIndividualHandler;

/**
 * A factory that can be used to create some default implementations.
 * <p>
 * Get the instance from {@link GeneticsAPI#geneticFactory}.
 */
public interface IGeneticFactory {

	/**
	 * Creates a {@link IAlleleTemplateBuilder} that contains the default template alleles.
	 *
	 * @param definition The definition that describes the individual which this template belongs to.
	 */
	IAlleleTemplateBuilder createTemplate(IGeneticDefinition definition);

	/**
	 * Creates a {@link IAlleleTemplateBuilder} that contains the given allele array.
	 *
	 * @param definition The definition that describes the individual which this template belongs to.
	 * @param alleles    A array that contains all alleles for this template. It must have the same length like the
	 *                   karyotype of the individual.
	 */
	IAlleleTemplateBuilder createTemplate(IGeneticDefinition definition, IAllele[] alleles);

	/**
	 * Creates a instance of the default implementation of a {@link IGenome} out of the NBT-Data.
	 *
	 * @param karyotype The karyotype of the individual that contains the genome.
	 * @param compound The NBT-Data that contains the information about the genome. You can use
	 * {@link IGenome#writeToNBT(NBTTagCompound)} or
	 * {@link IGeneticSaveHandler#writeTag(IChromosome[], IKaryotype, NBTTagCompound)} to get the data.
	 */
	IGenome createGenome(IKaryotype karyotype, NBTTagCompound compound);

	/**
	 * Creates a instance of the default implementation of a {@link IGenome}.
	 *
	 * @param karyotype The karyotype of the individual that contains the genome.
	 * @param chromosomes The chromosomes that the genome should contain
	 */
	IGenome createGenome(IKaryotype karyotype, IChromosome[] chromosomes);

	/**
	 * Creates an instance of a {@link IChromosome} with the same active and inactive allele.
	 *
	 * @return A instance of {@link IChromosome}.
	 */
	IChromosome createChromosome(IAllele allele, IGeneType type);

	/**
	 * Creates an instance of a {@link IChromosome}.
	 *
	 * The order of the alleles only matters if both alleles are recessive.
	 *
	 * @param firstAllele  The first allele.
	 * @param secondAllele The second allele.
	 * @return A instance of {@link IChromosome}.
	 */
	IChromosome createChromosome(IAllele firstAllele, IAllele secondAllele, IGeneType type);

	/**
	 * Creates a default instance of a {@link IIndividualHandler}
	 *
	 * @param itemStack   The item that contains the genetic information.
	 * @param type The species type of the individual.
	 * @param definition The definition that describes the individual.
	 *
	 * @return A instance of {@link IIndividualHandler}.
	 */
	<I extends IIndividual> IIndividualHandler<I> createIndividualHandler(ItemStack itemStack, IGeneticType type, IGeneticDefinition<I, IGeneticRoot> definition);
}
