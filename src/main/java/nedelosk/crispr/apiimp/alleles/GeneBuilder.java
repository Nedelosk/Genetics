package nedelosk.crispr.apiimp.alleles;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

import nedelosk.crispr.api.alleles.IAlleleKey;
import nedelosk.crispr.api.alleles.IAlleleNameFormatter;
import nedelosk.crispr.api.gene.IGeneBuilder;
import nedelosk.crispr.api.gene.IGeneType;
import nedelosk.crispr.apiimp.gene.Gene;

public class GeneBuilder<V> implements IGeneBuilder<V> {
	private final Class<? extends V> valueClass;
	private final Set<IAlleleKey> keys = new HashSet<>();
	private final Set<IGeneType> types = new HashSet<>();
	private final String name;
	@Nullable
	private IAlleleKey defaultKey;
	private IAlleleNameFormatter<V> nameFormatter;

	public GeneBuilder(Class<? extends V> valueClass, String name) {
		this.valueClass = valueClass;
		this.name = name;
	}

	@Override
	public IGeneBuilder<V> addAllele(IAlleleKey key) {
		this.keys.add(key);
		return this;
	}

	@Override
	public IGeneBuilder<V> setNameFormatter(IAlleleNameFormatter<V> formatter) {
		this.nameFormatter = formatter;
		return this;
	}

	@Override
	public IGeneBuilder<V> addType(IGeneType type) {
		this.types.add(type);
		return this;
	}

	@Override
	public IGeneBuilder<V> setDefaultAllele(IAlleleKey key) {
		this.defaultKey = key;
		return this;
	}

	public Set<IGeneType> getTypes() {
		return types;
	}

	public Gene createGene() {
		Preconditions.checkNotNull(defaultKey);
		return new Gene(ImmutableSet.copyOf(keys), valueClass, defaultKey, name, nameFormatter);
	}
}
