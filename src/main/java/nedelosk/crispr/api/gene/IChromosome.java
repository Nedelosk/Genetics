package nedelosk.crispr.api.gene;

import nedelosk.crispr.api.alleles.Allele;

public interface IChromosome<V> {
	IGene<V> getGene();

	Allele<V> getActiveAllele();

	Allele<V> getInactiveAllele();
}
