package genetics.api.registry;

import java.util.Collection;
import java.util.Optional;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;

import genetics.api.GeneticsAPI;
import genetics.api.IGeneticPlugin;
import genetics.api.alleles.IAllele;
import genetics.api.alleles.IAlleleData;
import genetics.api.alleles.IAlleleHandler;
import genetics.api.alleles.IAlleleKey;

/**
 * The {@link IAlleleRegistry} offers several functions for registering and retrieving alleles.
 * <p>
 * The IAlleleRegistry instance is passed to your genetic plugin in {@link IGeneticPlugin#registerAlleles(IAlleleRegistry)}}.
 * Later you can get the instance from {@link GeneticsAPI#alleleRegistry}.
 */
public interface IAlleleRegistry {

	/**
	 * Creates a allele with the data that the {@link IAlleleData} contains.
	 */
	default IAlleleRegistry registerAllele(IAlleleData value) {
		return registerAllele(value.getValue(), value.isDominant(), value.getKey());
	}

	/**
	 * Creates and registers an allele that contains the given value and has the given dominant state if no allele with
	 * the value and the given dominant state exists, otherwise it adds the keys to the existing {@link IAllele}.
	 *
	 * The registry name of the allele will be created out of the given value and the given dominant state:
	 * "value" + "_" + "dominant"
	 * You can control the string that is used for the value by implementing {@link Object#toString()} or
	 * {@link IStringSerializable#getName()}.
	 *
	 * @param value    the value of the allele
	 * @param dominant if true the allele is dominant, otherwise the allele is recessive.
	 * @param keys     allele keys for this allele.
	 */
	<V> IAlleleRegistry registerAllele(V value, boolean dominant, IAlleleKey... keys);

	/**
	 * Registers an allele.
	 *
	 * @param allele IAllele to register.
	 * @param keys   allele keys for this allele.
	 * @param <V>    the type that the value of the allele has
	 */
	<V> IAlleleRegistry registerAllele(IAllele<V> allele, IAlleleKey... keys);

	Optional<IAllele<?>> getAllele(IAlleleKey key);

	/**
	 * Gets an allele
	 *
	 * @param registryName The registry name of the allele to retrieve.
	 * @return A optional that contains the IAllele if found, a empty optional otherwise.
	 */
	Optional<IAllele<?>> getAllele(String registryName);

	Optional<IAllele<?>> getAllele(ResourceLocation registryName);

	/**
	 * Gets all keys for the given allele.
	 *
	 * @return all keys that were registered for the given keys.
	 */
	Collection<IAlleleKey> getKeys(IAllele<?> allele);

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
