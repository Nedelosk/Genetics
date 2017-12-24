package nedelosk.crispr.api;

import nedelosk.crispr.api.alleles.Allele;
import nedelosk.crispr.api.alleles.IAlleleTemplateBuilder;

public interface IGeneticFactory {

	IAlleleTemplateBuilder createTemplate();

	IAlleleTemplateBuilder createTemplate(Allele[] alleles);
}
