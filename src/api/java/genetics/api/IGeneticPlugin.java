package genetics.api;

import genetics.api.alleles.IAlleleRegistry;
import genetics.api.classification.IClassificationRegistry;
import genetics.api.gene.IGeneBuilder;
import genetics.api.gene.IGeneFactory;
import genetics.api.gene.IKaryotype;
import genetics.api.gene.IKaryotypeBuilder;
import genetics.api.gene.IKaryotypeFactory;
import genetics.api.root.IIndividualRoot;
import genetics.api.root.IIndividualRootBuilder;
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
	 * Create {@link IGeneBuilder}s, {@link IKaryotypeBuilder}s and {@link IIndividualRootBuilder}s
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
	 * Create {@link IndividualRootBuilder}s.
	 */
	default void createRoot(IRootManager manager) {
		//Default Implementation
	}

	/**
	 * Called after {@link #createRoot(IRootManager)} was called at all {@link IGeneticPlugin}s.
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
