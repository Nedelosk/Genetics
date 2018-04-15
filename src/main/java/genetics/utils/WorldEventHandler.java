package genetics.utils;

import net.minecraft.world.World;

import net.minecraftforge.event.world.WorldEvent;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WorldEventHandler {
	@SubscribeEvent
	public void onLoadWorld(WorldEvent.Load event) {
		World world = event.getWorld();
		if (world.provider.isSurfaceWorld()) {
			AlleleSavedData data = (AlleleSavedData) world.loadData(AlleleSavedData.class, "alleles");
			if (data == null) {
				world.setData("alleles", data = new AlleleSavedData("alleles"));
			}
		}
	}
}
