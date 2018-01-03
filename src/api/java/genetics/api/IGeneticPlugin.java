package genetics.api;

import genetics.api.definition.IGeneticDefinitionBuilder;
import genetics.api.gene.IGeneBuilder;
import genetics.api.gene.IKaryotypeBuilder;
import genetics.api.registry.IAlleleRegistry;
import genetics.api.registry.IGeneticRegistry;
import genetics.api.registry.IGeneticSystem;

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
	 * Create {@link IGeneBuilder}s, {@link IKaryotypeBuilder}s and {@link IGeneticDefinitionBuilder}s
	 */
	default void register(IGeneticRegistry registry) {
		//Default Implementation
	}

	/**
	 * Called after the previous methods were called and every thing is registered.
	 */
	default void onFinishRegistration(IGeneticSystem system) {
		//Default Implementation
	}
}
