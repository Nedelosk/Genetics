package mariculture;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import genetics.api.GeneticsAPI;

import genetics.ApiInstance;

@Mod("mariculture")
public class Mariculture {

	public Mariculture() {
		GeneticsAPI.apiInstance = ApiInstance.INSTANCE;
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::preInit);
	}

	public void preInit(FMLCommonSetupEvent event) {

	}
}
