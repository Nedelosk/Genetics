package cultivation.items;

import javax.annotation.Nullable;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;

import net.minecraftforge.common.capabilities.ICapabilityProvider;

import genetics.api.GeneticsAPI;
import genetics.api.organism.IOrganism;

import genetics.Genetics;

import cultivation.CultivationPlugin;
import cultivation.indivudual.Plant;
import cultivation.indivudual.PlantRoot;
import cultivation.indivudual.PlantType;

public class ItemSeed extends Item {
	public ItemSeed() {
		setCreativeTab(CreativeTabs.MISC);
		setUnlocalizedName("seed");
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
		return GeneticsAPI.apiInstance.getGeneticFactory().createOrganism(stack, PlantType.SEED, CultivationPlugin.DEFINITION);
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		PlantRoot plantRoot = CultivationPlugin.DEFINITION.get();
		for(Plant plant : plantRoot.getIndividualTemplates()){
			ItemStack stack = new ItemStack(this);
			@SuppressWarnings("unchecked")
			IOrganism<Plant> organism = stack.getCapability(Genetics.ORGANISM, null);
			if(organism != null) {
				organism.setIndividual(plant);
			}
			items.add(stack);
		}
	}
}
