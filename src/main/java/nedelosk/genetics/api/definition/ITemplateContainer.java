package nedelosk.genetics.api.definition;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Random;

import nedelosk.genetics.api.alleles.IAllele;
import nedelosk.genetics.api.alleles.IAlleleTemplate;
import nedelosk.genetics.api.alleles.IAlleleTemplateBuilder;
import nedelosk.genetics.api.gene.IKaryotype;

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
