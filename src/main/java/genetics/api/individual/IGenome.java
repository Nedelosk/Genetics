package genetics.api.individual;

import net.minecraft.nbt.NBTTagCompound;

import genetics.api.definition.IGeneticDefinition;
import genetics.api.gene.IGene;

public interface IGenome {

	<V> IChromosome<V> getChromosome(IGene gene);

	IGene[] getGenes();

	IChromosome[] getChromosomes();

	IGeneticDefinition getDefininition();

	NBTTagCompound writeToNBT(NBTTagCompound compound);
}
