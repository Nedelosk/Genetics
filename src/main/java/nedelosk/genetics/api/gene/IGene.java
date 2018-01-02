package nedelosk.genetics.api.gene;

import java.util.Collection;
import java.util.Optional;

import nedelosk.genetics.api.alleles.IAllele;
import nedelosk.genetics.api.alleles.IAlleleKey;

public interface IGene {
	Collection<IAllele> getVariants();

	Collection<IAlleleKey> getKeys();

	boolean isValidAllele(IAllele<?> allele);

	Optional getValue(IAlleleKey key);

	Optional<IAlleleKey> getKey(IAllele<?> allele);

	Optional<IAllele> getAllele(IAlleleKey key);

	IAllele<?> getDefaultAllele();

	String getShortName();

	String getUnlocalizedShortName();

	String getLocalizedName();

	String getUnlocalizedName();

	String getUnlocalizedName(IAllele<?> allele);

	String getLocalizedName(IAllele<?> allele);
}
