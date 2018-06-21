package genetics.api.alleles;

import java.util.Collection;
import java.util.Optional;

import net.minecraft.util.ResourceLocation;

import genetics.api.IGeneticApiInstance;
import genetics.api.IGeneticPlugin;
import genetics.api.individual.IChromosomeType;

/**
 * The {@link IAlleleRegistry} offers several functions for registering and retrieving alleles.
 * <p>
 * The IAlleleRegistry instance is passed to your genetic plugin in {@link IGeneticPlugin#registerAlleles(IAlleleRegistry)}}.
 * Later you can get the instance from {@link IGeneticApiInstance#getAlleleRegistry()}.
 */
public interface IAlleleRegistry {
	/**
	 * Creates a allele with the data that the {@link IAlleleData} contains.
	 */
	default IAllele registerAllele(IAlleleData value, IChromosomeType... types) {
		return registerAllele(value.getCategory(), value.getName(), value.getValue(), value.isDominant(), types);
	}

	/**
	 * Creates and registers an allele that contains the given value and has the given dominant state if no allele with
	 * the value and the given dominant state exists, otherwise it adds the types to the existing {@link IAllele}.
	 *
	 * @param category
	 * @param valueName
	 * @param value     the value of the allele
	 * @param dominant  if true the allele is dominant, otherwise the allele is recessive.
	 * @param types      chromosome types for this allele.
	 */
	<V> IAllele registerAllele(String category, String valueName, V value, boolean dominant, IChromosomeType... types);

	/**
	 * Registers an allele.
	 *
	 * @param allele IAllele to register.
	 * @param types   allele keys for this allele.
	 */
	IAllele registerAllele(IAllele allele, IChromosomeType... types);

	/**
	 * Add more valid chromosome types for an allele.
	 * Used by addons that create new chromosome types beyond bees, trees, and butterflies.
	 */
	default IAlleleRegistry addValidAlleleTypes(String registryName, IChromosomeType... types) {
		return addValidAlleleTypes(new ResourceLocation(registryName), types);
	}

	/**
	 * Add more valid chromosome types for an allele.
	 * Used by addons that create new chromosome types beyond bees, trees, and butterflies.
	 */
	IAlleleRegistry addValidAlleleTypes(ResourceLocation registryName, IChromosomeType... types);

	/**
	 * Add more valid chromosome types for an allele.
	 * Used by addons that create new chromosome types beyond bees, trees, and butterflies.
	 */
	IAlleleRegistry addValidAlleleTypes(IAllele allele, IChromosomeType... types);

	/**
	 * Gets an allele
	 *
	 * @param registryName The registry name of the allele to retrieve as a {@link String}.
	 * @return A optional that contains the IAllele if found, a empty optional otherwise.
	 */
	default Optional<IAllele> getAllele(String registryName) {
		return getAllele(new ResourceLocation(registryName));
	}

	/**
	 * Gets an allele
	 *
	 * @param registryName The registry name of the allele to retrieve as a {@link ResourceLocation}.
	 * @return A optional that contains the IAllele if found, a empty optional otherwise.
	 */
	Optional<IAllele> getAllele(ResourceLocation registryName);

	/**
	 * @return unmodifiable collection of all the known chromosome types.
	 */
	Collection<IChromosomeType> getChromosomeTypes(IAllele allele);

	/**
	 * @return unmodifiable collection of all the known allele variations for the given chromosome type.
	 */
	Collection<IAllele> getRegisteredAlleles(IChromosomeType type);

	/**
	 * Returns true if the given allele is a valid allele for the given chromosome type.
	 *
	 * @param allele
	 * @param type
	 * @return
	 */
	boolean isValidAllele(IAllele allele, IChromosomeType type);

	/* ALLELE HANDLERS */
	/**
	 * Registers a new IAlleleHandler
	 *
	 * @param handler IAlleleHandler to register.
	 */
	void registerHandler(IAlleleHandler handler);

	/**
	 * @return all handlers that were registered.
	 */
	Collection<IAlleleHandler> getHandlers();

	/* BLACKLIST */
	/**
	 * Blacklist an allele identified by its UID from mutation.
	 *
	 * @param registryName UID of the allele to blacklist.
	 */
	void blacklistAllele(String registryName);

	default void blacklistAllele(ResourceLocation registryName){
		blacklistAllele(registryName.toString());
	}

	/**
	 * @return Current blacklisted alleles.
	 */
	Collection<String> getAlleleBlacklist();

	/**
	 * @param registryName UID of the species to vet.
	 * @return true if the allele is blacklisted.
	 */
	boolean isBlacklisted(String registryName);

	default boolean isBlacklisted(ResourceLocation registryName){
		return isBlacklisted(registryName.toString());
	}
}
