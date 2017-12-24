package nedelosk.crispr.api;

import nedelosk.crispr.api.alleles.Allele;
import nedelosk.crispr.api.alleles.IAlleleTemplateBuilder;
import nedelosk.crispr.api.gene.IKaryotype;

public interface IGeneticFactory {

	IAlleleTemplateBuilder createTemplate(IKaryotype karyotype);

	IAlleleTemplateBuilder createTemplate(IKaryotype karyotype, Allele[] alleles);
}
