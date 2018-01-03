package genetics.registry;

import com.google.common.collect.HashMultimap;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.Set;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import genetics.api.alleles.Allele;
import genetics.api.alleles.IAllele;
import genetics.api.alleles.IAlleleHandler;
import genetics.api.alleles.IAlleleKey;
import genetics.api.registry.IAlleleRegistry;

import genetics.Genetics;

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
			.setMaxID(ALLELE_ARRAY_SIZE)
			.setName(new ResourceLocation(Genetics.MOD_ID, "alleles"))
			.setType(IAllele.class);
		//Cast the registry to the class type so we can get the ids of the alleles
		this.alleleRegistry = (ForgeRegistry<IAllele<?>>) builder.create();
	}

	@Override
	public <V> IAlleleRegistry registerAllele(V value, boolean dominant, IAlleleKey... keys) {
		String alleleName = value instanceof IStringSerializable ? ((IStringSerializable) value).getName() : value.toString();
		return registerAllele(new Allele<>(value, dominant).setRegistryName(Genetics.MOD_ID, alleleName + "_" + dominant), keys);
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
