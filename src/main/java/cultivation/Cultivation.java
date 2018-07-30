package cultivation;

import net.minecraft.item.Item;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import cultivation.items.ItemCultivation;

@Mod(modid = Cultivation.MOD_ID)
public class Cultivation {

	public static final String MOD_ID = "cultivation";

	public static Item seed;
	public static Item plant;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> registry = event.getRegistry();
		seed = new ItemCultivation("seed");
		registry.register(seed);
		plant = new ItemCultivation("plant");
		registry.register(plant);
	}
}
