package nedelosk.crispr.api.individual;

import net.minecraft.nbt.NBTTagCompound;

import nedelosk.crispr.api.IGeneticDefinition;
import nedelosk.crispr.api.gene.IGene;

public interface IGenome {

	<V> IChromosome<V> getChromosome(IGene gene);

	IGene[] getGenes();

	IChromosome[] getChromosomes();

	IGeneticDefinition getDefininition();

	NBTTagCompound writeToNBT(NBTTagCompound compound);
}
