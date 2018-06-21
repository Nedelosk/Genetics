package genetics.alleles;

import com.google.common.collect.HashMultimap;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import net.minecraft.util.ResourceLocation;

import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import genetics.api.alleles.AlleleCategorized;
import genetics.api.alleles.IAllele;
import genetics.api.alleles.IAlleleHandler;
import genetics.api.alleles.IAlleleRegistry;
import genetics.api.individual.IChromosomeType;

import genetics.Genetics;
import genetics.plugins.PluginManager;

public class AlleleRegistry implements IAlleleRegistry {

	private static final int ALLELE_ARRAY_SIZE = 2048;

	/* ALLELES */
	private final ForgeRegistry<IAllele> registry;
	private final HashMultimap<IChromosomeType, IAllele> allelesByType = HashMultimap.create();
	private final HashMultimap<IAllele, IChromosomeType> typesByAllele = HashMultimap.create();
	/*
	 * Internal Set of all alleleHandlers, which trigger when an allele or branch is registered
	 */
	private final Set<IAlleleHandler> handlers = new HashSet<>();

	public AlleleRegistry() {
		@SuppressWarnings("unchecked")
		RegistryBuilder<IAllele> builder = new RegistryBuilder()
			.setMaxID(ALLELE_ARRAY_SIZE)
			.setName(new ResourceLocation(Genetics.MOD_ID, "alleles"))
			.setType(IAllele.class);
		//Cast the registry to the class type so we can get the ids of the alleles
		this.registry = (ForgeRegistry<IAllele>) builder.create();
	}

	@Override
	public <V> IAllele registerAllele(String category, String valueName, V value, boolean dominant, IChromosomeType... types) {
		return registerAllele(new AlleleCategorized<>(PluginManager.getCurrentModId(), category, valueName, value, dominant), types);
	}

	@Override
	public IAllele registerAllele(IAllele allele, IChromosomeType... types) {
		if (!registry.containsKey(allele.getRegistryName())) {
			registry.register(allele);
			handlers.forEach(h -> h.onRegisterAllele(allele));
		}
		addValidAlleleTypes(allele, types);
		return allele;
	}

	@Override
	public IAlleleRegistry addValidAlleleTypes(ResourceLocation registryName, IChromosomeType... types) {
		Optional<IAllele> alleleOptional = getAllele(registryName);
		alleleOptional.ifPresent(allele -> addValidAlleleTypes(allele, types));
		return this;
	}

	@Override
	public IAlleleRegistry addValidAlleleTypes(IAllele allele, IChromosomeType... types) {
		handlers.forEach(h -> h.onAddTypes(allele, types));
		for (IChromosomeType chromosomeType : types) {
			if (!chromosomeType.isValid(allele)) {
				//throw new IllegalArgumentException("Allele class (" + allele.getClass() + ") does not match chromosome type (" + chromosomeType.getAlleleClass() + ").");
			}
			allelesByType.put(chromosomeType, allele);
			typesByAllele.put(allele, chromosomeType);
		}
		return this;
	}

	@Override
	public Collection<IChromosomeType> getChromosomeTypes(IAllele allele) {
		return typesByAllele.get(allele);
	}

	@Override
	public Collection<IAllele> getRegisteredAlleles(IChromosomeType type) {
		return allelesByType.get(type);
	}

	@Override
	public Optional<IAllele> getAllele(ResourceLocation location) {
		return Optional.ofNullable(registry.getValue(location));
	}

	@Override
	public boolean isValidAllele(IAllele allele, IChromosomeType type) {
		return type.isValid(allele);
	}

	@Override
	public void registerHandler(IAlleleHandler handler) {
		this.handlers.add(handler);
	}

	@Override
	public Set<IAlleleHandler> getHandlers() {
		return handlers;
	}

	public int getId(IAllele allele) {
		return registry.getID(allele);
	}

	public int getId(ResourceLocation alleleName) {
		return registry.getID(alleleName);
	}

	@Nullable
	public IAllele getAllele(int id) {
		return registry.getValue(id);
	}

	/* BLACKLIST */
	private final ArrayList<String> blacklist = new ArrayList<>();

	@Override
	public void blacklistAllele(String registryName) {
		blacklist.add(registryName);
	}

	@Override
	public Collection<String> getAlleleBlacklist() {
		return Collections.unmodifiableCollection(blacklist);
	}

	@Override
	public boolean isBlacklisted(String registryName) {
		return blacklist.contains(registryName);
	}

}
