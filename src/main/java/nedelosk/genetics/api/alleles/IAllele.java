package nedelosk.genetics.api.alleles;

import net.minecraftforge.registries.IForgeRegistryEntry;

import nedelosk.genetics.api.individual.IChromosome;
import nedelosk.genetics.api.individual.IGenome;
import nedelosk.genetics.api.individual.IIndividual;

/**
 * An {@link IIndividual}'s {@link IGenome} is composed of {@link IChromosome}s consisting each of a primary and secondary {@link IAllele}.
 * <p>
 * {@link IAllele}s hold all information regarding an {@link IIndividual}'s traits, from species to size, temperature tolerances, etc.
 * <p>
 * Should be extended for different types of alleles. IAlleleSpecies, IAlleleBiome, etc.
 * <p>
 *
 * @param <V> the type of value that this allele contains.
 */
public interface IAllele<V> extends IForgeRegistryEntry<IAllele<?>> {

	/**
	 * @return the value that this allele contains.
	 */
	V getValue();

	/**
	 * @return true if the allele is dominant, false otherwise.
	 */
	boolean isDominant();
}
