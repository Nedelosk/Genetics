package nedelosk.crispr.api.alleles;

import javax.annotation.Nullable;

import nedelosk.crispr.api.IGeneticTransformer;
import nedelosk.crispr.api.gene.IGeneType;
import nedelosk.crispr.api.gene.IKaryotype;
import nedelosk.crispr.api.individual.IChromosome;
import nedelosk.crispr.api.individual.IGenome;
import nedelosk.crispr.api.individual.IIndividual;

/**
 * Can be used to create {@link IGenome}s, {@link IIndividual}s or {@link IChromosome}s or get a allele.
 */
public interface IAlleleTemplate {

	/**
	 * @return The allele at the position of the chromosomeType at the allele array.
	 */
	<V> IAllele<V> get(IGeneType type);

	/**
	 * @return A copy of the allele array.
	 */
	IAllele[] alleles();

	/**
	 * @return The size of the allele array.
	 */
	int size();

	/**
	 * @return A copy of this template with a copied allele array.
	 */
	IAlleleTemplate copy();

	/**
	 * Creates a new builder with the alleles that are contained in this template.
	 */
	IAlleleTemplateBuilder createBuilder();

	IKaryotype getKaryotype();

	/**
	 * Creates an individual with the help of the species root using
	 * {@link IGeneticTransformer#templateAsIndividual(IAllele[], IAllele[])}.
	 */
	<I extends IIndividual> I toIndividual(IGeneticTransformer<I> transformer, @Nullable IAlleleTemplate inactiveTemplate);

	/**
	 * Creates a genome with the help of the species root using
	 * {@link IGeneticTransformer#templateAsGenome(IAllele[], IAllele[])}.
	 */
	IGenome toGenome(IGeneticTransformer transformer, @Nullable IAlleleTemplate inactiveTemplate);

	/**
	 * Creates a chromosome array with the help of the species root using
	 * {@link IGeneticTransformer#templateAsChromosomes(IAllele[], IAllele[])}.
	 */
	IChromosome[] toChromosomes(IGeneticTransformer transformer, @Nullable IAlleleTemplate inactiveTemplate);
}
