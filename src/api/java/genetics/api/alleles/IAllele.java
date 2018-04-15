package genetics.api.alleles;

import javax.annotation.Nonnull;

import net.minecraft.util.ResourceLocation;

import net.minecraftforge.registries.IForgeRegistryEntry;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import genetics.api.individual.IChromosome;
import genetics.api.individual.IGenome;
import genetics.api.individual.IIndividual;

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

	/**
	 * @return Localized short, human-readable identifier used in tooltips and beealyzer.
	 */
	@SideOnly(Side.CLIENT)
	String getLocalizedName();

	/**
	 * @return The unlocalized identifier
	 */
	String getUnlocalizedName();

	@Nonnull
	ResourceLocation getRegistryName();
}
