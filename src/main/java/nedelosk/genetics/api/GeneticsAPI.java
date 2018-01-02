package nedelosk.genetics.api;

import nedelosk.genetics.api.registry.IAlleleRegistry;
import nedelosk.genetics.api.registry.IGeneticRegistry;
import nedelosk.genetics.api.registry.IGeneticSystem;

public class GeneticsAPI {
	public static IAlleleRegistry alleleRegistry;
	public static IGeneticRegistry geneRegistry;
	public static IGeneticSystem geneticSystem;
	public static IGeneticSaveHandler saveHandler;
}
