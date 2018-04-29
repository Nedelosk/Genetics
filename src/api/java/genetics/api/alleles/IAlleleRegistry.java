package genetics.api.alleles;

import java.util.Collection;
import java.util.Optional;

import net.minecraft.util.ResourceLocation;

import genetics.api.IGeneticApiInstance;
import genetics.api.IGeneticPlugin;

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
	default IAlleleRegistry registerAllele(IAlleleData value) {
		return registerAllele(value.getCategory(), value.getName(), value.getValue(), value.isDominant(), value.getKey());
	}

	/**
	 * Creates and registers an allele that contains the given value and has the given dominant state if no allele with
	 * the value and the given dominant state exists, otherwise it adds the keys to the existing {@link IAllele}.
	 *
	 * @param category
	 * @param valueName
	 * @param value     the value of the allele
	 * @param dominant  if true the allele is dominant, otherwise the allele is recessive.
	 * @param keys      allele keys for this allele.
	 */
	<V> IAlleleRegistry registerAllele(String category, String valueName, V value, boolean dominant, IAlleleKey... keys);

	/**
	 * Registers an allele.
	 *
	 * @param allele IAllele to register.
	 * @param keys   allele keys for this allele.
	 */
	IAlleleRegistry registerAllele(IAllele allele, IAlleleKey... keys);

	default IAlleleRegistry addValidAlleleKeys(String registryName, IAlleleKey... keys) {
		return addValidAlleleKeys(new ResourceLocation(registryName), keys);
	}

	IAlleleRegistry addValidAlleleKeys(ResourceLocation registryName, IAlleleKey... keys);

	/**
	 * Returns the allele that is associated with the given key.
	 *
	 * @param key the key whose associated allele is to be returned
	 * @return an {@code Optional} describing the allele which is associated with the given key.
	 */
	Optional<IAllele> getAllele(IAlleleKey key);

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
	 * Gets all keys for the given allele.
	 *
	 * @return all keys that were registered for the given keys.
	 */
	Collection<IAlleleKey> getKeys(IAllele allele);

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
}
