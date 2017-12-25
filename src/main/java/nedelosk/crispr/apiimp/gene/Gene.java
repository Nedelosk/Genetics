package nedelosk.crispr.apiimp.gene;

import com.google.common.collect.ImmutableMap;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import net.minecraft.client.resources.I18n;

import nedelosk.crispr.api.alleles.Allele;
import nedelosk.crispr.api.alleles.IAlleleKey;
import nedelosk.crispr.api.gene.IGene;

public class Gene<V> implements IGene<V> {
	private final Map<V, Allele<V>> alleleByValue;
	private final Map<IAlleleKey, Allele<V>> alleleByKey;
	private final Class<? extends V> valueClass;
	private final Allele<V> defaultAllele;
	private final String name;

	public Gene(Set<Allele<V>> alleles, Class<? extends V> valueClass, IAlleleKey defaultKey, String name) {
		this.valueClass = valueClass;
		this.name = name;
		ImmutableMap.Builder<V, Allele<V>> builder = ImmutableMap.builder();
		alleles.forEach(a -> builder.put(a.get(), a));
		this.alleleByValue = builder.build();
		ImmutableMap.Builder<IAlleleKey, Allele<V>> keyBuilder = ImmutableMap.builder();
		alleles.forEach(a -> keyBuilder.put(a.key(), a));
		this.alleleByKey = keyBuilder.build();
		this.defaultAllele = alleleByKey.get(defaultKey);
	}

	@Override
	public Collection<Allele<V>> getValidAlleles() {
		return alleleByValue.values();
	}

	@Override
	public boolean isValidAllele(Allele<V> allele) {
		Optional<Allele<V>> optional = getAllele(allele.get());
		return optional.isPresent() && allele.equals(optional.get());
	}

	@Override
	public Optional<Allele<V>> getAllele(IAlleleKey key) {
		return Optional.ofNullable(alleleByKey.get(key));
	}

	@Override
	public Optional<V> getValue(IAlleleKey key) {
		Optional<Allele<V>> allele = getAllele(key);
		return allele.map(Allele::get);
	}

	@Override
	public Collection<V> getValidValues() {
		return alleleByValue.keySet();
	}

	@Override
	public Optional<Allele<V>> getAllele(V value) {
		return Optional.ofNullable(alleleByValue.get(value));
	}

	@Override
	public Class<? extends V> getValueClass() {
		return valueClass;
	}

	@Override
	public Allele<V> getDefaultAllele() {
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
}
