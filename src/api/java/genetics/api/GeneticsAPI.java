package genetics.api;

import genetics.api.alleles.IAllele;
import genetics.api.alleles.IAlleleKey;
import genetics.api.definition.IIndividualDefinition;
import genetics.api.definition.IIndividualDefinitionBuilder;
import genetics.api.gene.IGene;
import genetics.api.registry.IAlleleRegistry;
import genetics.api.registry.IGeneticRegistry;
import genetics.api.registry.IGeneticSystem;

public class GeneticsAPI {
	/**
	 * This instance of the allele registry can be used to get {@link IAllele}s and {@link IAlleleKey}s.
	 * It's available after all alleles where registered at {@link IGeneticPlugin#registerAlleles(IAlleleRegistry)}.
	 */
	public static IAlleleRegistry alleleRegistry;

	/**
	 * This instance of the genetic registry can be used to get the {@link IIndividualDefinitionBuilder}s of other
	 * plugins.
	 * It's available after all genes and builders where created and added at {@link IGeneticPlugin#register(IGeneticRegistry)}.
	 */
	public static IGeneticRegistry geneticRegistry;

	/**
	 * This instance of the genetic system can be used to get {@link IIndividualDefinition}s and {@link IGene}s.
	 * It's available after was called {@link IGeneticPlugin#onFinishRegistration(IGeneticSystem)}.
	 */
	public static IGeneticSystem geneticSystem;

	/**
	 * This instance is available before any method of a {@link IGeneticPlugin} was called.
	 */
	public static IGeneticFactory geneticFactory;

	/**
	 * This instance is available before any method of a {@link IGeneticPlugin} was called.
	 */
	public static IGeneticSaveHandler saveHandler;
}
