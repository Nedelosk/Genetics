package cultivation;

import net.minecraft.item.ItemStack;

import genetics.api.GeneticPlugin;
import genetics.api.GeneticsAPI;
import genetics.api.IGeneticFactory;
import genetics.api.IGeneticPlugin;
import genetics.api.alleles.IAlleleRegistry;
import genetics.api.individual.IKaryotype;
import genetics.api.individual.IKaryotypeFactory;
import genetics.api.root.IIndividualRootBuilder;
import genetics.api.root.IRootDefinition;
import genetics.api.root.IRootManager;

import cultivation.indivudual.Plant;
import cultivation.indivudual.PlantChromosomes;
import cultivation.indivudual.PlantDefinition;
import cultivation.indivudual.PlantRoot;
import cultivation.indivudual.PlantType;

@GeneticPlugin(modId = Cultivation.MOD_ID)
public class CultivationPlugin implements IGeneticPlugin {
	public static final IRootDefinition<PlantRoot> DEFINITION = GeneticsAPI.apiInstance.getRoot(PlantRoot.UID);

	@Override
	public void registerAlleles(IAlleleRegistry registry) {
		for(PlantDefinition definition : PlantDefinition.values()){
			registry.registerAllele(definition.getAllele(), PlantChromosomes.SPECIES);
		}
	}

	@Override
	public void createRoot(IKaryotypeFactory karyotypeFactory, IRootManager rootManager, IGeneticFactory geneticFactory) {
		IKaryotype karyotype = karyotypeFactory.createKaryotype(PlantRoot.UID, PlantChromosomes.class, PlantDefinition::getDefaultTemplate);
		IIndividualRootBuilder<Plant> rootBuilder =  rootManager.createRoot(PlantRoot.UID, karyotype, PlantRoot::new);
		rootBuilder.registerType(PlantType.SEED, ()->new ItemStack(Cultivation.seed));
		//rootBuilder.registerType(ItemType.SEED, );
		PlantDefinition.preInit(rootBuilder);
	}
}
