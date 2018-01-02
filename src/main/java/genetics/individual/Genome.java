package genetics.individual;

import net.minecraft.nbt.NBTTagCompound;

import genetics.api.definition.IGeneticDefinition;
import genetics.api.gene.IGene;
import genetics.api.individual.IChromosome;
import genetics.api.individual.IGenome;

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
