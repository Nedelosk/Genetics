package cultivation.indivudual;

import net.minecraft.nbt.NBTTagCompound;

import genetics.api.individual.IGenome;
import genetics.api.individual.IGenomeWrapper;
import genetics.api.individual.IKaryotype;
import genetics.api.organism.IOrganismTypes;
import genetics.api.root.ITemplateContainer;
import genetics.api.root.IndividualRoot;
import genetics.api.root.translator.IIndividualTranslator;

public class PlantRoot extends IndividualRoot<Plant> {

	public static final String UID = "rootPlant";

	public PlantRoot(IOrganismTypes<Plant> types, IIndividualTranslator<Plant> translator, ITemplateContainer templates, IKaryotype karyotype) {
		super(types, translator, templates, karyotype);
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
