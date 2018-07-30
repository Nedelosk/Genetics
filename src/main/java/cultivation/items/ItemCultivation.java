package cultivation.items;

import javax.annotation.Nullable;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;

import net.minecraftforge.common.capabilities.ICapabilityProvider;

import genetics.api.organism.OrganismHelper;

import cultivation.CultivationPlugin;
import cultivation.indivudual.Plant;
import cultivation.indivudual.PlantRoot;
import cultivation.indivudual.PlantType;

public class ItemCultivation extends Item {
	public ItemCultivation(String name) {
		setCreativeTab(CreativeTabs.MISC);
		setRegistryName(name);
		setUnlocalizedName(name);
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
		return OrganismHelper.createOrganism(stack, PlantType.SEED, CultivationPlugin.DEFINITION);
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (!isInCreativeTab(tab)) {
			return;
		}
		PlantRoot plantRoot = CultivationPlugin.DEFINITION.get();
		for (Plant plant : plantRoot.getIndividualTemplates()) {
			ItemStack stack = new ItemStack(this);
			OrganismHelper.setIndividual(stack, plant);
			items.add(stack);
		}
	}
}
