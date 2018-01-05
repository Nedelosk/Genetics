package genetics.api.alleles;

import javax.annotation.Nullable;

import genetics.api.definition.IOrganismRoot;
import genetics.api.gene.IGeneType;
import genetics.api.gene.IKaryotype;
import genetics.api.individual.IChromosome;
import genetics.api.individual.IGenome;
import genetics.api.individual.IGenomeWrapper;
import genetics.api.individual.IOrganism;

/**
 * Can be used to create {@link IGenome}s, {@link IOrganism}s or {@link IChromosome}s or get a allele.
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

	/**
	 * @return The karyotype that defines the {@link #size()} and which alleles this template can contain.
	 */
	IKaryotype getKaryotype();

	default <I extends IOrganism> I toIndividual(IOrganismRoot<I, IGenomeWrapper> root) {
		return toIndividual(root, null);
	}

	/**
	 * Creates an individual with the help of the genetic root using
	 * {@link IOrganismRoot#templateAsIndividual(IAllele[], IAllele[])}.
	 */
	<I extends IOrganism> I toIndividual(IOrganismRoot<I, IGenomeWrapper> root, @Nullable IAlleleTemplate inactiveTemplate);

	default IGenome toGenome() {
		return toGenome(null);
	}

	/**
	 * Creates a genome with the help of the karyotype using
	 * {@link IKaryotype#templateAsGenome(IAllele[], IAllele[])}.
	 */
	IGenome toGenome(@Nullable IAlleleTemplate inactiveTemplate);

	default IChromosome[] toChromosomes() {
		return toChromosomes(null);
	}

	/**
	 * Creates a chromosome array with the help of the karyotype using
	 * {@link IKaryotype#templateAsChromosomes(IAllele[], IAllele[])}.
	 */
	IChromosome[] toChromosomes(@Nullable IAlleleTemplate inactiveTemplate);
}
