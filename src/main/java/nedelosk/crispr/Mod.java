package nedelosk.crispr;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@net.minecraftforge.fml.common.Mod(modid = Mod.MOD_ID, name = Mod.NAME, version = Mod.VERSION)
public class Mod {
	public static final String MOD_ID = "crispr";
	public static final String NAME = "CRISPR API";
	public static final String VERSION = "@VERSION@";


	@net.minecraftforge.fml.common.Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event){
	}

	@net.minecraftforge.fml.common.Mod.EventHandler
	public void init(FMLInitializationEvent event){

	}

	@net.minecraftforge.fml.common.Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event){

	}
}
