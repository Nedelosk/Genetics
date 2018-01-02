package nedelosk.genetics.individual;

import net.minecraft.nbt.NBTTagCompound;

import nedelosk.genetics.api.definition.IGeneticDefinition;
import nedelosk.genetics.api.gene.IGene;
import nedelosk.genetics.api.individual.IChromosome;
import nedelosk.genetics.api.individual.IGenome;

public class Genome implements IGenome {
	public Genome(IChromosome[] chromosomes) {
	}

	@Override
	public <V> IChromosome<V> getChromosome(IGene gene) {
		return null;
	}

	@Override
	public IGene[] getGenes() {
		return new IGene[0];
	}

	@Override
	public IChromosome[] getChromosomes() {
		return new IChromosome[0];
	}

	@Override
	public IGeneticDefinition getDefininition() {
		return null;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		return null;
	}
}
