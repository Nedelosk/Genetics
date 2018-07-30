package genetics.api.alleles;

import net.minecraft.util.ResourceLocation;

import genetics.api.IGeneticFactory;
import genetics.api.individual.IChromosomeType;
import genetics.api.individual.IKaryotype;

/**
 * Can be used to create allele templates.
 * <p>
 * You can get an instance of this from the species root with
 * {@link IGeneticFactory#createTemplateBuilder(IKaryotype)} or {@link IGeneticFactory#createTemplateBuilder(IKaryotype, IAllele[])}.
 */
public interface IAlleleTemplateBuilder {

	/**
	 * Sets a allele at a position of the chromosome.
	 *
	 * @param allele         The allele that should be set at the position.
	 * @param chromosomeType The position at the chromosome array.
	 */
	IAlleleTemplateBuilder set(IChromosomeType chromosomeType, IAllele allele);

	/**
	 * Sets a allele at a position of the chromosome.
	 *
	 * @param chromosomeType The key of the allele that should be set at the position.
	 * @param registryName   The registry name of the allele.
	 */
	IAlleleTemplateBuilder set(IChromosomeType chromosomeType, ResourceLocation registryName);

	/**
	 * Sets a allele at a position of the chromosome.
	 *
	 * @param chromosomeType The key of the allele that should be set at the position.
	 * @param registryName   The registry name of the allele.
	 */
	default IAlleleTemplateBuilder set(IChromosomeType chromosomeType, String registryName) {
		return set(chromosomeType, new ResourceLocation(registryName));
	}

	/**
	 * @return The karyotype that defines the {@link #size()} and which alleles this template can contain.
	 */
	IKaryotype getKaryotype();

	/**
	 * @return The count of genes.
	 */
	int size();

	/**
	 * @return Builds a allele template out of the data of this builder.
	 */
	IAlleleTemplate build();
}
