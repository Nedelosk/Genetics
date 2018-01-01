package nedelosk.crispr.apiimp;

import com.google.common.collect.HashMultimap;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.Set;

import net.minecraft.util.ResourceLocation;

import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import nedelosk.crispr.Crispr;
import nedelosk.crispr.api.alleles.Allele;
import nedelosk.crispr.api.alleles.IAllele;
import nedelosk.crispr.api.alleles.IAlleleData;
import nedelosk.crispr.api.alleles.IAlleleHandler;
import nedelosk.crispr.api.alleles.IAlleleKey;
import nedelosk.crispr.api.alleles.IAlleleRegistry;

public class AlleleRegistry implements IAlleleRegistry {

	private static final int ALLELE_ARRAY_SIZE = 2048;

	/* ALLELES */
	private final HashMultimap<IAllele<?>, IAlleleKey> keysByAllele = HashMultimap.create();
	private final HashMap<IAlleleKey, IAllele<?>> alleleByKey = new LinkedHashMap<>(ALLELE_ARRAY_SIZE);
	private final ForgeRegistry<IAllele<?>> alleleRegistry;
	/*
	 * Internal Set of all alleleHandlers, which trigger when an allele or branch is registered
	 */
	private final Set<IAlleleHandler> handlers = new HashSet<>();

	@SuppressWarnings("unchecked")
	public AlleleRegistry() {
		RegistryBuilder<IAllele<?>> builder = new RegistryBuilder()
			.setMaxID(2048)
			.setName(new ResourceLocation(Crispr.MOD_ID, "alleles"))
			.setType(IAllele.class);
		//Cast the registry to the class type so we can get the ids of the alleles
		this.alleleRegistry = (ForgeRegistry<IAllele<?>>) builder.create();
	}

	@Override
	public IAlleleRegistry registerAllele(IAlleleData value, String registryName, IAlleleKey... keys) {
		return registerAllele(value, new ResourceLocation(registryName), keys);
	}

	@Override
	public IAlleleRegistry registerAllele(IAlleleData value, ResourceLocation registryName, IAlleleKey... keys) {
		return registerAllele(value.getValue(), value.isDominant(), registryName, keys);
	}

	@Override
	public <V> IAlleleRegistry registerAllele(V value, boolean dominant, String registryName, IAlleleKey... keys) {
		return registerAllele(value, dominant, new ResourceLocation(registryName), keys);
	}

	@Override
	public <V> IAlleleRegistry registerAllele(V value, boolean dominant, ResourceLocation registryName, IAlleleKey... keys) {
		return registerAllele(new Allele<>(value, dominant).setRegistryName(registryName), keys);
	}

	@Override
	public <V> IAlleleRegistry registerAllele(IAllele<V> allele, IAlleleKey... keys) {
		if (!alleleRegistry.containsKey(allele.getRegistryName())) {
			alleleRegistry.register(allele);
			handlers.forEach(h -> h.onRegisterAllele(allele));
		}
		for (IAlleleKey key : keys) {
			keysByAllele.put(allele, key);
			alleleByKey.put(key, allele);
		}
		handlers.forEach(h -> h.onAddKeys(allele, keys));
		return this;
	}

	@Override
	public <V> IAlleleRegistry registerAllele(IAllele<V> allele, Collection<IAlleleKey> keys) {
		return registerAllele(allele, keys.toArray(new IAlleleKey[keys.size()]));
	}

	@Override
	public Optional<IAllele<?>> getAllele(IAlleleKey key) {
		return Optional.ofNullable(alleleByKey.get(key));
	}

	@Override
	public Optional<IAllele<?>> getAllele(String registryName) {
		return getAllele(new ResourceLocation(registryName));
	}

	@Override
	public Optional<IAllele<?>> getAllele(ResourceLocation location) {
		return Optional.ofNullable(alleleRegistry.getValue(location));
	}

	@Override
	public Collection<IAlleleKey> getKeys(IAllele<?> allele) {
		return keysByAllele.get(allele);
	}

	@Override
	public void registerHandler(IAlleleHandler handler) {
		this.handlers.add(handler);
	}

	@Override
	public Set<IAlleleHandler> getHandlers() {
		return handlers;
	}

	public int getId(IAllele<?> allele) {
		return alleleRegistry.getID(allele);
	}

	public int getId(ResourceLocation alleleName) {
		return alleleRegistry.getID(alleleName);
	}

	@Nullable
	public IAllele<?> getAllele(int id) {
		return alleleRegistry.getValue(id);
	}

}
