package genetics.api.definition;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Random;

import genetics.api.alleles.IAllele;
import genetics.api.gene.IKaryotype;

/**
 * The ITemplateContainer contains all templates of the {@link IOrganismDefinition} that were added with the
 * {@link IOrganismDefinitionBuilder}.
 */
public interface ITemplateContainer {

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
