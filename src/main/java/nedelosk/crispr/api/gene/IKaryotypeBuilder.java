package nedelosk.crispr.api.gene;

import java.util.function.BiFunction;

import nedelosk.crispr.api.alleles.Allele;
import nedelosk.crispr.api.alleles.IAlleleTemplateBuilder;

public interface IKaryotypeBuilder {

	IKaryotypeBuilder register(IGeneKey gene);

	IKaryotypeBuilder setFactory(BiFunction<IKaryotype, Allele[], IAlleleTemplateBuilder> templateFactory);

	IKaryotype build();
}
