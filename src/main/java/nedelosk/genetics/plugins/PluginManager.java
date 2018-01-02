package nedelosk.genetics.plugins;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;

import nedelosk.genetics.GeneticFactory;
import nedelosk.genetics.Genetics;
import nedelosk.genetics.api.GeneticPlugin;
import nedelosk.genetics.api.GeneticsAPI;
import nedelosk.genetics.api.IGeneticPlugin;
import nedelosk.genetics.registry.AlleleRegistry;
import nedelosk.genetics.registry.GeneticRegistry;
import nedelosk.genetics.registry.GeneticSystem;

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
		GeneticsAPI.alleleRegistry = Genetics.alleleRegistry = alleleRegistry;
		plugins.forEach(p -> p.registerAlleles(alleleRegistry));
		//
		GeneticRegistry registry = new GeneticRegistry();
		GeneticsAPI.geneRegistry = registry;
		plugins.forEach(p -> p.registerGenes(registry, GeneticFactory.INSTANCE));
		GeneticSystem system = Genetics.system = registry.createSystem();
		//
		GeneticsAPI.geneticSystem = system;
		plugins.forEach(p -> p.registerDefinitions(system));
	}
}
