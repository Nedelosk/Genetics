package nedelosk.crispr;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Crispr.MOD_ID, name = Crispr.NAME, version = Crispr.VERSION)
public class Crispr {
	public static final String MOD_ID = "crispr";
	public static final String NAME = "CRISPR API";
	public static final String VERSION = "@VERSION@";


	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event){
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event){

	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event){

	}
}
