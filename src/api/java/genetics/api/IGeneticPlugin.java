package genetics.api;

import genetics.api.definition.IDefinitionFactory;
import genetics.api.definition.IIndividualDefinition;
import genetics.api.definition.IIndividualDefinitionBuilder;
import genetics.api.gene.IGeneBuilder;
import genetics.api.gene.IGeneFactory;
import genetics.api.gene.IKaryotype;
import genetics.api.gene.IKaryotypeBuilder;
import genetics.api.gene.IKaryotypeFactory;
import genetics.api.registry.IAlleleRegistry;

import genetics.definition.IndividualDefinitionBuilder;

/**
 * The main class to implement to create a Genetic plugin. Everything communicated between a mod and Genetics is through
 * this class. IGeneticPlugins must have the {@link GeneticPlugin} annotation to get loaded by Genetics.
 */
public interface IGeneticPlugin {

	/**
	 * Register simple genes
	 */
	default void registerSimple(IRegistryHelper helper) {
		//Default Implementation
	}

	/**
	 * Register alleles
	 */
	default void registerAlleles(IAlleleRegistry registry) {
		//Default Implementation
	}

	/**
	 * Create {@link IGeneBuilder}s, {@link IKaryotypeBuilder}s and {@link IIndividualDefinitionBuilder}s
	 */
	default void registerGenes(IGeneFactory registry) {
		//Default Implementation
	}

	/**
	 * Create {@link IKaryotype}s and {@link IKaryotypeBuilder}s
	 */
	default void createKaryotype(IKaryotypeFactory factory) {
		//Default Implementation
	}

	/**
	 * Create {@link IndividualDefinitionBuilder}s.
	 */
	default void registerDefinition(IDefinitionFactory registry) {
	}

	/**
	 * Called after the previous methods were called and every thing is registered.
	 * Can be used to get created {@link IIndividualDefinition}s.
	 */
	default void onFinishRegistration(IDefinitionFactory registry, IGeneticApiInstance instance) {
		//Default Implementation
	}
}
