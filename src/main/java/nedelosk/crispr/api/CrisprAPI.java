package nedelosk.crispr.api;

import nedelosk.crispr.api.alleles.IAlleleRegistry;
import nedelosk.crispr.api.gene.IGeneRegistry;

public class CrisprAPI {
	public static IAlleleRegistry alleleRegistry;
	public static IGeneRegistry geneRegistry;
	public static IGeneticSystem geneticSystem;
	public static IGeneticSaveHandler saveHandler;
}
