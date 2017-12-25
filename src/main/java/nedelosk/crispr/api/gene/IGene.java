package nedelosk.crispr.api.gene;

import java.util.Collection;
import java.util.Optional;

import nedelosk.crispr.api.alleles.Allele;
import nedelosk.crispr.api.alleles.IAlleleKey;

public interface IGene<V> {
	Collection<Allele<V>> getValidAlleles();

	Collection<V> getValidValues();

	boolean isValidAllele(Allele<V> allele);

	Optional<Allele<V>> getAllele(V value);

	Optional<Allele<V>> getAllele(IAlleleKey key);

	Optional<V> getValue(IAlleleKey key);

	Class<? extends V> getValueClass();

	Allele<V> getDefaultAllele();

	String getShortName();

	String getUnlocalizedShortName();

	String getLocalizedName();

	String getUnlocalizedName();
}
