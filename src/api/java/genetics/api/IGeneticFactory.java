package genetics.api;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import genetics.api.alleles.IAllele;
import genetics.api.alleles.IAlleleTemplate;
import genetics.api.alleles.IAlleleTemplateBuilder;
import genetics.api.definition.IIndividualDefinition;
import genetics.api.definition.IIndividualRoot;
import genetics.api.gene.IGeneType;
import genetics.api.gene.IKaryotype;
import genetics.api.individual.IChromosome;
import genetics.api.individual.IGenome;
import genetics.api.individual.IIndividual;
import genetics.api.items.IGeneTemplate;
import genetics.api.organism.IOrganism;
import genetics.api.organism.IOrganismType;

/**
 * A factory that can be used to create some default implementations.
 * <p>
 * Get the instance from {@link GeneticsAPI#geneticFactory}.
 */
public interface IGeneticFactory {

	/**
	 * Creates a {@link IAlleleTemplateBuilder} that contains the default template alleles.
	 *
	 * @param karyotype The karyotype that defines the size of the allele array and contains the default template.
	 */
	IAlleleTemplateBuilder createTemplateBuilder(IKaryotype karyotype);

	/**
	 * Creates a {@link IAlleleTemplateBuilder} that contains the given allele array.
	 *
	 * @param karyotype The karyotype that defines the size of the allele array.
	 * @param alleles    A array that contains all alleles for this template. It must have the same length like the
	 *                   karyotype of the individual.
	 */
	IAlleleTemplateBuilder createTemplateBuilder(IKaryotype karyotype, IAllele[] alleles);

	/**
	 * Creates a {@link IAlleleTemplate} that contains the given allele array.
	 *
	 * @param karyotype The karyotype that defines the size of the allele array.
	 * @param alleles    A array that contains all alleles for this template. It must have the same length like the
	 *                   karyotype of the individual.
	 */
	IAlleleTemplate createTemplate(IKaryotype karyotype, IAllele[] alleles);

	/**
	 * Creates a instance of the default implementation of a {@link IGenome} out of the NBT-Data.
	 *
	 * @param karyotype The karyotype of the individual that contains the genome.
	 * @param compound  The NBT-Data that contains the information about the genome. You can use
	 *                  {@link IGenome#writeToNBT(NBTTagCompound)} or
	 *                  {@link IGeneticSaveHandler#writeTag(IChromosome[], IKaryotype, NBTTagCompound)} to get the data.
	 */
	IGenome createGenome(IKaryotype karyotype, NBTTagCompound compound);

	/**
	 * Creates a instance of the default implementation of a {@link IGenome}.
	 *
	 * @param karyotype   The karyotype of the individual that contains the genome.
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
	 * Creates a default implementation of a {@link IOrganism}
	 *
	 * @param itemStack  The item that contains the genetic information.
	 * @param type       The species type of the individual.
	 * @param definition The definition that describes the individual.
	 * @return A instance of {@link IOrganism}.
	 */
	<I extends IIndividual> IOrganism<I> createOrganism(ItemStack itemStack, IOrganismType type, IIndividualDefinition<I, IIndividualRoot<I, ?>> definition);

	/**
	 * Creates a default implementation of a {@link IGeneTemplate}
	 */
	IGeneTemplate createGeneTemplate();
}
