package nedelosk.crispr.gene;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import net.minecraft.client.resources.I18n;

import nedelosk.crispr.api.CrisprAPI;
import nedelosk.crispr.api.alleles.IAllele;
import nedelosk.crispr.api.alleles.IAlleleKey;
import nedelosk.crispr.api.gene.IGene;
import nedelosk.crispr.api.gene.IGeneBuilder;
import nedelosk.crispr.api.gene.IGeneType;
import nedelosk.crispr.api.registry.IAlleleRegistry;

public class Gene implements IGene {
	private final ImmutableMap<IAlleleKey, String> alleleInstances;
	private final ImmutableBiMap<IAllele, IAlleleKey> alleles;
	private final IAllele defaultAllele;
	private final String name;

	public Gene(ImmutableMap<IAlleleKey, String> alleleInstances, IAlleleKey defaultKey, String name) {
		this.name = name;
		this.alleleInstances = alleleInstances;
		IAlleleRegistry alleleRegistry = CrisprAPI.alleleRegistry;
		ImmutableBiMap.Builder<IAllele, IAlleleKey> builder = ImmutableBiMap.builder();
		alleleInstances.forEach((k, v) -> alleleRegistry.getAllele(k).ifPresent(a -> builder.put(a, k)));
		this.alleles = builder.build();
		Optional<IAllele> optional = getAllele(defaultKey);
		if (!optional.isPresent()) {
			throw new RuntimeException();
		}
		this.defaultAllele = optional.get();
	}

	@Override
	public Collection<IAllele> getVariants() {
		return alleles.keySet();
	}

	@Override
	public Collection<IAlleleKey> getKeys() {
		return alleleInstances.keySet();
	}

	@Override
	public boolean isValidAllele(IAllele<?> allele) {
		return alleles.containsKey(allele);
	}

	@Override
	public Optional getValue(IAlleleKey key) {
		Optional<IAllele> allele = getAllele(key);
		return allele.map(IAllele::getValue);
	}

	@Override
	public Optional<IAlleleKey> getKey(IAllele<?> allele) {
		return Optional.ofNullable(alleles.get(allele));
	}

	@Override
	public Optional<IAllele> getAllele(IAlleleKey key) {
		return Optional.ofNullable(alleles.inverse().get(key));
	}

	@Override
	public IAllele<?> getDefaultAllele() {
		return defaultAllele;
	}

	@Override
	public String getShortName() {
		return I18n.format(getUnlocalizedShortName());
	}

	@Override
	public String getUnlocalizedShortName() {
		return "gene." + name + ".short.name";
	}

	@Override
	public String getLocalizedName() {
		return I18n.format(getUnlocalizedName());
	}

	@Override
	public String getUnlocalizedName() {
		return "gene." + name + ".name";
	}

	public String getUnlocalizedName(IAllele<?> allele) {
		return "allele." + alleleInstances.get(alleles.get(allele)) + ".name";
	}

	@Override
	public String getLocalizedName(IAllele<?> allele) {
		return I18n.format(getUnlocalizedName(allele));
	}

	public static class Builder implements IGeneBuilder {
		private final HashMap<IAlleleKey, String> alleleInstances = new HashMap<>();
		private final Set<IGeneType> types = new HashSet<>();
		private final String name;
		@Nullable
		private IAlleleKey defaultKey;

		public Builder(String name) {
			this.name = name;
		}

		@Override
		public IGeneBuilder addAllele(IAlleleKey key, String unlocalizedName) {
			this.alleleInstances.put(key, unlocalizedName);
			return this;
		}

		@Override
		public IGeneBuilder setDefaultAllele(IAlleleKey key) {
			this.defaultKey = key;
			return this;
		}

		@Override
		public IGeneBuilder addType(IGeneType type) {
			this.types.add(type);
			return this;
		}

		public Set<IGeneType> getTypes() {
			return types;
		}

		public Gene createGene() {
			Preconditions.checkNotNull(defaultKey);
			return new Gene(ImmutableMap.copyOf(alleleInstances), defaultKey, name);
		}
	}
}
