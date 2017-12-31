package nedelosk.crispr.api.gene;

import java.util.Collection;
import java.util.Optional;

import nedelosk.crispr.api.alleles.IAllele;
import nedelosk.crispr.api.alleles.IAlleleKey;

public interface IGene {
	Collection<IAllele> getVariants();

	Collection<IAlleleKey> getKeys();

	boolean isValidAllele(IAllele<?> allele);

	Optional getValue(IAlleleKey key);

	Optional<IAllele> getAllele(IAlleleKey key);

	Class<?> getValueClass();

	IAllele<?> getDefaultAllele();



	String getShortName();

	String getUnlocalizedShortName();

	String getLocalizedName();

	String getUnlocalizedName();

	String getAlleleName(IAllele<?> allele);
}
