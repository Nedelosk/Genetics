package genetics.gene;

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

import genetics.api.alleles.IAllele;
import genetics.api.alleles.IAlleleKey;
import genetics.api.alleles.IAlleleRegistry;
import genetics.api.gene.IChromosomeType;
import genetics.api.gene.IGene;
import genetics.api.gene.IGeneBuilder;

import genetics.ApiInstance;

public class Gene implements IGene {
	private final ImmutableMap<IAlleleKey, String> alleleInstances;
	private final ImmutableBiMap<IAllele, IAlleleKey> alleles;
	private final IAllele defaultAllele;
	private final String name;

	private Gene(ImmutableMap<IAlleleKey, String> alleleInstances, IAlleleKey defaultKey, String name) {
		this.name = name;
		this.alleleInstances = alleleInstances;
		IAlleleRegistry alleleRegistry = ApiInstance.INSTANCE.getAlleleRegistry();
		ImmutableBiMap.Builder<IAllele, IAlleleKey> builder = ImmutableBiMap.builder();
		alleleInstances.forEach((k, v) -> alleleRegistry.getAllele(k).ifPresent(a -> builder.put(a, k)));
		this.alleles = builder.build();
		Optional<IAllele> optional = getAllele(defaultKey);
		if (!optional.isPresent()) {
			throw new IllegalStateException();
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
	public Optional<IAlleleKey> getKey(IAllele<?> allele) {
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

	public String getUnlocalizedName(IAllele<?> allele) {
		return "allele." + alleleInstances.get(alleles.get(allele)) + ".name";
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public String getLocalizedName(IAllele<?> allele) {
		return I18n.format(getUnlocalizedName(allele));
	}

	public static class Builder implements IGeneBuilder {
		private final HashMap<IAlleleKey, String> alleleInstances = new HashMap<>();
		private final Set<IChromosomeType> types = new HashSet<>();
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
		public IGeneBuilder addType(IChromosomeType type) {
			this.types.add(type);
			return this;
		}

		public Set<IChromosomeType> getTypes() {
			return types;
		}

		public Gene createGene() {
			Preconditions.checkNotNull(defaultKey);
			return new Gene(ImmutableMap.copyOf(alleleInstances), defaultKey, name);
		}
	}
}
