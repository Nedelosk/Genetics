package nedelosk.crispr.apiimp.alleles;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.function.BiFunction;

import nedelosk.crispr.api.CrisprAPI;
import nedelosk.crispr.api.alleles.IAllele;
import nedelosk.crispr.api.alleles.IAlleleCategory;
import nedelosk.crispr.api.alleles.IAlleleKey;

public class AlleleCategory<V> implements IAlleleCategory<V> {
	private final Multimap<IAllele<V>, IAlleleKey> alleles = HashMultimap.create();
	private final String uid;
	private final BiFunction<V, Boolean, IAllele<V>> alleleFactory;

	public AlleleCategory(String uid, BiFunction<V, Boolean, IAllele<V>> alleleFactory) {
		this.uid = uid;
		this.alleleFactory = alleleFactory;
	}

	@Override
	public IAlleleCategory<V> registerAllele(V value, boolean dominant, IAlleleKey... keys) {
		IAllele<V> allele = alleleFactory.apply(value, dominant);
		alleles.putAll(allele, Arrays.asList(keys));
		return this;
	}

	@Override
	public void registerAlleles() {
		for (Map.Entry<IAllele<V>, Collection<IAlleleKey>> entry : alleles.asMap().entrySet()) {
			CrisprAPI.alleleRegistry.registerAllele(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public String getUID() {
		return uid;
	}
}
