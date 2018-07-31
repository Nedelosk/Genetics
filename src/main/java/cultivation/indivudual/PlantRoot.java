package cultivation.indivudual;

import java.util.Map;
import java.util.function.Function;

import net.minecraft.nbt.NBTTagCompound;

import genetics.api.individual.IGenome;
import genetics.api.individual.IGenomeWrapper;
import genetics.api.individual.IKaryotype;
import genetics.api.root.IIndividualRoot;
import genetics.api.root.IndividualRoot;
import genetics.api.root.components.ComponentKey;
import genetics.api.root.components.IRootComponent;

public class PlantRoot extends IndividualRoot<Plant> {

	public static final String UID = "rootPlant";

	public PlantRoot(IKaryotype karyotype, Function<IIndividualRoot<Plant>, Map<ComponentKey, IRootComponent>> components) {
		super(karyotype, components);
	}

	@Override
	public Plant create(NBTTagCompound compound) {
		return new Plant(compound);
	}

	@Override
	public Plant create(IGenome genome) {
		return new Plant(genome);
	}

	@Override
	public Plant create(IGenome genome, IGenome mate) {
		return new Plant(genome, mate);
	}

	@Override
	public IGenomeWrapper createWrapper(IGenome genome) {
		return null;
	}

	@Override
	public String getUID() {
		return UID;
	}
}
