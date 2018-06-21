package genetics.api;

import genetics.api.alleles.IAlleleRegistry;
import genetics.api.classification.IClassificationRegistry;
import genetics.api.individual.IKaryotypeFactory;
import genetics.api.root.IIndividualRoot;
import genetics.api.root.IRootManager;

import genetics.root.IndividualRootBuilder;

/**
 * The main class to implement to create a Genetic plugin. Everything communicated between a mod and Genetics is through
 * this class. IGeneticPlugins must have the {@link GeneticPlugin} annotation to get loaded by Genetics.
 */
public interface IGeneticPlugin {
	/**
	 * Register classifications
	 */
	default void registerClassifications(IClassificationRegistry registry) {
		//Default Implementation
	}

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
	 * Create {@link IndividualRootBuilder}s.
	 */
	default void createRoot(IKaryotypeFactory karyotypeFactory, IRootManager rootManager, IGeneticFactory geneticFactory) {
		//Default Implementation
	}

	/**
	 * Called after {@link #createRoot(IKaryotypeFactory, IRootManager, IGeneticFactory)} was called at all {@link IGeneticPlugin}s.
	 */
	default void initRoots(IRootManager manager) {
		//Default Implementation
	}

	/**
	 * Called after the previous methods were called and every thing is registered.
	 * Can be used to get created {@link IIndividualRoot}s.
	 */
	default void onFinishRegistration(IRootManager manager, IGeneticApiInstance instance) {
		//Default Implementation
	}
}
