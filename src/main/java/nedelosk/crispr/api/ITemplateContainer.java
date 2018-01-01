package nedelosk.crispr.api;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Random;

import nedelosk.crispr.api.alleles.IAllele;
import nedelosk.crispr.api.alleles.IAlleleTemplate;
import nedelosk.crispr.api.alleles.IAlleleTemplateBuilder;
import nedelosk.crispr.api.gene.IKaryotype;

public interface ITemplateContainer {
	IAlleleTemplate getDefaultTemplate();

	IAlleleTemplateBuilder createTemplate();

	IAlleleTemplateBuilder createTemplate(IAllele[] alleles);

	/**
	 * Retrieves a registered template using the passed species unique identifier.
	 *
	 * @param identifier species UID
	 * @return Array of {@link IAllele} representing a genome.
	 */
	@Nullable
	IAllele[] getTemplate(String identifier);

	/**
	 * @param rand Random to use.
	 * @return A random template from the pool of registered templates.
	 */
	IAllele[] getRandomTemplate(Random rand);

	Map<String, IAllele[]> getGenomeTemplates();

	IKaryotype getKaryotype();
}
