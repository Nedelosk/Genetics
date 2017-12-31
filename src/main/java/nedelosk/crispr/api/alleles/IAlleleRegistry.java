package nedelosk.crispr.api.alleles;

import java.util.Collection;
import java.util.Optional;
import java.util.function.BiFunction;

import net.minecraft.util.ResourceLocation;

public interface IAlleleRegistry {

	<V> IAlleleCategory<V> createCategory(String uid, BiFunction<V, Boolean, IAllele<V>> alleleFactory);

	<V> IAlleleCategory<V> addCategory(IAlleleCategory<V> category);

	Optional<IAlleleCategory> getCategory(String uid);

	IAlleleRegistry registerAllele(IAlleleData value, String registryName, IAlleleKey... keys);

	IAlleleRegistry registerAllele(IAlleleData value, ResourceLocation registryName, IAlleleKey... keys);

	<V> IAlleleRegistry registerAllele(V value, boolean dominant, String registryName, IAlleleKey... keys);

	<V> IAlleleRegistry registerAllele(V value, boolean dominant, ResourceLocation registryName, IAlleleKey... keys);

	<V> IAlleleRegistry registerAllele(IAllele<V> allele, IAlleleKey... keys);

	<V> IAlleleRegistry registerAllele(IAllele<V> allele, Collection<IAlleleKey> keys);

	Optional<IAllele<?>> getAllele(IAlleleKey key);

	Optional<IAllele<?>> getAllele(String registryName);

	Optional<IAllele<?>> getAllele(ResourceLocation registryName);

	void registerHandler(IAlleleHandler handler);

	Collection<IAlleleHandler> getHandlers();
}
