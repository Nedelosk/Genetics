package nedelosk.crispr.apiimp.alleles;

import com.google.common.base.Preconditions;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

import nedelosk.crispr.api.alleles.Allele;
import nedelosk.crispr.api.alleles.IAlleleKey;
import nedelosk.crispr.api.alleles.IAlleleRegistry;
import nedelosk.crispr.api.gene.IGeneKey;
import nedelosk.crispr.apiimp.gene.Gene;

public class AlleleRegistry<V> implements IAlleleRegistry<V> {
	private final Class<? extends V> valueClass;
	private final Set<Allele<V>> alleles = new HashSet<>();
	private final Set<IGeneKey<V>> keys = new HashSet<>();
	private final String name;
	@Nullable
	private IAlleleKey defaultKey;

	public AlleleRegistry(Class<? extends V> valueClass, String name) {
		this.valueClass = valueClass;
		this.name = name;
	}

	@Override
	public IAlleleRegistry<V> addAllele(IAlleleKey key, String unlocalizedName, V value, boolean dominant) {
		alleles.add(new Allele<>(key, dominant, unlocalizedName, value));
		return this;
	}

	@Override
	public IAlleleRegistry<V> addKey(IGeneKey<V> key) {
		this.keys.add(key);
		return this;
	}

	@Override
	public IAlleleRegistry<V> setDefaultAllele(IAlleleKey key) {
		this.defaultKey = key;
		return this;
	}

	public Set<IGeneKey<V>> getKeys() {
		return keys;
	}

	public Gene<V> createGene() {
		Preconditions.checkNotNull(defaultKey);
		return new Gene<>(alleles, valueClass, defaultKey, name);
	}
}
