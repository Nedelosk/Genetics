package nedelosk.crispr.apiimp;

import nedelosk.crispr.api.IGeneticFactory;
import nedelosk.crispr.api.alleles.Allele;
import nedelosk.crispr.api.alleles.IAlleleTemplateBuilder;
import nedelosk.crispr.api.gene.IKaryotype;
import nedelosk.crispr.apiimp.alleles.AlleleTemplateBuilder;

public enum GeneticFactory implements IGeneticFactory {
	INSTANCE;


	@Override
	public IAlleleTemplateBuilder createTemplate(IKaryotype karyotype) {
		return new AlleleTemplateBuilder(karyotype, karyotype.getDefaultTemplate().alleles());
	}

	@Override
	public IAlleleTemplateBuilder createTemplate(IKaryotype karyotype, Allele[] alleles) {
		return new AlleleTemplateBuilder(karyotype, alleles);
	}
}
