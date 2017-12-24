package nedelosk.crispr.plugins;

import java.util.Collections;
import java.util.List;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import nedelosk.crispr.api.CrisprAPI;
import nedelosk.crispr.api.IGeneticPlugin;
import nedelosk.crispr.api.gene.IGeneRegistry;
import nedelosk.crispr.apiimp.GeneticFactory;
import nedelosk.crispr.apiimp.gene.GeneRegistry;

public class PluginManager {
	private static List<IGeneticPlugin> plugins = Collections.emptyList();

	private PluginManager() {
	}

	public static void create(FMLPreInitializationEvent event) {
		plugins = PluginUtil.getPlugins(event.getAsmData());
	}

	public static void createRegistry() {
		IGeneRegistry geneRegistry = new GeneRegistry();
		CrisprAPI.geneRegistry = geneRegistry;
		for (IGeneticPlugin plugin : plugins) {
			plugin.registerGenes(geneRegistry, GeneticFactory.INSTANCE);
		}
	}
}
