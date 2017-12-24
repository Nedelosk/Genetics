package nedelosk.crispr.api.gene;

import nedelosk.crispr.api.IGeneticDefinition;

public interface IGenome {

	<V> IChromosome<V> getChromosome(IGene<V> gene);

	IGene[] getGenes();

	IChromosome[] getChromosomes();

	IGeneticDefinition getRoot();

	IGeneticStat getStat();
}
