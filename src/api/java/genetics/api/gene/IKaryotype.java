package genetics.api.gene;

import javax.annotation.Nullable;

import genetics.api.GeneticsAPI;
import genetics.api.alleles.IAllele;
import genetics.api.alleles.IAlleleTemplate;
import genetics.api.alleles.IAlleleTemplateBuilder;
import genetics.api.definition.ITemplateContainer;
import genetics.api.individual.IChromosome;
import genetics.api.individual.IGenome;
import genetics.api.registry.IGeneticRegistry;

/**
 * The IKaryotype defines how many chromosomes a {@link IGenome} contains and which type the {@link IChromosome}s have.
 * <p>
 * You can use a {@link IKaryotypeBuilder} to create an instance or you create the instance directly with
 * {@link IGeneticRegistry#createKaryotype(Class, String)} if you have a enum that contains your {@link IGeneType}s.
 */
public interface IKaryotype {
	/**
	 * @return Short identifier that is only used if something went wrong.
	 */
	String getIdentifier();

	/**
	 * @return All gene types of this IKaryotype.
	 */
	IGeneType[] getGeneTypes();

	/**
	 * Checks if this karyotype contains any of the types of this gene.
	 */
	default <V> boolean contains(IGene gene) {
		return GeneticsAPI.geneticSystem.getTypes(gene).stream().anyMatch(this::contains);
	}

	/**
	 * Checks if this karyotype contains the given type.
	 */
	boolean contains(IGeneType type);

	/**
	 * @return The {@link IGeneType} that is used by the {@link ITemplateContainer} to identify the different templates.
	 * It uses the {@link IAllele#getRegistryName()} of the allele that is at the active position of the template in the
	 * chromosome with this type.
	 */
	IGeneType getTemplateType();

	/**
	 * Creates a template builder that contains a copy of the default template allele array.
	 */
	IAlleleTemplateBuilder createTemplate();

	/**
	 * Creates a template builder that contains a copy of the allele array.
	 */
	IAlleleTemplateBuilder createTemplate(IAllele[] alleles);

	/**
	 * @return Default individual template for use when stuff breaks.
	 */
	IAlleleTemplate getDefaultTemplate();

	/*
	 * @return The default template as a IGenome.
	 */
	IGenome getDefaultGenome();

	default IChromosome[] templateAsChromosomes(IAllele[] template) {
		return templateAsChromosomes(template, null);
	}

	IChromosome[] templateAsChromosomes(IAllele[] templateActive, @Nullable IAllele[] templateInactive);

	default IGenome templateAsGenome(IAllele[] template) {
		return templateAsGenome(template, null);
	}

	IGenome templateAsGenome(IAllele[] templateActive, @Nullable IAllele[] templateInactive);
}
