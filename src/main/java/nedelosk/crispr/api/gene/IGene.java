package nedelosk.crispr.api.gene;

import java.util.Collection;

import nedelosk.crispr.api.alleles.Allele;

public interface IGene<V> {
	Collection<Allele<V>> getValidAlleles();

	Collection<V> getValidValues();

	Class<? extends V> getValueClass();

	Allele<V> getDefaultAllele();

	String getShortName();

	String getUnlocalizedShortName();

	String getLocalizedName();

	String getUnlocalizedName();
}
