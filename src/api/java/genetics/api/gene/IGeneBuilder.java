package genetics.api.gene;

import genetics.api.IGeneticPlugin;
import genetics.api.alleles.IAlleleKey;
import genetics.api.registry.IGeneticRegistry;
import genetics.api.registry.IGeneticSystem;

/**
 * A builder to create a IGene.
 *
 * After every {@link IGeneticPlugin} received {@link IGeneticPlugin#register(IGeneticRegistry)} all
 * {@link IGeneBuilder}s will be build automatically to {@link IGene}s. You can the instance of you gene from
 * {@link IGeneticSystem#getGene(IGeneType)} after it was created.
 * <p>
 * You can create an instance of this with {@link IGeneticRegistry#addGene(String)}.
 */
public interface IGeneBuilder {

	/**
	 * Add a the key of an allele and the unlocalized name that should be used in every case the name of the allele is
	 * required.
	 */
	IGeneBuilder addAllele(IAlleleKey key, String unlocalizedName);

	/**
	 * Sets the key of the allele that is used in the default template at the position of the type.
	 */
	IGeneBuilder setDefaultAllele(IAlleleKey key);

	/**
	 * Adds a type to the builder.
	 */
	IGeneBuilder addType(IGeneType type);
}
