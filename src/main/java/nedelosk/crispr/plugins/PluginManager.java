package nedelosk.crispr.plugins;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;

import nedelosk.crispr.Crispr;
import nedelosk.crispr.GeneticFactory;
import nedelosk.crispr.api.CrisprAPI;
import nedelosk.crispr.api.GeneticPlugin;
import nedelosk.crispr.api.IGeneticPlugin;
import nedelosk.crispr.registry.AlleleRegistry;
import nedelosk.crispr.registry.GeneticRegistry;
import nedelosk.crispr.registry.GeneticSystem;

public class PluginManager {
	private static final Comparator<IGeneticPlugin> PLUGIN_COMPARATOR = (firstPlugin, secondPlugin) -> {
		EventPriority first = firstPlugin.getClass().getAnnotation(GeneticPlugin.class).priority();
		EventPriority second = secondPlugin.getClass().getAnnotation(GeneticPlugin.class).priority();
		if (first == second) {
			return 0;
		}
		return first.ordinal() > second.ordinal() ? 1 : -1;
	};
	private static List<IGeneticPlugin> plugins = Collections.emptyList();

	private PluginManager() {
	}

	public static void create(FMLPreInitializationEvent event) {
		plugins = PluginUtil.getPlugins(event.getAsmData());
		plugins.sort(PLUGIN_COMPARATOR);
	}

	public static void initPlugins() {
		//register all alleles
		AlleleRegistry alleleRegistry = new AlleleRegistry();
		CrisprAPI.alleleRegistry = Crispr.alleleRegistry = alleleRegistry;
		plugins.forEach(p -> p.registerAlleles(alleleRegistry));
		//
		GeneticRegistry registry = new GeneticRegistry();
		CrisprAPI.geneRegistry = registry;
		plugins.forEach(p -> p.registerGenes(registry, GeneticFactory.INSTANCE));
		GeneticSystem system = Crispr.system = registry.createSystem();
		//
		CrisprAPI.geneticSystem = system;
		plugins.forEach(p -> p.registerDefinitions(system));
	}
}
