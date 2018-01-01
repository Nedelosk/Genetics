package nedelosk.crispr.api;

import net.minecraft.nbt.NBTTagCompound;

import nedelosk.crispr.api.gene.IGeneticStat;
import nedelosk.crispr.api.individual.IGenome;
import nedelosk.crispr.api.individual.IIndividual;

public interface IGeneticRoot<I extends IIndividual, S extends IGeneticStat> {

	IGeneticDefinition<I, IGeneticRoot<I, S>> getDefinition();

	I create(NBTTagCompound compound);

	I create(IGenome genome);

	I create(IGenome genome, IGenome mate);

	S createStat(IGenome genome);
}
