package nedelosk.crispr.api.gene;

import java.util.Collection;
import java.util.Optional;

import nedelosk.crispr.api.alleles.Allele;

public interface IGene<V> {
	Collection<Allele<V>> getValidAlleles();

	Collection<V> getValidValues();

	boolean isValidAllele(Allele<V> allele);

	Optional<Allele<V>> getAllele(V value);

	Class<? extends V> getValueClass();

	Allele<V> getDefaultAllele();

	String getShortName();

	String getUnlocalizedShortName();

	String getLocalizedName();

	String getUnlocalizedName();
}
