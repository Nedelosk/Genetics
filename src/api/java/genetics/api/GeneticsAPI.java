package genetics.api;

import genetics.api.registry.IAlleleRegistry;
import genetics.api.registry.IGeneticRegistry;
import genetics.api.registry.IGeneticSystem;

public class GeneticsAPI {
	/**
	 * Createt
	 */
	public static IAlleleRegistry alleleRegistry;

	public static IGeneticRegistry geneticRegistry;

	public static IGeneticSystem geneticSystem;

	public static IGeneticFactory geneticFactory;

	public static IGeneticSaveHandler saveHandler;
}
