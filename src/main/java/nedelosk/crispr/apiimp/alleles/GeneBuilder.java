package nedelosk.crispr.apiimp.alleles;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import nedelosk.crispr.api.alleles.IAlleleKey;
import nedelosk.crispr.api.gene.IGeneBuilder;
import nedelosk.crispr.api.gene.IGeneType;
import nedelosk.crispr.apiimp.gene.Gene;

public class GeneBuilder implements IGeneBuilder {
	private final HashMap<IAlleleKey, String> alleleInstances = new HashMap<>();
	private final Set<IGeneType> types = new HashSet<>();
	private final String name;
	@Nullable
	private IAlleleKey defaultKey;

	public GeneBuilder(String name) {
		this.name = name;
	}

	@Override
	public IGeneBuilder addAllele(IAlleleKey key, String unlocalizedName) {
		this.alleleInstances.put(key, unlocalizedName);
		return this;
	}

	@Override
	public IGeneBuilder addType(IGeneType type) {
		this.types.add(type);
		return this;
	}

	@Override
	public IGeneBuilder setDefaultAllele(IAlleleKey key) {
		this.defaultKey = key;
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
