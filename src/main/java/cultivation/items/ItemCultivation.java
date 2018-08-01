package cultivation.items;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

import net.minecraftforge.common.capabilities.ICapabilityProvider;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import genetics.api.GeneticHelper;
import genetics.api.individual.IChromosomeType;
import genetics.api.organism.IOrganism;
import genetics.api.root.IDisplayHelper;
import genetics.api.root.IIndividualRoot;

import cultivation.Cultivation;
import cultivation.CultivationPlugin;
import cultivation.indivudual.Plant;
import cultivation.indivudual.PlantChromosomes;
import cultivation.indivudual.PlantRoot;
import cultivation.indivudual.PlantType;

public class ItemCultivation extends Item {
	public ItemCultivation(String name) {
		setCreativeTab(CreativeTabs.MISC);
		setRegistryName(name);
		setUnlocalizedName(name);
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		IOrganism<Plant> organism = GeneticHelper.getOrganism(stack);
		return organism.getAllele(PlantChromosomes.SPECIES, true).getRegistryName() + " " + organism.getType().getName();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		IOrganism<Plant> organism = GeneticHelper.getOrganism(stack);
		IIndividualRoot root = organism.getRoot();
		IDisplayHelper displayHelper = root.getDisplayHelper();
		for (IChromosomeType type : root.getKaryotype()) {
			tooltip.add(displayHelper.getLocalizedName(type) + ": " + organism.getAllele(type, true).getLocalizedName());
		}
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
		return GeneticHelper.createOrganism(stack, this == Cultivation.seed ? PlantType.SEED : PlantType.PLANT, CultivationPlugin.DEFINITION);
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (!isInCreativeTab(tab)) {
			return;
		}
		PlantRoot plantRoot = CultivationPlugin.DEFINITION.get();
		for (Plant plant : plantRoot.getIndividualTemplates()) {
			ItemStack stack = new ItemStack(this);
			GeneticHelper.setIndividual(stack, plant);
			items.add(stack);
		}
	}
}
