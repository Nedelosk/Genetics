package nedelosk.crispr.api;

import nedelosk.crispr.api.registry.IAlleleRegistry;
import nedelosk.crispr.api.registry.IGeneticRegistry;
import nedelosk.crispr.api.registry.IGeneticSystem;

public class CrisprAPI {
	public static IAlleleRegistry alleleRegistry;
	public static IGeneticRegistry geneRegistry;
	public static IGeneticSystem geneticSystem;
	public static IGeneticSaveHandler saveHandler;
}
