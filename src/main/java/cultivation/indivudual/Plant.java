package cultivation.indivudual;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;

import genetics.api.individual.IGenome;
import genetics.api.individual.IIndividual;
import genetics.api.individual.Individual;
import genetics.api.root.IIndividualRoot;

public class Plant extends Individual {
	public Plant(IGenome genome) {
		super(genome);
	}

	public Plant(IGenome genome, @Nullable IGenome mate) {
		super(genome, mate);
	}

	public Plant(NBTTagCompound compound) {
		super(compound);
	}

	@Override
	public void addTooltip(List<String> tooltip) {

	}

	@Override
	public IIndividualRoot getRoot() {
		return null;
	}

	@Override
	public IIndividual copy() {
		return null;
	}
}
