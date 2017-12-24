package nedelosk.crispr.api.individual;

import net.minecraft.nbt.NBTTagCompound;

import nedelosk.crispr.api.gene.IGenome;

public interface IIndividualFactory<I extends IIndividual> {

	I create(NBTTagCompound compound);

	I create(IGenome genome);

	I create(IGenome genome, IGenome mate);
}
