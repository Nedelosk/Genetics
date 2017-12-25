package nedelosk.crispr.api.alleles;

import nedelosk.crispr.api.IGeneticFactory;
import nedelosk.crispr.api.gene.IGeneKey;
import nedelosk.crispr.api.gene.IKaryotype;

/**
 * Can be used to create allele templates.
 * <p>
 * You can get an instance of this from the species root with
 * {@link IGeneticFactory#createTemplate(IKaryotype)} or {@link IGeneticFactory#createTemplate(IKaryotype, Allele[])}.
 */
public interface IAlleleTemplateBuilder {

	/**
	 * Sets a allele at a position of the chromosome.
	 *
	 * @param allele The allele that should be set at the position.
	 * @param key    The position at the chromosome array.
	 */
	<V, K extends IGeneKey> IAlleleTemplateBuilder set(K key, Allele<V> allele);

	/**
	 * Sets a allele, that represents the given value, at a position of the chromosome.
	 *
	 * @param value The value that the allele should be represent.
	 * @param key   The position at the chromosome array.
	 */
	<V, K extends IGeneKey> IAlleleTemplateBuilder set(K key, V value);

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
