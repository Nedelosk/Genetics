package genetics.gene;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableSet;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import net.minecraft.client.resources.I18n;

import genetics.api.alleles.IAllele;
import genetics.api.alleles.IAlleleKey;
import genetics.api.alleles.IAlleleRegistry;
import genetics.api.alleles.IAlleleValue;
import genetics.api.gene.IChromosomeType;
import genetics.api.gene.IGene;
import genetics.api.gene.IGeneBuilder;

import genetics.ApiInstance;

public class Gene implements IGene {
	private final ImmutableBiMap<IAllele, IAlleleKey> alleles;
	private final IAllele defaultAllele;
	private final String name;

	private Gene(ImmutableSet<IAlleleKey> alleleInstances, IAlleleKey defaultKey, String name) {
		this.name = name;
		IAlleleRegistry alleleRegistry = ApiInstance.INSTANCE.getAlleleRegistry();
		ImmutableBiMap.Builder<IAllele, IAlleleKey> builder = ImmutableBiMap.builder();
		alleleInstances.forEach((k) -> alleleRegistry.getAllele(k).ifPresent(a -> builder.put(a, k)));
		this.alleles = builder.build();
		Optional<IAllele> optional = getAllele(defaultKey);
		if (!optional.isPresent()) {
			throw new IllegalStateException("Failed to create gene " + name + '.');
		}
		this.defaultAllele = optional.get();
	}

	@Override
	public Collection<IAllele> getVariants() {
		return alleles.keySet();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <V> Collection<V> getValues(Class<? extends V> valueClass) {
		return alleles.keySet().stream()
			.filter(allele -> allele instanceof IAlleleValue)
			.map(allele -> {
				Object value = ((IAlleleValue) allele).getValue();
				if (!valueClass.isInstance(value)) {
					return null;
				}
				return (V) value;
			})
			.filter(Objects::nonNull)
			.collect(Collectors.toList());
	}

	@Override
	public Collection<IAlleleKey> getKeys() {
		return alleles.values();
	}

	@Override
	public boolean isValidAllele(IAllele allele) {
		return alleles.containsKey(allele);
	}

	@Override
	public Optional<IAlleleKey> getKey(IAllele allele) {
		return Optional.ofNullable(alleles.get(allele));
	}

	@Override
	public Optional<IAllele> getAllele(IAlleleKey key) {
		return Optional.ofNullable(alleles.inverse().get(key));
	}

	@Override
	public IAllele getDefaultAllele() {
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

	@Override
	public String toString() {
		return name;
	}

	public static class Builder implements IGeneBuilder {
		private final Set<IAlleleKey> keys = new HashSet<>();
		private final Set<IChromosomeType> types = new HashSet<>();
		private final String name;
		@Nullable
		private IAlleleKey defaultKey;

		public Builder(String name) {
			this.name = name;
		}

		@Override
		public IGeneBuilder addAlleles(IAlleleKey... keys) {
			this.keys.addAll(Arrays.asList(keys));
			return this;
		}

		@Override
		public IGeneBuilder setDefaultAllele(IAlleleKey key) {
			this.defaultKey = key;
			this.keys.add(key);
			return this;
		}

		@Override
		public IGeneBuilder addType(IChromosomeType type) {
			this.types.add(type);
			return this;
		}

		public Set<IChromosomeType> getTypes() {
			return types;
		}

		public Gene createGene() {
			Preconditions.checkNotNull(defaultKey);
			return new Gene(ImmutableSet.copyOf(keys), defaultKey, name);
		}
	}
}
