package nedelosk.crispr.api.alleles;

import nedelosk.crispr.api.gene.IGene;
import nedelosk.crispr.api.IGeneticFactory;
import nedelosk.crispr.api.IGeneticDefinition;

/**
 * Can be used to create allele templates.
 * <p>
 * You can get an instance of this from the species root with
 * {@link IGeneticFactory#createTemplate()} or {@link IGeneticFactory#createTemplate(Allele[])}.
 */
public interface IAlleleTemplateBuilder {

	/**
	 * Sets a allele at a position of the chromosome.
	 *
	 * @param allele The allele that should be set at the position.
	 * @param chromosomeType The position at the chromosome array.
	 */
	<V, G extends IGene<V>> IAlleleTemplateBuilder set(G chromosomeType, Allele<V> allele);

	/**
	 * Sets a allele, that represents the given value, at a position of the chromosome.
	 *
	 * @param value The value that the allele should be represent.
	 * @param chromosomeType The position at the chromosome array.
	 */
	<V, G extends IGene<V>> IAlleleTemplateBuilder set(G chromosomeType, V value);

	IGeneticDefinition getRoot();

	/**
	 * @return The count of genes.
	 */
	int size();

	/**
	 * @return Builds a allele template out of the data of this builder.
	 */
	IAlleleTemplate build();
}
