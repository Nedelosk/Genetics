package genetics.api.gene;

import java.util.Collection;
import java.util.Optional;

import genetics.api.IGeneticPlugin;
import genetics.api.alleles.IAllele;
import genetics.api.alleles.IAlleleKey;
import genetics.api.individual.IChromosome;
import genetics.api.individual.IOrganism;
import genetics.api.registry.IGeneticRegistry;
import genetics.api.registry.IGeneticSystem;

/**
 * The IGene contains every {@link IAllele} an the associated {@link IAlleleKey} of every {@link IAlleleKey} that was
 * added to the {@link IGeneBuilder} at {@link IGeneticPlugin#register(IGeneticRegistry)}.
 * <p>
 * You have to create a IGeneBuilder at {@link IGeneticPlugin#register(IGeneticRegistry)} with
 * {@link IGeneticRegistry#addGene(String)}. Later after {@link IGeneticPlugin#register(IGeneticRegistry)} the builder
 * will be automatically build to a IGene and you can get the instance of the IGene with
 * {@link IGeneticSystem#getGene(IGeneType)}
 */
public interface IGene {
	/**
	 * All alleles that can be used at a {@link IChromosome} with this type.
	 */
	Collection<IAllele> getVariants();

	/**
	 * All keys that were registered at the {@link IGeneBuilder} with {@link IGeneBuilder#addAllele(IAlleleKey, String)}.
	 */
	Collection<IAlleleKey> getKeys();

	/**
	 * Checks if the allele is a valid variant of this gene.
	 */
	boolean isValidAllele(IAllele<?> allele);

	/**
	 * The key with that the allele is associated at this gene.
	 */
	Optional<IAlleleKey> getKey(IAllele<?> allele);

	/**
	 * The allele with that the key is associated at this gene.
	 */
	Optional<IAllele> getAllele(IAlleleKey key);

	/**
	 * The allele that is used for the default template of the {@link IOrganism}.
	 */
	IAllele<?> getDefaultAllele();

	/**
	 * @return A short localized name for this gene.
	 */
	String getShortName();

	/**
	 * @return The unlocalized name for {@link #getShortName()}.
	 */
	String getUnlocalizedShortName();

	/**
	 * @return A localized name for this gene.
	 */
	String getLocalizedName();

	/**
	 * @return The unlocalized name for {@link #getLocalizedName()}.
	 */
	String getUnlocalizedName();

	/**
	 * The unlocalized name for given the allele if this gene contains a key of the allele that is associated with a name.
	 */
	String getUnlocalizedName(IAllele<?> allele);

	/**
	 * The localized name for given the allele if this gene contains a key of the allele that is associated with a name.
	 */
	String getLocalizedName(IAllele<?> allele);
}
