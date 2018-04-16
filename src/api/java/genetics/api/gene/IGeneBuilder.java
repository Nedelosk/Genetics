package genetics.api.gene;

import genetics.api.IGeneticPlugin;
import genetics.api.alleles.IAlleleKey;

/**
 * A builder to create a IGene.
 * <p>
 * After every {@link IGeneticPlugin} received {@link IGeneticPlugin#registerGenes(IGeneFactory)} all
 * {@link IGeneBuilder}s will be build automatically to {@link IGene}s. You can the instance of you gene from
 * {@link IGeneRegistry#getGene(IChromosomeType)} after it was created.
 * <p>
 * You can create an instance of this with {@link IGeneFactory#addGene(String)}.
 */
public interface IGeneBuilder {

	/**
	 * Add a the key of an allele.
	 */
	IGeneBuilder addAlleles(IAlleleKey... keys);

	/**
	 * Sets the key of the allele that is used in the default template at the position of the type.
	 */
	IGeneBuilder setDefaultAllele(IAlleleKey key);

	/**
	 * Adds a type to the builder.
	 */
	IGeneBuilder addType(IChromosomeType type);
}
