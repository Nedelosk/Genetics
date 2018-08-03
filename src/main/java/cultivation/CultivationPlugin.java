package cultivation;

import javax.annotation.Nullable;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import genetics.api.GeneticPlugin;
import genetics.api.GeneticsAPI;
import genetics.api.IGeneticFactory;
import genetics.api.IGeneticPlugin;
import genetics.api.alleles.IAlleleRegistry;
import genetics.api.root.IIndividualRootBuilder;
import genetics.api.root.IRootDefinition;
import genetics.api.root.IRootManager;
import genetics.api.root.components.ComponentKeys;
import genetics.api.root.translator.IIndividualTranslatorBuilder;
import genetics.api.root.translator.IItemTranslator;

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
		for (PlantDefinition definition : PlantDefinition.values()) {
			registry.registerAllele(definition.getAllele(), PlantChromosomes.SPECIES);
		}
	}

	@Override
	public void createRoot(IRootManager rootManager, IGeneticFactory geneticFactory) {
		IIndividualRootBuilder<Plant> rootBuilder = rootManager.createRoot(PlantRoot.UID, PlantChromosomes.class, PlantDefinition::createDefaultTemplate, PlantRoot::new);
		rootBuilder.addListener(ComponentKeys.TYPES, types -> {
			types.registerType(PlantType.SEED, () -> new ItemStack(Cultivation.seed));
			types.registerType(PlantType.PLANT, () -> new ItemStack(Cultivation.plant));
		});
		rootBuilder.addComponent(ComponentKeys.TRANSLATORS);
		IItemTranslator<Plant> wheatTranslator = new WheatTranslator();
		rootBuilder.addListener(ComponentKeys.TRANSLATORS, (IIndividualTranslatorBuilder translators) -> {
			translators.registerTranslator(Items.WHEAT, wheatTranslator);
			translators.registerTranslator(Items.WHEAT_SEEDS, wheatTranslator);
		});
		rootBuilder.addListener(ComponentKeys.TEMPLATES, PlantDefinition::registerTemplates);
	}

	private static class WheatTranslator implements IItemTranslator<Plant> {
		@Nullable
		@Override
		public Plant getIndividualFromObject(ItemStack itemStack) {
			return PlantDefinition.WHEAT.createIndividual();
		}

		@Override
		public ItemStack getGeneticEquivalent(ItemStack itemStack) {
			PlantType type = itemStack.getItem() == Items.WHEAT ? PlantType.PLANT : PlantType.SEED;
			return DEFINITION.get().getTypes().createStack(PlantDefinition.WHEAT.createIndividual(), type);
		}
	}
}
