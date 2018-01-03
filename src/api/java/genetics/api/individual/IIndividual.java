package genetics.api.individual;

import java.util.Optional;

import net.minecraft.nbt.NBTTagCompound;

import genetics.api.alleles.IAllele;
import genetics.api.definition.IGeneticDefinition;
import genetics.api.gene.IGeneType;
import genetics.api.gene.IKaryotype;

/**
 * An actual individual with genetic information.
 */
public interface IIndividual {
	/**
	 * @return The {@link IAllele#getRegistryName()} of the allele that is at the {@link IKaryotype#getTemplateType()}
	 * of the {@link IGenome} of this individual.
	 */
	String getIdentifier();

	/**
	 * @return The definition that describes this individual.
	 */
	IGeneticDefinition getDefinition();

	/**
	 * @return The genetic data of this individual.
	 */
	IGenome getGenome();

	/**
	 * Mate with the given individual.
	 *
	 * @param individual the {@link IIndividual} to mate this one with.
	 */
	void mate(IGenome individual);

	/**
	 * @return Genetic information of the mate, null if unmated.
	 */
	Optional<IGenome> getMate();

	/**
	 * @return A deep copy of this individual.
	 */
	IIndividual copy();

	/**
	 * @return true if this individual has the same active and inactive allele at the position.
	 */
	boolean isPureBred(IGeneType geneType);

	/**
	 * Writes the data of this into NBT-Data.
	 */
	NBTTagCompound writeToNBT(NBTTagCompound compound);

	/**
	 * Call to mark the IIndividual as analyzed.
	 *
	 * @return true if the IIndividual has not been analyzed previously.
	 */
	boolean analyze();

	/**
	 * @return true if the IIndividual has been analyzed previously.
	 */
	boolean isAnalyzed();
}
