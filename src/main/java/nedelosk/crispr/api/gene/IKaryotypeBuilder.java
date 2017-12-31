package nedelosk.crispr.api.gene;

import java.util.function.BiFunction;

import nedelosk.crispr.api.alleles.IAllele;
import nedelosk.crispr.api.alleles.IAlleleTemplateBuilder;

public interface IKaryotypeBuilder {

	IKaryotypeBuilder register(IGeneType gene);

	IKaryotypeBuilder setFactory(BiFunction<IKaryotype, IAllele[], IAlleleTemplateBuilder> templateFactory);

	/**
	 * Registers a allele template using the UID of the first allele as identifier.
	 */
	void registerTemplate(IAllele[] template);

	/**
	 * Registers a allele template using the passed identifier.
	 */
	void registerTemplate(String identifier, IAllele[] template);

	IKaryotype build();
}
