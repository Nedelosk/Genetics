package nedelosk.genetics.api.registry;

import java.util.Collection;
import java.util.Optional;

import net.minecraft.util.ResourceLocation;

import nedelosk.genetics.api.alleles.IAllele;
import nedelosk.genetics.api.alleles.IAlleleData;
import nedelosk.genetics.api.alleles.IAlleleHandler;
import nedelosk.genetics.api.alleles.IAlleleKey;

public interface IAlleleRegistry {

	IAlleleRegistry registerAllele(IAlleleData value, String registryName, IAlleleKey... keys);

	IAlleleRegistry registerAllele(IAlleleData value, ResourceLocation registryName, IAlleleKey... keys);

	<V> IAlleleRegistry registerAllele(V value, boolean dominant, String registryName, IAlleleKey... keys);

	<V> IAlleleRegistry registerAllele(V value, boolean dominant, ResourceLocation registryName, IAlleleKey... keys);

	<V> IAlleleRegistry registerAllele(IAllele<V> allele, IAlleleKey... keys);

	<V> IAlleleRegistry registerAllele(IAllele<V> allele, Collection<IAlleleKey> keys);

	Optional<IAllele<?>> getAllele(IAlleleKey key);

	Optional<IAllele<?>> getAllele(String registryName);

	Optional<IAllele<?>> getAllele(ResourceLocation registryName);

	Collection<IAlleleKey> getKeys(IAllele<?> allele);

	void registerHandler(IAlleleHandler handler);

	Collection<IAlleleHandler> getHandlers();
}
