package nedelosk.crispr.api.gene;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Random;

import nedelosk.crispr.api.CrisprAPI;
import nedelosk.crispr.api.alleles.IAllele;
import nedelosk.crispr.api.alleles.IAlleleTemplate;
import nedelosk.crispr.api.alleles.IAlleleTemplateBuilder;

public interface IKaryotype {
	IGeneType[] getGeneTypes();

	default <V> boolean contains(IGene gene) {
		return CrisprAPI.geneticSystem.getTypes(gene).stream().anyMatch(this::contains);
	}

	boolean contains(IGeneType type);

	IGeneType getTemplateType();

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
}
