package genetics.api.individual;

import java.util.Optional;

import net.minecraft.nbt.NBTTagCompound;

import genetics.api.definition.IGeneticDefinition;

public interface IIndividual {
	IGeneticDefinition getDefinition();

	IGenome getGenome();

	Optional<IGenome> getMate();

	IIndividual copy();

	NBTTagCompound writeToNBT(NBTTagCompound compound);
}
