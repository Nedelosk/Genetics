package nedelosk.crispr.api.alleles;

import nedelosk.crispr.api.IGeneticDefinition;
import nedelosk.crispr.api.IGeneticFactory;
import nedelosk.crispr.api.gene.IGeneType;
import nedelosk.crispr.api.gene.IKaryotype;

/**
 * Can be used to create allele templates.
 * <p>
 * You can get an instance of this from the species root with
 * {@link IGeneticFactory#createTemplate(IGeneticDefinition)} or {@link IGeneticFactory#createTemplate(IGeneticDefinition, Allele[])}.
 */
public interface IAlleleTemplateBuilder {

	/**
	 * Sets a allele at a position of the chromosome.
	 *
	 * @param allele  The allele that should be set at the position.
	 * @param geneKey The position at the chromosome array.
	 */
	IAlleleTemplateBuilder set(IGeneType geneKey, IAllele<?> allele);

	IAlleleTemplateBuilder set(IGeneType geneKey, IAlleleKey alleleKey);

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
