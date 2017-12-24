package nedelosk.crispr.api;

import net.minecraft.nbt.NBTTagCompound;

import nedelosk.crispr.api.gene.IGenome;
import nedelosk.crispr.api.individual.IIndividual;

public interface IGeneticRoot<I extends IIndividual> {

	I create(NBTTagCompound compound);

	I create(IGenome genome);

	I create(IGenome genome, IGenome mate);
}
