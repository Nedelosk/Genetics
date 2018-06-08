package genetics.api;

import genetics.api.alleles.IAllele;
import genetics.api.alleles.IAlleleKey;
import genetics.api.alleles.IAlleleRegistry;
import genetics.api.classification.IClassification;
import genetics.api.classification.IClassificationRegistry;
import genetics.api.gene.IGeneFactory;
import genetics.api.gene.IGeneRegistry;
import genetics.api.root.IIndividualRoot;
import genetics.api.root.IIndividualRootBuilder;
import genetics.api.root.IRootDefinition;

public interface IGeneticApiInstance {
	/**
	 * This instance of the classification registry can be used to get or register {@link IClassification}s.
	 *
	 * @throws IllegalStateException if the method gets called before the pre init phase of the genetics mod.
	 */
	IClassificationRegistry getClassificationRegistry();

	/**
	 * This instance of the allele registry can be used to get {@link IAllele}s and {@link IAlleleKey}s.
	 * It's available after all alleles where registered at {@link IGeneticPlugin#registerAlleles(IAlleleRegistry)}.
	 *
	 * @throws IllegalStateException if the method gets called before {@link IGeneticPlugin#registerAlleles(IAlleleRegistry)}  was called at all plugins.
	 */
	IAlleleRegistry getAlleleRegistry();

	/**
	 * This instance of the genetic registry can be used to get the {@link IIndividualRootBuilder}s of other
	 * plugins.
	 * It's available after all genes and builders where created and added at {@link IGeneticPlugin#registerGenes(IGeneFactory)}.
	 *
	 * @throws IllegalStateException if the method gets called before {@link IGeneticPlugin#registerGenes(IGeneFactory)}  was called at all plugins.
	 */
	IGeneRegistry getGeneRegistry();

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
	 * Retrieve the {@link IRootDefinition} with the given uid and computes one if there currently is no definition
	 * for the given uid.
	 *
	 * @param rootUID The uid that the {@link IIndividualRoot} object of the {@link IRootDefinition} is associated with.
	 * @param <R>     The type of the {@link IIndividualRoot} object that the definition contains.
	 * @return The definition that is associated with given uid.
	 */
	<R extends IIndividualRoot> IRootDefinition<R> getRoot(String rootUID);

	/**
	 * @return Checks if the genetics mod is present.
	 */
	boolean isModPresent();
}
