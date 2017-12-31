package nedelosk.crispr.apiimp;

import com.google.common.collect.HashMultimap;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;

import net.minecraft.util.ResourceLocation;

import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import nedelosk.crispr.Crispr;
import nedelosk.crispr.api.alleles.Allele;
import nedelosk.crispr.api.alleles.IAllele;
import nedelosk.crispr.api.alleles.IAlleleCategory;
import nedelosk.crispr.api.alleles.IAlleleData;
import nedelosk.crispr.api.alleles.IAlleleHandler;
import nedelosk.crispr.api.alleles.IAlleleKey;
import nedelosk.crispr.api.alleles.IAlleleRegistry;
import nedelosk.crispr.apiimp.alleles.AlleleCategory;

public class AlleleRegistry implements IAlleleRegistry {

	private final HashMultimap<IAllele<?>, IAlleleKey> keysByAllele = HashMultimap.create();
	private final HashMap<IAlleleKey, IAllele<?>> alleleByKey = new HashMap<>();
	private final Set<IAlleleHandler> handlers = new HashSet<>();
	private final HashMap<String, IAlleleCategory<?>> categories = new HashMap<>();
	private final ForgeRegistry<IAllele<?>> alleleRegistry;

	@SuppressWarnings("unchecked")
	public AlleleRegistry() {
		this.alleleRegistry = (ForgeRegistry<IAllele<?>>) new RegistryBuilder().setMaxID(2048).setName(new ResourceLocation(Crispr.MOD_ID, "alleles")).setType(IAllele.class).create();
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
		return registerAllele(new Allele<>(value, dominant, "allele." + registryName.getResourceDomain() + "." + registryName.getResourcePath()).setRegistryName(registryName), keys);
	}

	@Override
	public <V> IAlleleRegistry registerAllele(IAllele<V> allele, Collection<IAlleleKey> keys) {
		return registerAllele(allele, keys.toArray(new IAlleleKey[keys.size()]));
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
	public <V> IAlleleCategory<V> createCategory(String uid, BiFunction<V, Boolean, IAllele<V>> alleleFactory) {
		AlleleCategory<V> category = new AlleleCategory<>(uid, alleleFactory);
		categories.put(uid, category);
		return category;
	}

	@Override
	public <V> IAlleleCategory<V> addCategory(IAlleleCategory<V> category) {
		categories.put(category.getUID(), category);
		return category;
	}

	@Override
	public Optional<IAlleleCategory> getCategory(String uid) {
		return Optional.ofNullable(categories.get(uid));
	}

	@Override
	public void registerHandler(IAlleleHandler handler) {
		this.handlers.add(handler);
	}

	@Override
	public Set<IAlleleHandler> getHandlers() {
		return handlers;
	}

	@Override
	public Optional<IAllele<?>> getAllele(IAlleleKey key) {
		return Optional.ofNullable(alleleByKey.get(key));
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

	@Override
	public Optional<IAllele<?>> getAllele(ResourceLocation location) {
		return Optional.ofNullable(alleleRegistry.getValue(location));
	}

	@Override
	public Optional<IAllele<?>> getAllele(String registryName) {
		return getAllele(new ResourceLocation(registryName));
	}

	public void registerCategoryAlleles() {
		categories.values().forEach(IAlleleCategory::registerAlleles);
	}

}
