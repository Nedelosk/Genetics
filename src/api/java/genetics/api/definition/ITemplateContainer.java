package genetics.api.definition;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Random;

import genetics.api.alleles.IAllele;
import genetics.api.alleles.IAlleleTemplate;
import genetics.api.alleles.IAlleleTemplateBuilder;
import genetics.api.gene.IKaryotype;
import genetics.api.individual.IGenome;

/**
 * The ITemplateContainer contains all templates of the {@link IGeneticDefinition} that were added with the
 * {@link IGeneticDefinitionBuilder}.
 */
public interface ITemplateContainer {
	/**
	 * @return Default individual template for use when stuff breaks.
	 */
	IAlleleTemplate getDefaultTemplate();

	/*
	 * @return The default template as a IGenome.
	 */
	IGenome getDefaultGenome();

	/**
	 * Creates a template builder that contains a copy of the default template allele array.
	 */
	IAlleleTemplateBuilder createTemplate();

	/**
	 * Creates a template builder that contains a copy of the allele array.
	 */
	IAlleleTemplateBuilder createTemplate(IAllele[] alleles);

	/**
	 * Retrieves a registered template using the passed species unique identifier.
	 *
	 * @param identifier the {@link IAllele#getRegistryName()} of the active allele at the
	 *                   {@link IKaryotype#getTemplateType()} of the {@link #getKaryotype()}.
	 * @return Array of {@link IAllele} representing a genome.
	 */
	@Nullable
	IAllele[] getTemplate(String identifier);

	/**
	 * @param rand Random to use.
	 * @return A random template from the pool of registered templates.
	 */
	IAllele[] getRandomTemplate(Random rand);

	/**
	 * All templates with there associated identifier.
	 */
	Map<String, IAllele[]> getGenomeTemplates();

	/**
	 * @return The karyotype that defines the size of the allele array and which alleles it can contain.
	 */
	IKaryotype getKaryotype();
}
