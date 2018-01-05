package genetics.api.individual;

import java.util.Optional;

import net.minecraft.nbt.NBTTagCompound;

import genetics.api.alleles.IAllele;
import genetics.api.definition.IOrganismDefinition;
import genetics.api.gene.IGeneType;
import genetics.api.gene.IKaryotype;

/**
 * An actual individual organism with genetic information.
 */
public interface IOrganism {
	/**
	 * @return The {@link IAllele#getRegistryName()} of the allele that is at the {@link IKaryotype#getTemplateType()}
	 * of the {@link IGenome} of this individual.
	 */
	String getIdentifier();

	/**
	 * @return The definition that describes this organism.
	 */
	IOrganismDefinition getDefinition();

	/**
	 * @return The genetic data of this organism.
	 */
	IGenome getGenome();

	/**
	 * Mate with the given organism.
	 *
	 * @param individual the {@link IOrganism} to mate this one with.
	 */
	void mate(IGenome individual);

	/**
	 * @return Genetic information of the mate, empty if unmated.
	 */
	Optional<IGenome> getMate();

	/**
	 * @return A deep copy of this organism.
	 */
	IOrganism copy();

	/**
	 * @return true if this organism has the same active and inactive allele at the position.
	 */
	boolean isPureBred(IGeneType geneType);

	/**
	 * Writes the data of this into NBT-Data.
	 */
	NBTTagCompound writeToNBT(NBTTagCompound compound);

	/**
	 * Call to mark the IOrganism as analyzed.
	 *
	 * @return true if the IOrganism has not been analyzed previously.
	 */
	boolean analyze();

	/**
	 * @return true if the IOrganism has been analyzed previously.
	 */
	boolean isAnalyzed();
}
