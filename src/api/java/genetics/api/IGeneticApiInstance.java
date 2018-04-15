package genetics.api;

import genetics.api.alleles.IAllele;
import genetics.api.alleles.IAlleleKey;
import genetics.api.definition.IDefinitionFactory;
import genetics.api.definition.IDefinitionRegistry;
import genetics.api.definition.IIndividualDefinition;
import genetics.api.definition.IIndividualDefinitionBuilder;
import genetics.api.gene.IGene;
import genetics.api.gene.IGeneFactory;
import genetics.api.gene.IGeneRegistry;
import genetics.api.registry.IAlleleRegistry;

public interface IGeneticApiInstance {
	/**
	 * This instance of the allele registry can be used to get {@link IAllele}s and {@link IAlleleKey}s.
	 * It's available after all alleles where registered at {@link IGeneticPlugin#registerAlleles(IAlleleRegistry)}.
	 *
	 * @throws IllegalStateException if the method gets called before {@link IGeneticPlugin#registerAlleles(IAlleleRegistry)}  was called at all plugins.
	 */
	IAlleleRegistry getAlleleRegistry();

	/**
	 * This instance of the genetic registry can be used to get the {@link IIndividualDefinitionBuilder}s of other
	 * plugins.
	 * It's available after all genes and builders where created and added at {@link IGeneticPlugin#registerGenes(IGeneFactory)}.
	 *
	 * @throws IllegalStateException if the method gets called before {@link IGeneticPlugin#registerGenes(IGeneFactory)}  was called at all plugins.
	 */
	IGeneRegistry getGeneRegistry();

	/**
	 * This instance of the genetic system can be used to get {@link IIndividualDefinition}s and {@link IGene}s.
	 * It's available after was called {@link IGeneticPlugin#onFinishRegistration(IDefinitionFactory, IGeneticApiInstance)}.
	 *
	 * @throws IllegalStateException if the method gets called before {@link IGeneticPlugin#onFinishRegistration(IDefinitionFactory, IGeneticApiInstance)} was called at all plugins.
	 */
	IDefinitionRegistry getDefinitionRegistry();

	/**
	 * This instance is available before any method of a {@link IGeneticPlugin} was called.
	 *
	 * @throws IllegalStateException if the method gets called before the pre-init phase of fml.
	 */
	IGeneticFactory getGeneticFactory();

	/**
	 * This instance is available before any method of a {@link IGeneticPlugin} was called.
	 *
	 * @throws IllegalStateException if the method gets called before the pre-init phase of fml.
	 */
	IGeneticSaveHandler getSaveHandler();

	/**
	 * @return Checks if the genetics mod is present.
	 */
	boolean isModPresent();
}