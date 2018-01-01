package nedelosk.crispr.api;

import java.util.function.BiFunction;

import nedelosk.crispr.api.alleles.IAllele;
import nedelosk.crispr.api.alleles.IAlleleTemplate;
import nedelosk.crispr.api.alleles.IAlleleTemplateBuilder;
import nedelosk.crispr.api.gene.IKaryotype;

public interface ITemplateRegistry {
	ITemplateRegistry setFactory(BiFunction<IKaryotype, IAllele[], IAlleleTemplateBuilder> templateFactory);

	/**
	 * Registers a allele template using the UID of the first allele as identifier.
	 */
	void registerTemplate(IAllele[] template);

	/**
	 * Registers a allele template using the passed identifier.
	 */
	//TODO: REMOVE ?
	void registerTemplate(String identifier, IAllele[] template);

	/**
	 * Registers a allele template using the UID of the first allele as identifier.
	 */
	void registerTemplate(IAlleleTemplate template);

	/**
	 * Registers a allele template using the passed identifier.
	 */
	//TODO: REMOVE ?
	void registerTemplate(String identifier, IAlleleTemplate template);

	ITemplateContainer create();
}
