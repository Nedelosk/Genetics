package nedelosk.crispr.api.alleles;

import nedelosk.crispr.api.gene.IGene;

public interface IAlleleNameFormatter<V> {

	String getName(IAllele<V> allele, IGene gene);
}
