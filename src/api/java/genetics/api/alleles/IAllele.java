package genetics.api.alleles;

import javax.annotation.Nonnull;

import net.minecraft.util.ResourceLocation;

import net.minecraftforge.registries.IForgeRegistryEntry;

import genetics.api.individual.IChromosome;
import genetics.api.individual.IGenome;
import genetics.api.individual.IOrganism;

/**
 * An {@link IOrganism}'s {@link IGenome} is composed of {@link IChromosome}s consisting each of a primary and secondary {@link IAllele}.
 * <p>
 * {@link IAllele}s hold all information regarding an {@link IOrganism}'s traits, from species to size, temperature tolerances, etc.
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

	@Nonnull
	ResourceLocation getRegistryName();
}
